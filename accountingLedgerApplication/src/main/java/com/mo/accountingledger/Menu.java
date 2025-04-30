package com.mo.accountingledger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private static final LedgerManager ledger = new LedgerManager();
    private static final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        showHomeScreen();

    }

    public static void showHomeScreen() {
        System.out.println("\nACCOUNTING LEDGER");

        while (true) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "D" -> addDeposit();
                case "P" -> addPayment();
                case "L" -> showLedger();
                case "X" -> {
                    System.out.println("\nThank you for using the ledger. Take care of yourself for now!");
                }
                default -> System.out.println("Invalid choice. Please try D, P, L, or X.");
            }
        }
    }

    private static void addDeposit() {
        System.out.println("\nADD DEPOSIT");

        System.out.print("Description (e.g., 'Paycheck'): ");
        String description = scanner.nextLine().trim();

        System.out.print("From (Vendor name): ");
        String vendor = scanner.nextLine().trim();

        double amount = 0;
        while (amount <= 0) {
            System.out.print("Amount (must be positive): $");
            try {
                amount = Double.parseDouble(scanner.nextLine());
                if (amount <= 0) {
                    System.out.println("Amount must be greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (like 50 or 29.95).");
            }
        }

        Transaction deposit = new Transaction(
                LocalDate.now(),
                LocalTime.now(),
                description,
                vendor,
                amount
        );

        ledger.addTransaction(deposit);
        System.out.println("Deposit added successfully!");
    }

    private static void addPayment() {
        System.out.println("\nADD PAYMENT (DEBIT)");

        System.out.print("Description (e.g., 'Groceries'): ");
        String description = scanner.nextLine().trim();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine().trim();

        double amount = 0;
        while (amount >= 0) {
            System.out.print("Amount (must be negative): $");
            try {
                amount = Double.parseDouble(scanner.nextLine());
                if (amount >= 0) {
                    System.out.println("Amount must be negative (e.g., -20.00).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (like -50 or -29.95).");
            }
        }

        Transaction payment = new Transaction(
                LocalDate.now(),
                LocalTime.now(),
                description,
                vendor,
                amount
        );

        ledger.addTransaction(payment);
        System.out.println("Payment added successfully!");
    }

    private static void showLedger() {
        while (true) {
            System.out.println("\nLEDGER");
            System.out.println("A) All Transactions (Newest First)");
            System.out.println("D) Deposits Only");
            System.out.println("P) Payments Only");
            System.out.println("H) Home");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "A" -> displayTransactions(false, false);
                case "D" -> displayTransactions(true, false);
                case "P" -> displayTransactions(false, true);
                case "H" -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void displayTransactions(boolean showDepositsOnly, boolean showPaymentsOnly) {

        System.out.println("\nDATE       | DESCRIPTION          | AMOUNT");
        System.out.println("------------------------------------------");

        ArrayList<Transaction> allTransactions = ledger.getTransactions();

        int count = 0;

        for (Transaction t : allTransactions) {
            // Skip if not matching what we want to show
            if (showDepositsOnly && t.getAmount() <= 0) continue;
            if (showPaymentsOnly && t.getAmount() >= 0) continue;

            String shortDesc = t.getDescription();
            if (shortDesc.length() > 20) {
                shortDesc = shortDesc.substring(0, 17) + "...";
            }

            System.out.printf("%s | %-20s | $%7.2f%n",
                    t.getDate(),
                    shortDesc,
                    t.getAmount());

            count++;
        }

        System.out.println("------------------------------------------");
        System.out.println("Total entries: " + count);
    }

}



