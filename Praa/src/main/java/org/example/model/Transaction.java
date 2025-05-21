package org.example.model;
import org.example.annotation.MongoField;
import java.time.LocalDateTime;

public record Transaction(
        @MongoField("_id") String id,
        @MongoField("timestamp") LocalDateTime timestamp,
        @MongoField("TransactionWithdrawalAmount") double  transactionWithdrawalAmount,
        @MongoField("TransactionDepositAmount") double transactionDepositAmount,
        @MongoField("Payer") String payer,
        @MongoField("Payee") String payee,
        @MongoField("TransactionType") String transactionType,
        @MongoField("TransactionReferenceNumber") String transactionReferenceNumber,
        @MongoField("TransactionClosingAmount") double transactionClosingAmount,
        @MongoField("TransactionStatus") String transactionStatus
) {}
