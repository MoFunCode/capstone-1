package com.mo.accountingledger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public class LedgerManager {
    // This ArrayList stores all our transactions in memory while the program runs
    private ArrayList<Transaction> transactions;

    private final String filePath = "/Users/erimo/pluralsight/capstone-1/accountingLedgerApplication/" +
            "src/main/resources/transactions.csv";

    // Constructor - runs automatically when we create a new LedgerManager
    public LedgerManager() {
        this.transactions = new ArrayList<>(); // Create empty list
        loadFromFile(); // Load any saved transactions from file
    }

    public static void main(String[] args) {
        // Get today's date and current time
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        // Create our ledger manager (this will automatically load saved transactions)
        LedgerManager manager = new LedgerManager();

        // Create a new transaction for buying a MacBook
        Transaction macbookPurchase = new Transaction(date, time, "Macbook Pro M4", "Apple", 1534.56);

        // Add the transaction to our ledger (this will also save it to file)
        manager.addTransaction(macbookPurchase);
    }

    public void addTransaction(Transaction newTransaction) {
        // Safety check - don't allow null transactions
        if (newTransaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        // Add to our in memory list
        transactions.add(newTransaction);

        // Save all transactions to file
        saveToFile();
    }

    /**
     * Saves all transactions to our CSV file
     * Each transaction becomes one line in the file
     */
    private void saveToFile() {
        // Try-with-resources automatically closes the file when done
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {

            // Write each transaction to the file
            for (Transaction t : transactions) {
                // Convert transaction to CSV format (date|time|description|vendor|amount)
                writer.write(t.toCsvString());
                writer.newLine(); // Move to next line
            }

            System.out.println("Transactions saved successfully!");

        } catch (IOException e) {
            System.err.println("Error saving transactions: " + e.getMessage());
        }
    }

    /**
     * Loads saved transactions from our CSV file when program starts
     */
    private void loadFromFile() {
        try {
            // First check if the file exists
            if (!Files.exists(Paths.get(filePath))) {
                System.out.println("No existing transactions file found. Starting fresh.");
                return;
            }

            // Read all lines from the file
            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(filePath));

            // Convert each line to a Transaction object
            for (String line : lines) {
                // Skip empty lines
                if (!line.trim().isEmpty()) {
                    transactions.add(Transaction.fromCsvString(line));
                }
            }

            System.out.println("Loaded " + transactions.size() + " existing transactions.");

        } catch (IOException e) {
            System.err.println("Warning: Could not load transactions. Starting fresh. Error: " + e.getMessage());
        }
    }
}