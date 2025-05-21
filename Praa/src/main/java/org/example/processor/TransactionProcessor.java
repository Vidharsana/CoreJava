package org.example.processor;

import org.example.model.*;
import org.example.util.MongoRecordMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.*;
import org.bson.Document;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionProcessor {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "bank";

    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final ObjectMapper objectMapper;
    private final MongoRecordMapper mapper;

    public TransactionProcessor() {
        // Initialize MongoDB connection and database
        mongoClient = MongoClients.create(CONNECTION_STRING);
        database = mongoClient.getDatabase(DATABASE_NAME);

        // Initialize ObjectMapper for Jackson
        objectMapper = new ObjectMapper();

        // Initialize the MongoRecordMapper which will handle collection loading
        mapper = new MongoRecordMapper(objectMapper);
    }

    public static void main(String[] args) throws Exception {
        // Start the processing of transactions
        TransactionProcessor processor = new TransactionProcessor();
        processor.processTransactions();
    }

    public void processTransactions() throws Exception {
        // Load collections from MongoDB using MongoRecordMapper, and map by key fields
        Map<String, Transaction> transactions = loadCollection("Transaction", Transaction.class, "transactionReferenceNumber");
        Map<String, Customer> customers = loadCollection("Customer", Customer.class, "customerID");
        Map<String, Account> accounts = loadCollection("Account", Account.class, "accountNumber");
        Map<String, Bank> banks = loadCollection("Bank", Bank.class, "bankID");
        Map<String, CreditCard> creditCards = loadCollection("CreditCard", CreditCard.class, "cardNumber");
        Map<String, DebitCard> debitCards = loadCollection("DebitCard", DebitCard.class, "cardNumber");
        Map<String, Loan> loans = loadCollection("Loan", Loan.class, "loanID");
        Map<String, UPI> upis = loadCollection("UPI", UPI.class, "upiID");
        Map<String, Address> addresses = loadCollection("Address", Address.class, "addressID");
        Map<String, TransactionStatus> statuses = loadCollection("TransactionStatus", TransactionStatus.class, "statusID");

        List<FullTransaction> fullTransactions = new ArrayList<>();

        // Process each transaction and associate with related entities
        for (Transaction txn : transactions.values()) {
            // Assuming payer and payee fields exist on Transaction record
            String payerId = txn.payer();
            String payeeId = txn.payee();

            Customer customer = customers.get(payerId);
            Account account = accounts.get(payerId);
            Bank bank = banks.get(payerId);
            CreditCard creditCard = creditCards.get(payerId);
            DebitCard debitCard = debitCards.get(payerId);
            Loan loan = loans.get(payerId);
            UPI upi = upis.get(payerId);
            Address address = addresses.get(payerId);
            TransactionStatus status = statuses.get(txn.transactionStatus());

            // Create a full transaction with all the associated data
            FullTransaction fullTxn = new FullTransaction(
                    txn.transactionReferenceNumber(),
                    txn.timestamp(),
                    txn.transactionWithdrawalAmount(),
                    txn.transactionDepositAmount(),
                    txn.transactionClosingAmount(),
                    txn.transactionType(),
                    txn.transactionStatus(),
                    customer,
                    account,
                    bank,
                    upi,
                    creditCard,
                    debitCard,
                    loan,
                    address,
                    status
            );

            fullTransactions.add(fullTxn);
        }

        // Filter success and failed transactions
        List<FullTransaction> successTxns = fullTransactions.stream()
                .filter(t -> "Success".equalsIgnoreCase(t.transactionStatus()))
                .collect(Collectors.toList());

        List<FullTransaction> failedTxns = fullTransactions.stream()
                .filter(t -> !"Success".equalsIgnoreCase(t.transactionStatus()))
                .collect(Collectors.toList());

        // Write the filtered transactions to files
        writeToFile("success_transactions.txt", successTxns);
        writeToFile("failed_transactions.txt", failedTxns);

        // Write date-based files
        Map<String, List<FullTransaction>> byDate = fullTransactions.stream()
                .collect(Collectors.groupingBy(t -> t.timestamp()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));

        for (Map.Entry<String, List<FullTransaction>> entry : byDate.entrySet()) {
            writeToFile(entry.getKey() + ".txt", entry.getValue());
        }

        // Close the MongoDB connection after processing
        mongoClient.close();
        System.out.println("Processing completed!");
    }

    // Generic method to load collections from MongoDB and map them by the key field
    private <T> Map<String, T> loadCollection(String collectionName, Class<T> clazz, String keyFieldName) throws Exception {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        return mapper.loadCollection(collection, clazz, keyFieldName);
    }

    // Helper method to get the value of a field from an object using its getter method
    private <T> String getFieldValue(T obj, String fieldName) {
        try {
            var method = obj.getClass().getMethod("get" + capitalizeFirstLetter(fieldName)); // Ensure method name starts with 'get'
            Object value = method.invoke(obj);
            return value == null ? null : value.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper method to write a list of FullTransaction objects to a file
    private void writeToFile(String filename, List<FullTransaction> transactions) throws Exception {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename))) {
            for (FullTransaction txn : transactions) {
                writer.write(txn.toString());
                writer.newLine();
            }
        }
    }

    // Helper method to capitalize the first letter of the field name for getter method invocation
    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
