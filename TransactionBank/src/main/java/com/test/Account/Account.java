package com.test.Account;

public record Account(
    String _id,
    String AccountHolderNumber,
    String AccountScheme,
    String AccountSchemeCode,
    String AccountBranch,
    String AccountIFSC,
    Double AccountBalance,
    Double AccountUnclearedFunds,
    Double AccountAmountOnHold,
    Double AccountSpendingLimit,
    String AccountBank,
    String AccountCreditCard,
    String AccountDebitCard,
    String AccountLoan,
    String AccountCurrency,
    String AccountOpeningDate,
    String AccountUPI
) {
    public static String header() {
        return String.format(
            "%-36s %-22s %-10s %-10s %-15s %-10s %-12s %-15s %-15s %-15s %-25s %-36s %-36s %-10s %-5s %-15s %-36s",
            "_id",
            "AccountHolderNumber",
            "Scheme",
            "SchemeCode",
            "Branch",
            "IFSC",
            "Balance",
            "UnclearedFunds",
            "AmountOnHold",
            "SpendingLimit",
            "Bank",
            "CreditCard",
            "DebitCard",
            "Loan",
            "Currency",
            "OpeningDate",
            "UPI"
        );
    }

    @Override
    public String toString() {
        return String.format(
            "%-36s %-22s %-10s %-10s %-15s %-10s %-12.2f %-15.2f %-15.2f %-15.2f %-25s %-36s %-36s %-10s %-5s %-15s %-36s",
            _id,
            AccountHolderNumber,
            AccountScheme,
            AccountSchemeCode,
            AccountBranch,
            AccountIFSC,
            AccountBalance,
            AccountUnclearedFunds,
            AccountAmountOnHold,
            AccountSpendingLimit,
            AccountBank,
            AccountCreditCard,
            AccountDebitCard,
            AccountLoan,
            AccountCurrency,
            AccountOpeningDate,
            AccountUPI
        );
    }
}
