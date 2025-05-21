package org.example.model;

import org.example.annotation.MongoField;

public record UPI(
        @MongoField("_id") String id,
        @MongoField("upiId") String upiId,
        @MongoField("linkedAccountId") String linkedAccountId
) {}
