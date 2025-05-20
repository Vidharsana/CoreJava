package com.model;

import com.test.New.FixedWidth;

public record Customer(
	    @FixedWidth(size = 36) String _id,
	    @FixedWidth(size = 25) String CustomerName,
	    @FixedWidth(size = 20) String CustomerAccount
	) {}
