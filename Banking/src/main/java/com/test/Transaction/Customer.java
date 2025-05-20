package com.test.Transaction;

import java.time.LocalDateTime;



public record Customer(
    String customerName,
    String customerUsername,
    LocalDateTime customerDOB,
    String customerEmail,
    String customerContactNumber,
    String customerAlternateNumber,
    String customerMailingAddress,
    String customerPermanentAddress,
    String customerNationality,
    String customerNominee,
    String customerMMID,
    String customerAadhar,
    String customerPANNumber,
    String customerAccount
) {}
