package com.example.controller;

import com.example.service.TextToSpeechService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tts")
public class TextToSpeechController {

    private final TextToSpeechService ttsService;

    public TextToSpeechController(TextToSpeechService ttsService) {
        this.ttsService = ttsService;
    }

    @GetMapping("/speak")
    public ResponseEntity<byte[]> speak(@RequestParam String text) {

        byte[] audioData = ttsService.generateSpeech(text);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"speech.wav\"")
                .contentType(MediaType.parseMediaType("audio/wav"))
                .body(audioData);
    }
}