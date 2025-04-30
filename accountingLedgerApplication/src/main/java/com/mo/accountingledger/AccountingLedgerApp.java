package com.mo.accountingledger;

public class AccountingLedgerApp {

    public static void main(String[] args) {
        System.out.println("Welcome to your Accounting Ledger!");
        System.out.println("Loading your transactions...\n");

        LedgerManager ledgerManager = new LedgerManager();
        Menu menu = new Menu();

        menu.showHomeScreen();
    }

}
