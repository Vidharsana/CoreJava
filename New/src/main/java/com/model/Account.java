package com.model;

import com.test.New.FixedWidth;

public record Account(
	    @FixedWidth(size = 36) String _id,
	    @FixedWidth(size = 25) String AccountHolderNumber,
	    @FixedWidth(size = 15) Double AccountBalance
	) {

	public String AccountDebitCard() {
		// TODO Auto-generated method stub
		return null;
	}}