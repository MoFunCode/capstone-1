package com.mo.accountingledger;

/*
* Home Screen
o The home screen should give the user the following options. The
application should continue to run until the user chooses to exit.
§ D) Add Deposit - prompt user for the deposit information and
save it to the csv file
§ P) Make Payment (Debit) - prompt user for the debit
information and save it to the csv file
§ L) Ledger - display the ledger screen
§ X) Exit - exit the application
* */

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Menu {

    private final LedgerManager ledger = new LedgerManager();
    private final Scanner scanner = new Scanner(System.in);

    public void showHomeScreen() {
        System.out.println("\nACCOUNTING LEDGER");

        while (true) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) View Ledger");
            System.out.println("X) Exit");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "D" -> addDeposit();
                case "P" -> addPayment();
                case "L" -> showLedger();
                case "X" -> {
                    System.out.println("\nThank you for using the ledger. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try D, P, L, or X.");
            }
        }
    }

    private void addDeposit() {
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

    private void addPayment() {

    }

    private void showLedger() {

    }
}


