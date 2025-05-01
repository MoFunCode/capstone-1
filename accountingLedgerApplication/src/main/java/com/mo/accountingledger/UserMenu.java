package com.mo.accountingledger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class UserMenu {

    private static final Ledger ledger = new Ledger();
    private static final Scanner input = new Scanner(System.in);

    public static void showMainMenu() {
        System.out.println("\n MAIN MENU");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) View Ledger");
            System.out.println("X) Exit");
            System.out.print("Enter choice: ");

            String choice = input.nextLine().trim().toUpperCase();

            switch (choice) {
                case "D" -> addDeposit();
                case "P" -> addPayment();
                case "L" -> showLedger();
                case "X" -> {
                    System.out.println("Goodbye! Take care.");
                    return;
                }
                default -> System.out.println("Invalid option. Try D, P, L, or X.");
            }
        }
    }

    private static void showLedgerMenu() {
        while (true) {
            System.out.println("\nLEDGER MENU");
            System.out.println("A) All - Display all entries");
            System.out.println("D) Deposits - Display only deposits");
            System.out.println("P) Payments - Display only payments");
            System.out.println("R) Reports - Run reports");
            System.out.println("H) Home - Return to main menu");
            System.out.print("Enter choice: ");

        }

    }

    private static void addDeposit() {
        System.out.println("\nAdd a Deposit");

        System.out.print("Description: ");
        String description = input.nextLine();

        System.out.print("From (vendor): ");
        String vendor = input.nextLine();

        double amount = readAmount("Enter amount (positive): ", true);

        ledger.addTransaction(new Transaction(
                LocalDate.now(),
                LocalTime.now(),
                description,
                vendor,
                amount
        ));

        System.out.println("Deposit added!");
    }

    private static void addPayment() {
        System.out.println("\nAdd a Payment");

        System.out.print("Description: ");
        String description = input.nextLine();

        System.out.print("To (vendor): ");
        String vendor = input.nextLine();

        double amount = readAmount("Enter amount (negative): ", false);

        ledger.addTransaction(new Transaction(
                LocalDate.now(),
                LocalTime.now(),
                description,
                vendor,
                amount
        ));

        System.out.println("Payment added!");
    }

    private static void showLedger() {
        System.out.println("\nYOUR LEDGER");

        ArrayList<Transaction> allTransactions = ledger.getTransactions();
        System.out.println("DATE       | DESCRIPTION          | AMOUNT");

        int count = 0;
        for (Transaction t : allTransactions) {
            String desc = t.getDescription();
            if (desc.length() > 20) {
                desc = desc.substring(0, 17) + "...";
            }

            System.out.printf("%s | %-20s | $%7.2f%n",
                    t.getDate(), desc, t.getAmount());
            count++;
        }

        System.out.println("---- Total Transactions: " + count + " ----");
    }

    private static double readAmount(String prompt, boolean mustBePositive) {
        double amount = 0;
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            try {
                amount = Double.parseDouble(input.nextLine());
                if (mustBePositive && amount <= 0 || !mustBePositive && amount >= 0) {
                    System.out.println("Please enter a valid " + (mustBePositive ? "positive" : "negative") + " amount.");
                } else {
                    valid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }

        return amount;
    }
}



