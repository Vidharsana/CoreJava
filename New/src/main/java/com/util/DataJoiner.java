package com.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.Account;
import com.model.Customer;
import com.model.DebitCard;
import com.test.New.FixedWidth;
import com.test.New.FixedWidthFormatter;

public class DataJoiner {

    public static void joinAndWrite(List<Customer> customers, List<Account> accounts, List<DebitCard> debitCards) throws Exception {
        Map<String, Account> accountMap = new HashMap<>();
        for (Account acc : accounts) {
            accountMap.put(acc._id(), acc);
        }

        Path path = Paths.get("customer_report.txt");
        Files.write(path, Arrays.asList("CustomerName           AccountNo                      Balance         CardNo                  ".strip()), StandardOpenOption.CREATE);

        for (Customer customer : customers) {
            Account account = accountMap.get(customer.CustomerAccount());
            DebitCard card = null;

            String cardId = account != null ? account.AccountDebitCard() : "";
            if (cardId != null) {
                for (DebitCard dc : debitCards) {
                    if (dc._id().equals(cardId)) {
                        card = dc;
                        break;
                    }
                }
            }

            String line = FixedWidthFormatter.format(new ReportLine(
                customer.CustomerName(),
                account != null ? account.AccountHolderNumber() : "",
                account != null ? account.AccountBalance() : 0.0,
                card != null ? card.CardNumber() : ""
            ));
            Files.writeString(path, line + System.lineSeparator(), StandardOpenOption.APPEND);
        }
    }

    public record ReportLine(
        @FixedWidth(size = 20) String name,
        @FixedWidth(size = 25) String accountNo,
        @FixedWidth(size = 15) Double balance,
        @FixedWidth(size = 20) String cardNo
    ) {}
}
