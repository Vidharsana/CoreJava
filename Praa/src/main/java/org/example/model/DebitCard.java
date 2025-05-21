package org.example.model;

import org.example.annotation.MongoField;

public record DebitCard(
        @MongoField("_id") String id,
        @MongoField("cardNumber") String cardNumber,
        @MongoField("accountId") String accountId,
        @MongoField("expiryDate") String expiryDate
) {}