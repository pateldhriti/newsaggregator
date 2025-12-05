package com.example.service;

import com.example.db.MongoDBConnection;
import com.example.model.UserInteraction;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserInteractionService {

    private MongoCollection<Document> getCollection() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        return db.getCollection("user_interactions");
    }

    /**
     * Track a user's article click
     */
    public void trackClick(String userId, String articleId, String articleTitle, String section) {
        try {
            if (userId == null || articleId == null) {
                System.err.println("⚠️ Cannot track click: userId or articleId is null");
                return;
            }

            Document interaction = new Document()
                    .append("userId", userId)
                    .append("articleId", articleId)
                    .append("articleTitle", articleTitle)
                    .append("section", section)
                    .append("timestamp", LocalDateTime.now().toString());

            getCollection().insertOne(interaction);
            System.out.println("✅ Tracked click: " + userId + " -> " + articleTitle);

        } catch (Exception e) {
            System.err.println("❌ Error tracking click: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get user's interaction history
     */
    public List<UserInteraction> getUserHistory(String userId, int limit) {
        try {
            List<UserInteraction> history = new ArrayList<>();
            
            getCollection()
                    .find(Filters.eq("userId", userId))
                    .sort(Sorts.descending("timestamp"))
                    .limit(limit)
                    .forEach(doc -> {
                        UserInteraction interaction = new UserInteraction();
                        interaction.setUserId(doc.getString("userId"));
                        interaction.setArticleId(doc.getString("articleId"));
                        interaction.setArticleTitle(doc.getString("articleTitle"));
                        interaction.setSection(doc.getString("section"));
                        history.add(interaction);
                    });

            return history;

        } catch (Exception e) {
            System.err.println("❌ Error getting user history: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Get user's clicked article IDs (to exclude from recommendations)
     */
    public Set<String> getClickedArticleIds(String userId) {
        try {
            Set<String> clickedIds = new HashSet<>();
            
            getCollection()
                    .find(Filters.eq("userId", userId))
                    .forEach(doc -> {
                        String articleId = doc.getString("articleId");
                        if (articleId != null) {
                            clickedIds.add(articleId);
                        }
                    });

            return clickedIds;

        } catch (Exception e) {
            System.err.println("❌ Error getting clicked articles: " + e.getMessage());
            return Collections.emptySet();
        }
    }

    /**
     * Track a user's search query
     */
    public void trackSearch(String userId, String query) {
        try {
            if (userId == null || query == null || query.trim().isEmpty()) {
                return;
            }

            Document interaction = new Document()
                    .append("userId", userId)
                    .append("type", "search")
                    .append("query", query.trim())
                    .append("timestamp", LocalDateTime.now().toString());

            getCollection().insertOne(interaction);
            System.out.println("✅ Tracked search: " + userId + " -> " + query);

        } catch (Exception e) {
            System.err.println("❌ Error tracking search: " + e.getMessage());
        }
    }

    /**
     * Get user preferences based on interaction history (Clicks + Searches)
     */
    public Map<String, Object> getUserPreferences(String userId) {
        try {
            // Get recent history (clicks and searches)
            List<Document> history = new ArrayList<>();
            getCollection()
                    .find(Filters.eq("userId", userId))
                    .sort(Sorts.descending("timestamp"))
                    .limit(50)
                    .into(history);
            
            if (history.isEmpty()) {
                return Map.of(
                    "favoriteSections", Collections.emptyList(),
                    "keywords", Collections.emptyList()
                );
            }

            // 1. Calculate Section Preferences (from clicks)
            Map<String, Long> sectionCounts = history.stream()
                    .filter(doc -> !doc.containsKey("type") || "click".equals(doc.getString("type"))) // Assume missing type is click (legacy data)
                    .filter(doc -> doc.getString("section") != null)
                    .collect(Collectors.groupingBy(
                            doc -> doc.getString("section"),
                            Collectors.counting()
                    ));

            List<String> favoriteSections = sectionCounts.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .limit(3)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            // 2. Calculate Keywords (from Clicks AND Searches)
            Map<String, Integer> wordFrequency = new HashMap<>();
            Set<String> stopWords = new HashSet<>(Arrays.asList(
                    "the", "a", "an", "and", "or", "but", "in", "on", "at", "to", "for",
                    "of", "with", "by", "from", "as", "is", "was", "are", "be", "been",
                    "has", "have", "had", "will", "would", "could", "should", "may", "might"
            ));

            for (Document doc : history) {
                String textToAnalyze = "";
                int weight = 1;

                // If it's a search, give it higher weight!
                if ("search".equals(doc.getString("type"))) {
                    textToAnalyze = doc.getString("query");
                    weight = 3; // Searches are 3x more important than clicks
                } else {
                    // It's a click
                    textToAnalyze = doc.getString("articleTitle");
                }

                if (textToAnalyze != null) {
                    String[] words = textToAnalyze
                            .toLowerCase()
                            .replaceAll("[^a-z0-9\\s]", "")
                            .split("\\s+");
                    
                    for (String word : words) {
                        if (word.length() > 3 && !stopWords.contains(word)) {
                            wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + weight);
                        }
                    }
                }
            }

            // Get top 15 keywords (increased from 10 to accommodate search terms)
            List<String> keywords = wordFrequency.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(15)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            return Map.of(
                    "favoriteSections", favoriteSections,
                    "keywords", keywords
            );

        } catch (Exception e) {
            System.err.println("❌ Error getting user preferences: " + e.getMessage());
            return Map.of(
                    "favoriteSections", Collections.emptyList(),
                    "keywords", Collections.emptyList()
            );
        }
    }
}
