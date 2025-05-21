package org.example.model;

import java.time.LocalDateTime;

public record FullTransaction(
        String transactionReferenceNumber,
        LocalDateTime timestamp,
        double transactionWithdrawalAmount,
        double transactionDepositAmount,
        double transactionClosingAmount,
        String transactionType,
        String transactionStatus,

        // Related Entities
        Customer customer,
        Account account,
        Bank bank,
        UPI upi,
        CreditCard creditCard,
        DebitCard debitCard,
        Loan loan,
        Address address,
        TransactionStatus transactionStatusDescription
) {}