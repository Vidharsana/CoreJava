package com.model;

import com.test.New.FixedWidth;

public record UPI(
	    @FixedWidth(size = 36) String _id,
	    @FixedWidth(size = 20) String UPIID
	) {}