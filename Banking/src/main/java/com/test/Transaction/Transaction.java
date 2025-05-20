package com.test.Transaction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.test.annotation.*;
import com.test.annotation.*;

public record Transaction
		(
		LocalDateTime timestamp,
		Double TransactionWithdrawalAmount,
		Double TransactionDepositAmount,
		@FieldLength(8) String Payer,
		@FieldLength(8) String Payee,
		@FieldLength(3) String TransactionType,
		@FieldLength(9) String TransactionReferenceNumber,
		Double TransactionClosingAmount,
		@FieldLength(6) String TransactionStatus
		) 
{
	public Transaction(
			String time,
			Double TransactionWithdrawalAmount,
			Double TransactionDepositAmount,
			String Payer,
			String Payee,
			String TransactionType,
			String TransactionReferenceNumber,
			Double TransactionClosingAmount,
			String TransactionStatus
			)
	{
		this(LocalDateTime.parse(time,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),TransactionWithdrawalAmount,TransactionDepositAmount,Payer,Payee,TransactionType,TransactionReferenceNumber,TransactionClosingAmount,TransactionStatus);
	}
	
	public static String header() {
        return String.format(
            "%-25s %-25.2s %-25s %-20s %-20s %-20s %-40s %-25.2s %-20s",
            "Timestamp",
            "TransactionWithdrawalAmount",
            "TransactionDepositAmount",
            "Payer",
            "Payee",
            "TransactionType",
            "TransactionReferenceNumber",
            "TransactionClosingAmount",
            "TransactionStatus"
        );
    }
	@Override
    public String toString() {
        return String.format(
            "%-25s %-25.2f %-25.2f %-20s %-20s %-20s %-40s %-25.2f %-20s",
            timestamp,
            TransactionWithdrawalAmount,
            TransactionDepositAmount,
            Payer,
            Payee,
            TransactionType,
            TransactionReferenceNumber,
            TransactionClosingAmount,
            TransactionStatus
        );
    }
}
