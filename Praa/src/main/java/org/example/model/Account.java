package org.example.model;

import org.example.annotation.MongoField;

public record Account(
        @MongoField("_id") String id,
        @MongoField("accountNumber") String accountNumber,
        @MongoField("accountType") String accountType,
        @MongoField("balance") double balance,
        @MongoField("customerId") String customerId
) {}