package com.example.controller;

import com.example.model.News;
import com.example.service.RecommendationService;
import com.example.service.UserInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recommendations")
@CrossOrigin(origins = "*")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private UserInteractionService userInteractionService;

    /**
     * Track user article click
     * POST /api/recommendations/track
     * Body: { "userId": "user@email.com", "articleId": "...", "articleTitle": "...", "section": "..." }
     */
    @PostMapping("/track")
    public ResponseEntity<Map<String, String>> trackClick(@RequestBody Map<String, String> request) {
        try {
            String userId = request.get("userId");
            String articleId = request.get("articleId");
            String articleTitle = request.get("articleTitle");
            String section = request.get("section");

            if (userId == null || articleId == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("status", "error", "message", "userId and articleId are required"));
            }

            userInteractionService.trackClick(userId, articleId, articleTitle, section);

            return ResponseEntity.ok(Map.of("status", "success", "message", "Click tracked"));

        } catch (Exception e) {
            System.err.println("❌ Error tracking click: " + e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    /**
     * Get personalized recommendations
     * GET /api/recommendations?userId=user@email.com&limit=20
     */
    @GetMapping
    public ResponseEntity<List<News>> getRecommendations(
            @RequestParam(name = "userId") String userId,
            @RequestParam(name = "limit", defaultValue = "20") int limit) {
        
        try {
            if (userId == null || userId.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            List<News> recommendations = recommendationService.getRecommendations(userId, limit);
            return ResponseEntity.ok(recommendations);

        } catch (Exception e) {
            System.err.println("❌ Error getting recommendations: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
