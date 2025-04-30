package com.mo.accountingledger;

import java.time.LocalDate;
import java.time.LocalTime;
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
            System.out.println("X) Exit");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "D" -> addDeposit();
                case "P" -> addPayment();
                case "X" -> {
                    System.out.println("\nThank you for using the ledger. Goodbye!");
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

    }



