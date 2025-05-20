package com.test.WriteInTxt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.test.Transaction.Transaction;

public class WriteInTxt {

    // Method to write the transactions in a tabular format using streams
    public static <T> void writeToFile(List<T> transactions, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            // Write the header
            writer.write("Transactions for: " + fileName.replace("transactions_", "").replace(".txt", ""));
            writer.newLine();
            writer.newLine();

           
            writer.write(Transaction.header());
            writer.newLine();
            // Write the formatted transactions to the file
            for (T transaction : transactions) {
                writer.write(transaction.toString());
                writer.newLine();
            }

            System.out.println("Transactions have been written to the file: " + fileName);
        }
    }
}
