package com.test.Main;


import java.util.List;
import java.util.Map;

import com.test.MongoDBConnection.MongoDBConnection;
import com.test.MongoToPojo.MongoRecordMapper;
import com.test.Transaction.Customer;
import com.test.Transaction.Transaction;

public class Main {
    public static void main(String[] args) {
        // Register collection names with record classes
        Map<String, Class<?>> collectionRegistry = Map.of(
            "Transaction", Transaction.class,
            "Customer", Customer.class
        );

        // Connect to MongoDB
        var database = MongoDBConnection.getDatabase();

        // Initialize mapper
        MongoRecordMapper recordMapper = new MongoRecordMapper(database, collectionRegistry);
        
        Map<String, List<?>> loadedData = recordMapper.loadAllCollections();

        // Process Transaction data
        List<Transaction> transactions = (List<Transaction>) loadedData.get("Transaction");

        // Write transactions grouped by date to files
        MongoRecordMapper.writeObjectsByDate(transactions, "Transaction");

        
    }
}

