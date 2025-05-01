package com.mo.accountingledger;

import java.time.LocalDate;
import java.time.LocalTime;

public class AccountingLedgerApp {

    public static void main(String[] args) {

        System.out.println("Welcome to your Accounting Ledger!");
        System.out.println("Loading saved transactions...\n");

        Transaction groceries = new Transaction(
                LocalDate.now(), LocalTime.now(), "Groceries",
                "International Market", -120.45);

        Transaction carRepair = new Transaction(
                LocalDate.now().minusDays(1), LocalTime.of(14, 30), "Car Repair",
                "Friend's Garage", -200.50);

        Transaction coffee = new Transaction(
                LocalDate.now(), LocalTime.of(8, 15), "Coffee",
                "Mozart's Cafe", -5.25);

        Ledger ledger = new Ledger();

        ledger.addTransaction(groceries);
        ledger.addTransaction(carRepair);
        ledger.addTransaction(coffee);


        UserMenu.showMainMenu();
    }
}
