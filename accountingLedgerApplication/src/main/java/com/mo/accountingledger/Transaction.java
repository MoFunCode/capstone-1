package com.mo.accountingledger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public String getDescription() { return description; }
    public String getVendor() { return vendor; }
    public double getAmount() { return amount; }

    // Convert to CSV format
    public String toCsv() {
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm:ss");

        return String.join("|",
                date.format(dateFmt),
                time.format(timeFmt),
                description,
                vendor,
                String.format("%.2f", amount)
        );
    }

    public static Transaction fromCsv(String csvLine) {
        String[] parts = csvLine.split("\\|");

        LocalDate date = LocalDate.parse(parts[0]);
        LocalTime time = LocalTime.parse(parts[1]);
        String description = parts[2];
        String vendor = parts[3];
        double amount = Double.parseDouble(parts[4]);

        return new Transaction(date, time, description, vendor, amount);
    }


    public static Transaction fromCsvString(String csvLine) {
        try {
            String[] parts = csvLine.split("\\|");

            if (parts.length != 5) {
                throw new IllegalArgumentException("Invalid CSV format - expected 5 fields");
            }

            LocalDate date = LocalDate.parse(parts[0].trim());
            LocalTime time = LocalTime.parse(parts[1].trim());
            String description = parts[2].trim();
            String vendor = parts[3].trim();
            double amount = Double.parseDouble(parts[4].trim());

            return new Transaction(date, time, description, vendor, amount);

        } catch (Exception e) {
            System.err.println("Failed to parse CSV line: '" + csvLine + "'");
            System.err.println("Reason: " + e.getMessage());
            return null;
        }
    }
}
