# Accounting Ledger Application

A simple Java CLI application for tracking personal or business financial transactions.

## Description

This application allows users to track financial transactions through a command-line interface. Users can add deposits, make payments, view transaction history, and generate various reports. All transactions are saved to a CSV file for persistent storage.

## Demo

Check out a video demonstration of the application:

[![Accounting Ledger App Demo](https://img.youtube.com/vi/TNkj2SEoP9w/0.jpg)](https://youtu.be/TNkj2SEoP9w)


## Features

- Add deposits and payments
- View all transactions in a formatted ledger
- Filter transactions (all, deposits only, payments only)
- Generate reports:
  - Month to date
  - Previous month
  - Year to date
  - Previous year
- Search transactions by vendor

## Screenshots

### Main Menu

<img width="475" alt="Screenshot 2025-05-01 at 9:29:57 PM" src="https://github.com/user-attachments/assets/058e95c3-09cf-4379-a3cd-acbb43e203f5" />


### Ledger View

<img width="401" alt="Screenshot 2025-05-01 at 9:32:41 PM" src="https://github.com/user-attachments/assets/60f31097-a991-469c-aa2b-e85f882e09a7" />




### Reports Menu

<img width="476" alt="Screenshot 2025-05-01 at 9:34:51 PM" src="https://github.com/user-attachments/assets/ddf04b40-cc2b-4fff-8619-90369e524778" />

## Interesting Code Section

One of the most useful parts of this application is the transaction filtering system that powers the reports functionality:

```java
private static ArrayList<Transaction> filterByType(boolean isDeposit) {
    ArrayList<Transaction> filtered = new ArrayList<>();
    for (Transaction t : ledger.getTransactions()) {
        if ((isDeposit && t.getAmount() > 0) || (!isDeposit && t.getAmount() < 0)) {
            filtered.add(t);
        }
    }
    return filtered;
}
```

This method filters transactions based on whether they're deposits (positive amounts) or payments (negative amounts), which provides the foundation for various views in the application.

## How to Run

1. Clone this repository
2. Navigate to the project directory
3. Compile the Java files
4. Run the `AccountingLedgerApp` class

```
javac *.java
java AccountingLedgerApp
```

## Data Storage

All transactions are stored in a CSV file (`transactions.csv`) with the following format:
```
date|time|description|vendor|amount
2023-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50
2023-04-15|11:15:00|Invoice 1001 paid|Joe|1500.00
```
