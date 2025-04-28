package com.mo.accountingledger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class LedgerManager {

    private ArrayList<Transaction> transactions = new ArrayList<>();

    private final String filePath = "/Users/erimo/pluralsight/capstone-1/accountingLedgerApplication/" +
            "src/main/resources/transactions.csv";

    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        // Create an instance of LedgerManager
        LedgerManager manager = new LedgerManager();

        // Create a new transaction (buying a MacBook)
        Transaction macbookPurchase = new Transaction(date, time, "Macbook Pro M4", "Apple", 1534.56);

        // Add the new transaction to the ledger (it will also save to the file)
        manager.addTransaction(macbookPurchase);
    }

    public void addTransaction(Transaction newTransaction) {
        // Check if the transaction is null, If it is, stop and throw an error.
        if (newTransaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        // Add the transaction to the list
        transactions.add(newTransaction);

        // Save the updated list to the CSV file
        saveToFile();
    }

    // Method to save all transactions to the CSV file
    private void saveToFile() {
        // Try to open the file and write to it
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {

            // Go through each transaction and write it as a CSV line
            for (Transaction t : transactions) {
                writer.write(t.toCsvString()); // Convert the transaction into CSV format
                writer.newLine(); // Go to the next line
            }
            System.out.println("Transactions saved successfully!");

        } catch (IOException e) {
            // If something goes wrong (like file not found), print an error message
            System.err.println("Error saving transactions: " + e.getMessage());
        }
    }
}
