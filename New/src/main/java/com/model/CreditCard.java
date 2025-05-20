package com.model;

import com.test.New.FixedWidth;

public record CreditCard(
	    @FixedWidth(size = 36) String _id,
	    @FixedWidth(size = 20) String CardNumber
	) {}