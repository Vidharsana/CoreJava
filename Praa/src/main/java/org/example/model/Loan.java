package org.example.model;

import org.example.annotation.MongoField;

public record Loan(
        @MongoField("_id") String id,
        @MongoField("loanAmount") double loanAmount,
        @MongoField("loanType") String loanType,
        @MongoField("accountId") String accountId
) {}
