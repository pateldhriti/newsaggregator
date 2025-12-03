package com.example.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.SummarizationService;

@RestController
@RequestMapping("/api")
public class SummarizationController {

    @Autowired
    private SummarizationService summarizationService;

    @PostMapping("/summarize")
    public Map<String, String> summarize(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        String summary = summarizationService.summarizeText(text);
        return Map.of("summary", summary);
    }
}
