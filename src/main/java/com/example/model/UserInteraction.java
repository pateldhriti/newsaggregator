package com.example.model;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class UserInteraction {
    private ObjectId id;
    private String userId;
    private String articleId;
    private String articleTitle;
    private String section;
    private LocalDateTime timestamp;

    public UserInteraction() {
        this.timestamp = LocalDateTime.now();
    }

    public UserInteraction(String userId, String articleId, String articleTitle, String section) {
        this.userId = userId;
        this.articleId = articleId;
        this.articleTitle = articleTitle;
        this.section = section;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "UserInteraction{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", articleId='" + articleId + '\'' +
                ", articleTitle='" + articleTitle + '\'' +
                ", section='" + section + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
