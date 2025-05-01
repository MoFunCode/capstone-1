package com.mo.accountingledger;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class Ledger {

    private ArrayList<Transaction> transactions = new ArrayList<>();
    private final String filePath = "src/main/resources/transactions.csv";

    public Ledger() {
        loadTransactionsFromFile();
    }

    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null.");
        }

        transactions.add(transaction);
        saveTransactionsToFile();
    }

    public ArrayList<Transaction> getTransactions() {
        return new ArrayList<>(transactions); // Prevent external modification
    }

    private void saveTransactionsToFile() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            for (Transaction t : transactions) {
                writer.write(t.toCsv());
                writer.newLine();
            }
            System.out.println("Transactions saved.");
        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    private void loadTransactionsFromFile() {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            System.out.println("No saved transactions found. Starting fresh.");
            return;
        }

        try {
            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(path));

            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    transactions.add(Transaction.fromCsv(line));
                }
            }

            System.out.println("Loaded " + transactions.size() + " saved transactions.");
        } catch (IOException e) {
            System.out.println("Failed to load transactions: " + e.getMessage());
        }
    }
}
