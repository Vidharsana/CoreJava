package org.example.model;

import org.example.annotation.MongoField;

public record Customer(
        @MongoField("_id") String id,
        @MongoField("name") String name,
        @MongoField("email") String email,
        @MongoField("phone") String phone
) {}