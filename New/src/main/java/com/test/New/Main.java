package com.test.New;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.Document;

import com.model.Account;
import com.model.Customer;
import com.model.DebitCard;
import com.model.Transaction;
import com.util.DataJoiner;
import com.util.MongoFetcher;

public class Main {
    public static void main(String[] args) throws Exception {
        // Step 1: Fetch raw documents
        List<Document> customerDocs = MongoFetcher.fetch("Customer");
        List<Document> accountDocs = MongoFetcher.fetch("Account");
        List<Document> transactionDocs = MongoFetcher.fetch("Transaction");

        // Step 2: Map to Java records
        List<Customer> customers = customerDocs.stream()
                .map(doc -> RecordMapper.map(doc, Customer.class))
                .toList();

        List<Account> accounts = accountDocs.stream()
                .map(doc -> RecordMapper.map(doc, Account.class))
                .toList();

        List<Transaction> transactions = transactionDocs.stream()
                .map(doc -> RecordMapper.map(doc, Transaction.class))
                .toList();

        // Step 3: Group by date
        Map<String, List<Transaction>> byDate = new HashMap<>();
        for (Transaction txn : transactions) {
            String date = txn.timestamp().substring(0, 10); // YYYY-MM-DD
            byDate.computeIfAbsent(date, d -> new ArrayList<>()).add(txn);
        }

        // Step 4: Write output files
        Path dir = Paths.get("output");
        if (!Files.exists(dir)) Files.createDirectories(dir);

        for (Map.Entry<String, List<Transaction>> entry : byDate.entrySet()) {
            Path path = dir.resolve(entry.getKey() + ".txt");
            Files.write(path, Arrays.asList("Payer                  Payee                  Amount          Type         "), StandardOpenOption.CREATE);

            for (Transaction txn : entry.getValue()) {
                String line = FixedWidthFormatter.format(txn);
                Files.writeString(path, line + System.lineSeparator(), StandardOpenOption.APPEND);
            }
        }

        System.out.println(" Reports generated in 'output/' directory.");
    }
}