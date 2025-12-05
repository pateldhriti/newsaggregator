package com.example.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SummarizationService {

    @Value("${huggingface.api.token}")
    private String HF_TOKEN;

    private static final String HF_API_URL =
            "https://router.huggingface.co/hf-inference/models/google/pegasus-xsum";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final int MAX_CHUNK_LENGTH = 1000;

    public SummarizationService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @PostConstruct
    public void init() {
        if (HF_TOKEN != null && !HF_TOKEN.isEmpty() && !HF_TOKEN.equals("${huggingface.api.token}")) {
            System.out.println("===============================================");
            System.out.println("✓ AI Summarization Service Initialized");
            System.out.println("✓ Using Hugging Face Router");
            System.out.println("✓ Token loaded: " + HF_TOKEN.substring(0, 8) + "...");
            System.out.println("===============================================");
        } else {
            System.out.println("⚠ Hugging Face token not found. Fallback summarization will be used.");
        }
    }

    public String summarizeText(String text) {
        if (text == null || text.trim().isEmpty()) return "Error: Text cannot be empty";
        if (HF_TOKEN == null || HF_TOKEN.isEmpty() || HF_TOKEN.equals("${huggingface.api.token}")) {
            return fallbackSummarize(text);
        }

        List<String> chunks = splitTextIntoChunks(text, MAX_CHUNK_LENGTH);
        List<String> chunkSummaries = new ArrayList<>();

        for (int i = 0; i < chunks.size(); i++) {
            String chunk = chunks.get(i);
            String summary = callHuggingFaceAPI(chunk, i + 1, chunks.size());
            chunkSummaries.add(summary);
        }

        String combinedSummary = String.join(" ", chunkSummaries);

        // Summarize again if too long
        if (combinedSummary.length() > MAX_CHUNK_LENGTH) {
            combinedSummary = callHuggingFaceAPI(combinedSummary, 1, 1);
        }

        return combinedSummary;
    }

    private List<String> splitTextIntoChunks(String text, int maxLength) {
        List<String> chunks = new ArrayList<>();
        int start = 0;
        while (start < text.length()) {
            int end = Math.min(text.length(), start + maxLength);
            if (end < text.length()) {
                int lastPeriod = text.lastIndexOf(".", end);
                if (lastPeriod > start) end = lastPeriod + 1;
            }
            chunks.add(text.substring(start, end).trim());
            start = end;
        }
        return chunks;
    }

    private String callHuggingFaceAPI(String text, int chunkIndex, int totalChunks) {
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                if (totalChunks > 1) {
                    System.out.printf("🤖 Chunk %d/%d - Attempt %d%n", chunkIndex, totalChunks, attempt);
                } else {
                    System.out.printf("🤖 Attempt %d%n", attempt);
                }

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("Accept", "application/json");
                headers.setBearerAuth(HF_TOKEN);

                Map<String, Object> body = new HashMap<>();
                body.put("inputs", text);
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("max_length", 150);
                parameters.put("min_length", 30);
                parameters.put("do_sample", false);
                body.put("parameters", parameters);
                body.put("options", Map.of("wait_for_model", true));

                HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
                ResponseEntity<String> response = restTemplate.exchange(HF_API_URL, HttpMethod.POST, request, String.class);

                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    JsonNode root = objectMapper.readTree(response.getBody());
                    if (root.isArray() && root.size() > 0 && root.get(0).has("summary_text")) {
                        return root.get(0).get("summary_text").asText();
                    }
                    if (root.has("error")) {
                        String error = root.get("error").asText();
                        System.err.println("❌ HF Error: " + error);
                        if (error.contains("loading")) Thread.sleep(10000);
                    }
                }

            } catch (Exception e) {
                System.err.printf("❌ Attempt %d failed: %s%n", attempt, e.getMessage());
                try { Thread.sleep(8000); } catch (InterruptedException ex) { Thread.currentThread().interrupt(); }
            }
        }

        System.out.println("⚠ All attempts failed. Using fallback.");
        return fallbackSummarize(text);
    }

    private String fallbackSummarize(String text) {
        String[] sentences = text.split("(?<=[.!?])\\s+");
        if (sentences.length <= 2) return text;

        Map<String, Integer> wordFreq = new HashMap<>();
        String[] words = text.toLowerCase().replaceAll("[^a-z0-9\\s]", "").split("\\s+");
        Set<String> stopWords = new HashSet<>(Arrays.asList(
                "the","is","at","which","on","a","an","and","or","but","in","with","to","for","of"
        ));
        for (String word : words) {
            if (!stopWords.contains(word) && word.length() > 2)
                wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
        }

        Map<String, Double> sentenceScores = new HashMap<>();
        for (String sentence : sentences) {
            double score = 0;
            String[] sentWords = sentence.toLowerCase().replaceAll("[^a-z0-9\\s]", "").split("\\s+");
            for (String word : sentWords) score += wordFreq.getOrDefault(word, 0);
            if (sentWords.length > 0) score /= sentWords.length;
            sentenceScores.put(sentence, score);
        }

        List<String> topSentences = sentenceScores.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        StringBuilder summary = new StringBuilder();
        for (String sentence : sentences) {
            if (topSentences.contains(sentence)) summary.append(sentence).append(" ");
        }

        return summary.toString().trim();
    }
}
