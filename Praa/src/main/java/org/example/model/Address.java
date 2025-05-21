package org.example.model;

import org.example.annotation.MongoField;

public record Address(
        @MongoField("_id") String id,
        @MongoField("street") String street,
        @MongoField("city") String city,
        @MongoField("state") String state,
        @MongoField("zip") String zip
) {}