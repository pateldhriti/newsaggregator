package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.model.News;
import com.example.service.NewsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class NewsController {

    @Autowired
    private NewsService newsService;

    /**
     * Endpoint: GET /api/news
     * Fetch news articles with optional search and section filtering
     */
    @GetMapping("/news")
    public ResponseEntity<List<News>> getAllNews(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "30") int limit,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "section", defaultValue = "all") String section) {
        
        try {
            System.out.println("📰 NewsController: GET /api/news");
            System.out.println("   Page: " + page + ", Limit: " + limit);
            System.out.println("   Search: '" + search + "'");
            System.out.println("   Section: '" + section + "'");

            List<News> result = newsService.getAllNews(page, limit, search, section);
            
            System.out.println("✅ Returned " + result.size() + " articles");
            
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            System.err.println("❌ Error in NewsController.getAllNews: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Endpoint: POST /api/search-increment
     * Increment search frequency for analytics
     */
    @PostMapping("/search-increment")
    public ResponseEntity<Map<String, Object>> incrementSearchFrequency(
            @RequestBody String searchTerm) {

        try {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "error");
                response.put("message", "Search term cannot be empty");
                return ResponseEntity.badRequest().body(response);
            }

            System.out.println("📊 Search Frequency Incremented: '" + searchTerm.trim() + "'");

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("search_term", searchTerm.trim());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("❌ Error incrementing search frequency: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * Endpoint: POST /api/search-suggest
     * Get autocomplete suggestions for search
     */
    @PostMapping("/search-suggest")
    public ResponseEntity<List<Map<String, Object>>> getSuggestions(
            @RequestBody String prefix) {

        try {
            if (prefix == null || prefix.trim().isEmpty()) {
                return ResponseEntity.ok(List.of());
            }

            System.out.println("🔍 Search Suggestion Requested: '" + prefix.trim() + "'");
            
            // TODO: Implement autocomplete logic with database
            // For now, return empty list
            List<Map<String, Object>> suggestions = List.of();

            return ResponseEntity.ok(suggestions);

        } catch (Exception e) {
            System.err.println("❌ Error getting suggestions: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(List.of());
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        response.put("service", "News API");
        return ResponseEntity.ok(response);
    }
}