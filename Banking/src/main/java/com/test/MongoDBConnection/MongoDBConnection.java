package com.test.MongoDBConnection;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
	
	private static final String CONNECTION = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "Bank";

    private static MongoDatabase database;

    public static MongoDatabase getDatabase() {
        if (database == null) {
            MongoClient client = MongoClients.create(CONNECTION);
            database = client.getDatabase(DATABASE_NAME);
        }
        return database;
    }

}
