package org.example.util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;  // Import the module
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.example.annotation.MongoField;
import org.example.model.Transaction;

import java.lang.reflect.Constructor;
import java.lang.reflect.RecordComponent;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MongoRecordMapper {

    private final ObjectMapper objectMapper;

    public MongoRecordMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        // Register the JavaTimeModule to handle LocalDateTime
        this.objectMapper.registerModule(new JavaTimeModule());  // Register the module
    }

    public <T> Map<String, T> loadCollection(MongoCollection<Document> collection, Class<T> recordClass, String keyField) throws Exception {
        Map<String, T> resultMap = new HashMap<>();

        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                System.out.println("Processing document: " + doc);  // Debug print
                T recordInstance = createRecordInstance(recordClass, doc);
                Object keyObj = doc.get(keyField);
                if (keyObj != null) {
                    resultMap.put(keyObj.toString(), recordInstance);
                    System.out.println("Added record with key: " + keyObj);  // Debug print
                }
            }
        } finally {
            cursor.close();
        }

        return resultMap;
    }

    private <T> T createRecordInstance(Class<T> recordClass, Document doc) throws Exception {
        // Get all fields in the order declared in the record
        RecordComponent[] recordComponents = recordClass.getRecordComponents();
        Object[] args = new Object[recordComponents.length];

        for (int i = 0; i < recordComponents.length; i++) {
            RecordComponent component = recordComponents[i];
            MongoField mongoField = component.getAnnotation(MongoField.class);
            String fieldName = mongoField != null ? mongoField.value() : component.getName();
            Object fieldValue = doc.get(fieldName);

            // Handle LocalDateTime conversion if required
            if (fieldValue != null && component.getType().equals(LocalDateTime.class)) {
                fieldValue = LocalDateTime.parse(fieldValue.toString());
            }

            // Convert the field value to the correct type
            args[i] = objectMapper.convertValue(fieldValue, component.getType());
        }

        // Get the constructor that matches the argument types
        Constructor<T> constructor = recordClass.getDeclaredConstructor(
                Arrays.stream(recordComponents)
                        .map(component -> component.getType())  // Get the field's type
                        .toArray(Class[]::new)
        );

        // Invoke the constructor and return the new instance
        return constructor.newInstance(args);
    }
}