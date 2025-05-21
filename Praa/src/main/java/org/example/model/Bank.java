package org.example.model;

import org.example.annotation.MongoField;

public record Bank(
        @MongoField("_id") String id,
        @MongoField("name") String name,
        @MongoField("branchCode") String branchCode
) {}