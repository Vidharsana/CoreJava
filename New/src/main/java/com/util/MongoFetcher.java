package com.util;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoFetcher {

    public static List<Document> fetch(String collectionName) {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = client.getDatabase("bank");
        MongoCollection<Document> collection = db.getCollection(collectionName);

        List<Document> docs = new ArrayList<>();
        collection.find().forEach(docs::add);
        return docs;
    }
}
