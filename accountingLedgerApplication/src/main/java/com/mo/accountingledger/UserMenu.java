package com.mo.accountingledger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class UserMenu {

    private static final Ledger ledger = new Ledger();
    private static final Scanner input = new Scanner(System.in);
    private static final Reports reports = new Reports(ledger);

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
                case "L" -> showLedgerMenu();
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

            String choice = input.nextLine().trim().toUpperCase();

            switch (choice) {
                case "A" -> displayTransactions(ledger.getTransactions(), "ALL TRANSACTIONS");
                case "D" -> displayTransactions(filterByType(true), "DEPOSITS ONLY");
                case "P" -> displayTransactions(filterByType(false), "PAYMENTS ONLY");
                case "R" -> showReportsMenu();
                case "H" -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try A, D, P, R, or H.");
            }
        }
    }

    private static void showReportsMenu() {
        while (true) {
            System.out.println("\nREPORTS MENU");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back - Return to ledger menu");
            System.out.print("Enter choice: ");

            String choice = input.nextLine().trim();

            switch (choice) {
                case "1" -> displayTransactions(reports.getMonthToDate(), "MONTH TO DATE");
                case "2" -> displayTransactions(reports.getPreviousMonth(), "PREVIOUS MONTH");
                case "3" -> displayTransactions(reports.getYearToDate(), "YEAR TO DATE");
                case "4" -> displayTransactions(reports.getPreviousYear(), "PREVIOUS YEAR");
                case "5" -> searchByVendor();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try 1, 2, 3, 4, 5, or 0.");
            }
        }
    }

    private static void searchByVendor() {
        System.out.print("Enter vendor name to search: ");
        String vendorName = input.nextLine().trim();

        ArrayList<Transaction> results = new ArrayList<>();
        for (Transaction t : ledger.getTransactions()) {
            if (t.getVendor().toLowerCase().contains(vendorName.toLowerCase())) {
                results.add(t);
            }
        }

        displayTransactions(results, "VENDOR SEARCH: " + vendorName);
    }

    private static ArrayList<Transaction> filterByType(boolean isDeposit) {
        ArrayList<Transaction> filtered = new ArrayList<>();
        for (Transaction t : ledger.getTransactions()) {
            if ((isDeposit && t.getAmount() > 0) || (!isDeposit && t.getAmount() < 0)) {
                filtered.add(t);
            }
        }
        return filtered;
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

    private static void displayTransactions(ArrayList<Transaction> transactions, String title) {
        System.out.println("\n" + title);

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("DATE       | TIME     | DESCRIPTION          | VENDOR              | AMOUNT");
        System.out.println("--------------------------------------------------------------------------");

        for (Transaction t : transactions) {
            String desc = t.getDescription();
            if (desc.length() > 20) {
                desc = desc.substring(0, 17) + "...";
            }

            String vendor = t.getVendor();
            if (vendor.length() > 20) {
                vendor = vendor.substring(0, 17) + "...";
            }
            System.out.printf("%s | %s | %-20s | %-20s | $%9.2f%n",
                    t.getDate(), t.getTime().toString().substring(0, 8),
                    desc, vendor, t.getAmount());
        }

        System.out.println("---- Total Transactions: " + transactions.size() + " ----");
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



