package com.model;

import com.test.New.FixedWidth;

public record Transaction(
	    @FixedWidth(size = 36) String _id,
	    @FixedWidth(size = 12) String timestamp,
	    @FixedWidth(size = 15) Double TransactionWithdrawalAmount,
	    @FixedWidth(size = 15) Double TransactionDepositAmount,
	    @FixedWidth(size = 20) String Payer,
	    @FixedWidth(size = 20) String Payee,
	    @FixedWidth(size = 10) String TransactionType,
	    @FixedWidth(size = 36) String TransactionReferenceNumber,
	    @FixedWidth(size = 15) Double TransactionClosingAmount,
	    @FixedWidth(size = 10) String TransactionStatus
	) {}
