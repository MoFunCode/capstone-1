package com.mo.accountingledger;

import java.time.LocalDate;
import java.util.ArrayList;

public class Reports {

    private final Ledger ledger;

    public Reports(Ledger ledger) {
        this.ledger = ledger;
    }

    public ArrayList<Transaction> getMonthToDate() {
        LocalDate today = LocalDate.now();
        return getByDateRange(today.withDayOfMonth(1), today);
    }

    public ArrayList<Transaction> getPreviousMonth() {
        LocalDate today = LocalDate.now();
        LocalDate firstOfLastMonth = today.minusMonths(1).withDayOfMonth(1);
        LocalDate lastOfLastMonth = today.withDayOfMonth(1).minusDays(1);
        return getByDateRange(firstOfLastMonth, lastOfLastMonth);
    }

    public ArrayList<Transaction> getYearToDate() {
        LocalDate today = LocalDate.now();
        return getByDateRange(today.withDayOfYear(1), today);
    }

    private ArrayList<Transaction> getByDateRange(LocalDate start, LocalDate end) {
        ArrayList<Transaction> results = new ArrayList<>();
        for (Transaction t : ledger.getTransactions()) {
            if (!t.getDate().isBefore(start) && !t.getDate().isAfter(end)) {
                results.add(t);
            }
        }
        return results;
    }

    public ArrayList<Transaction> getPreviousYear() {
        LocalDate today = LocalDate.now();
        LocalDate firstOfLastYear = today.minusYears(1).withDayOfYear(1);
        LocalDate lastOfLastYear = today.withDayOfYear(1).minusDays(1);
        return getByDateRange(firstOfLastYear, lastOfLastYear);
    }
}