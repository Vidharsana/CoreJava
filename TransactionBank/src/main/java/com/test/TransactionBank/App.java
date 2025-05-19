package com.test.TransactionBank;

import com.test.Account.Account;

import com.test.MongoDBConnection.MongoDBConnection;
import com.test.MongoToPojo.MongoRecordMapper;


import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        // Register collection names with record classes
        Map<String, Class<?>> collectionRegistry = Map.of(
            "Accounts", Account.class
        );

        // Connect to MongoDB
        var database = MongoDBConnection.getDatabase();

        // Initialize mapper
        MongoRecordMapper recordMapper = new MongoRecordMapper(database, collectionRegistry);
        
        // Load data from MongoDB collections
        Map<String, List<?>> loadedData = recordMapper.loadAllCollections();

        // Process Account data
        List<Account> accounts = (List<Account>) loadedData.get("Accounts");

        // Debugging: Check if accounts were loaded successfully
        System.out.println("Loaded " + accounts.size() + " accounts");
        if (!accounts.isEmpty()) {
            System.out.println("First account: " + accounts.get(0));
        } else {
            System.out.println("No accounts were loaded.");
        }

        // Write accounts data to file
        try {
            MongoRecordMapper.writeObjectsByDate(accounts, "Account");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
