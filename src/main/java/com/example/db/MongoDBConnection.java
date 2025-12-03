package com.example.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    /**
     * This MongoDB connection is used ONLY by your crawler.
     * Spring Boot will NOT scan this class.
     * It will NOT interfere with Spring Data MongoDB.
     */
    public static MongoDatabase getDatabase() {

        if (database == null) {
            try {
                // Always use your Atlas URI with database name in it
                String mongoUri = "mongodb+srv://dhruvipatel:dhruviVpatel37@cluster0.ky8w9m9.mongodb.net/newsAggregatorDB";

                System.out.println("✅ Connecting to MongoDB (crawler)...");
                mongoClient = MongoClients.create(mongoUri);

                // Must use same DB name as Spring Boot config
                database = mongoClient.getDatabase("newsAggregatorDB");

                System.out.println("✅ Crawler MongoDB connected!");
            } catch (Exception e) {
                System.err.println("❌ Crawler failed to connect to MongoDB:");
                e.printStackTrace();
                throw e;
            }
        }

        return database;
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("MongoDB connection closed");
        }
    }
}
