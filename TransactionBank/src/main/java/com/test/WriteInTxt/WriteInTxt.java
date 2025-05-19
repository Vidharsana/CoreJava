package com.test.WriteInTxt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.test.Account.Account;

public class WriteInTxt {

    // Method to write the transactions in a tabular format using streams
    public static <T> void writeToFile(List<T> accounts, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            // Write the header
            writer.write("Accounts for: " + fileName.replace("accounts_", "").replace(".txt", ""));
            writer.newLine();
            writer.newLine();

           
            writer.write(Account.header());
            writer.newLine();
            // Write the formatted transactions to the file
            for (T account : accounts) {
                writer.write(account.toString());
                writer.newLine();
            }

            System.out.println("Accounts have been written to the file: " + fileName);
        }
    }
}
