package org.example.model;
import org.example.annotation.MongoField;

public record TransactionStatus(
        @MongoField("_id") String id,
        @MongoField("Status") String status,
        @MongoField("Description") String description
) {}