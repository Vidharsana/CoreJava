package com.test.MongoToPojo;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.test.Transaction.Transaction;
import com.test.WriteInTxt.WriteInTxt;

import com.test.annotation.FieldLength;

import org.bson.Document;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.RecordComponent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class MongoRecordMapper {

    private final MongoDatabase database;
    private final ObjectMapper objectMapper;
    private final Map<String, Class<?>> collectionToRecordMap;

    public MongoRecordMapper(MongoDatabase database, Map<String, Class<?>> collectionToRecordMap) {
        this.database = database;
        this.collectionToRecordMap = collectionToRecordMap;
        this.objectMapper = new ObjectMapper()
        		.registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    private <T> List<T> mapCollectionToRecords(MongoCollection<Document> collection, Class<T> recordClass) {
        List<T> result = new ArrayList<>();
        
        for (Document doc : collection.find()) {  
        	
//        	checkAndTrimFieldLength(doc, recordClass);
        	
            try {
                T obj = objectMapper.readValue(doc.toJson(), recordClass);
                result.add(obj);
            } catch (JsonProcessingException e) {
                System.err.println("Failed to map document: " + doc);
                e.printStackTrace();
            }
        }
        return result;
    }

    public Map<String, List<?>> loadAllCollections() {
        Map<String, List<?>> results = new HashMap<>();

        for (Map.Entry<String, Class<?>> entry : collectionToRecordMap.entrySet()) {
            String collectionName = entry.getKey();
            Class<?> recordClass = entry.getValue();

            MongoCollection<Document> collection = database.getCollection(collectionName);
            List<?> mapped = mapCollectionToRecords(collection, recordClass);
            results.put(collectionName, mapped);
        } 
        return results;
    }
    
    public static <T> void writeObjectsByDate(List<T> objects, String directoryPath) {

        // Group transactions by date
        Map<Object, List<T>> groupByDate = objects.stream()
                .collect(Collectors.groupingBy(tx -> ((Transaction) tx).timestamp(), TreeMap::new, Collectors.toList()));

        // For each grouped date, create a filename and write the transactions
        groupByDate.forEach((date, transactions) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String fileName = "transactions_" + ((LocalDateTime) date).format(formatter) + ".txt";
            try {
                // Write the transactions to the file with the generated filename
                WriteInTxt.writeToFile(transactions, fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
        
        
}
    
