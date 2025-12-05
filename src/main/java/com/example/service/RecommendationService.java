package com.example.service;

import com.example.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private UserInteractionService userInteractionService;

    @Autowired
    private NewsService newsService;

    /**
     * Get personalized recommendations for a user
     */
    public List<News> getRecommendations(String userId, int limit) {
        try {
            System.out.println("🎯 Generating recommendations for user: " + userId);

            // Get user preferences
            Map<String, Object> preferences = userInteractionService.getUserPreferences(userId);
            @SuppressWarnings("unchecked")
            List<String> favoriteSections = (List<String>) preferences.get("favoriteSections");
            @SuppressWarnings("unchecked")
            List<String> keywords = (List<String>) preferences.get("keywords");

            // Get clicked article IDs to exclude
            Set<String> clickedIds = userInteractionService.getClickedArticleIds(userId);

            // Get all available news articles
            List<News> allNews = newsService.getAllNews(1, 500, "", "all");

            if (allNews.isEmpty()) {
                System.out.println("⚠️ No news articles available");
                return Collections.emptyList();
            }

            // Score each article
            List<ScoredArticle> scoredArticles = new ArrayList<>();
            
            for (News article : allNews) {
                // Skip if already clicked
                String articleId = generateArticleId(article);
                if (clickedIds.contains(articleId)) {
                    continue;
                }

                double score = calculateScore(article, favoriteSections, keywords);
                scoredArticles.add(new ScoredArticle(article, score));
            }

            // Sort by score and return top N
            List<News> recommendations = scoredArticles.stream()
                    .sorted(Comparator.comparingDouble(ScoredArticle::getScore).reversed())
                    .limit(limit)
                    .map(ScoredArticle::getArticle)
                    .collect(Collectors.toList());

            System.out.println("✅ Generated " + recommendations.size() + " recommendations");
            return recommendations;

        } catch (Exception e) {
            System.err.println("❌ Error generating recommendations: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Calculate relevance score for an article
     */
    private double calculateScore(News article, List<String> favoriteSections, List<String> keywords) {
        double score = 0.0;

        // Section matching (highest weight)
        if (article.getSection() != null && favoriteSections.contains(article.getSection())) {
            score += 10.0;
        }

        // Keyword matching
        String articleText = (article.getTitle() + " " + article.getDescription()).toLowerCase();
        for (String keyword : keywords) {
            if (articleText.contains(keyword.toLowerCase())) {
                score += 5.0;
            }
        }

        // Recency bonus (prefer newer articles)
        // This is a simple heuristic - you can improve it with actual date parsing
        score += 1.0;

        return score;
    }

    /**
     * Generate a unique ID for an article
     */
    private String generateArticleId(News article) {
        // Use title as ID (simple approach)
        return article.getTitle() != null ? article.getTitle() : UUID.randomUUID().toString();
    }

    /**
     * Helper class to store article with its score
     */
    private static class ScoredArticle {
        private final News article;
        private final double score;

        public ScoredArticle(News article, double score) {
            this.article = article;
            this.score = score;
        }

        public News getArticle() {
            return article;
        }

        public double getScore() {
            return score;
        }
    }
}
