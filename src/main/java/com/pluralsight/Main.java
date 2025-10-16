package com.pluralsight;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TransactionLedger ledger = new TransactionLedger();

        showHomeMenu(ledger);

    }
    // HOME MENU
    private static void showHomeMenu(TransactionLedger ledger) {
        boolean running = true;

        while (running) {
            System.out.println("\nWelcome to Tanner's Terrific team's Ledger Application!");
            System.out.println("\n==== 🏈 Locker Room Menu 🏈 ====");
            System.out.println("I) Add Team Income (Add Deposit)");
            System.out.println("E) Log Team Expense (Make Payment)");
            System.out.println("T) View Team Transactions (View Ledger)");
            System.out.println("X) Exit Game");

            String choice = ConsoleHelper.prompt("Choose a play: ").toUpperCase();

            switch (choice) {
                case "I":
                    addDeposit(ledger);
                    break;
                case "E":
                    makePayment(ledger);
                    break;
                case "T":
                    showLedgerMenu(ledger);
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Incomplete pass! Invalid Entry, please try again and enter either 'I', 'E', 'T', or 'X'.");
            }
        }
        System.out.println("🏈 Game Over! Thanks for managing Tanner's Terrific team's finances.");
    }

    // ADD DEPOSIT
    private static void addDeposit(TransactionLedger ledger) {
        while (true) {
            String description = ConsoleHelper.prompt("Enter Income Description (e.g., Sponsorship) [0 to Return]: ");
            if (description.equals("0")) return;

            String vendor = ConsoleHelper.prompt("Enter Income Source (e.g., Sponsor Name): ");

            double amount;
            try {
                amount = Double.parseDouble(ConsoleHelper.prompt("Enter Income Amount: "));
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println("Flag on the play! Invalid Entry. Please try again.");
                continue;
            }

            Transaction deposit = new Transaction(
                    LocalDate.now(),
                    java.time.LocalTime.now(),
                    description,
                    vendor,
                    amount
            );

            ledger.addTransaction(deposit);
            System.out.println("Touchdown! Income Logged Successfully.\n");
        }
    }

    // MAKE PAYMENT
    private static void makePayment(TransactionLedger ledger) {
        while (true) {
            String description = ConsoleHelper.prompt("Enter Expense Description (e.g., Travel) [0 to Return]: ");
            if (description.equals("0")) return;

            String vendor = ConsoleHelper.prompt("Enter Expense Vendor or Source: ");

            double amount;
            try {
                amount = Double.parseDouble(ConsoleHelper.prompt("Enter Expense Amount: "));
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println("Flag on the play! Invalid number. Try again.");
                continue;
            }

            if (amount > 0) amount *= -1;

            Transaction payment = new Transaction(
                    LocalDate.now(),
                    java.time.LocalTime.now(),
                    description,
                    vendor,
                    amount
            );

            ledger.addTransaction(payment);
            System.out.println("Expense logged. Good hustle!\n");
        }
    }

    // LEDGER MENU
    private static void showLedgerMenu(TransactionLedger ledger) {
        boolean viewingLedger = true;

        while (viewingLedger) {
            System.out.println("\n==== 🗂️ Team Transactions Menu 🗂 ====");
            System.out.println("A) Show All Transactions");
            System.out.println("I) Show Incomes Only (Show Deposits)");
            System.out.println("E) Show Expenses Only (Show Payments)");
            System.out.println("R) Team Finance Reports (Show Reports Menu)");
            System.out.println("B) Back to Locker Room (Home Page)");

            String choice = ConsoleHelper.prompt("Select an option: ").toUpperCase();

            List<Transaction> allTransactions = new ArrayList<>(ledger.getAllTransactions());
            java.util.Collections.reverse(allTransactions);

            switch (choice) {
                case "A":
                    System.out.println("\n--- All Team Transactions ---");
                    for (Transaction t : allTransactions) System.out.println(t);
                    break;
                case "I":
                    System.out.println("\n--- Team Incomes ---");
                    for (Transaction t : allTransactions)
                        if (t.getAmount() > 0) System.out.println(t);
                    break;
                case "E":
                    System.out.println("\n--- Team Expenses ---");
                    for (Transaction t : allTransactions)
                        if (t.getAmount() < 0) System.out.println(t);
                    break;
                case "R":
                    showReportsMenu(ledger);
                    break;
                case "B":
                    viewingLedger = false;
                    break;
                default:
                    System.out.println("Penalty! Invalid Entry, please try again and enter 'A', 'E', 'R', 'B'.");
            }
        }
    }

    // REPORTS MENU
    private static void showReportsMenu(TransactionLedger ledger) {
        boolean viewingReports = true;

        while (viewingReports) {
            System.out.println("\n==== 📁 Team Finance Reports Menu 📁 ====");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Custom Search");
            System.out.println("0) Back to Team Transactions Menu");

            String choice = ConsoleHelper.prompt("Choose an option: ");

            switch (choice) {
                case "1":
                    showMonthToDate(ledger);
                    break;
                case "2":
                    showPreviousMonth(ledger);
                    break;
                case "3":
                    showYearToDate(ledger);
                    break;
                case "4":
                    showPreviousYear(ledger);
                    break;
                case "5":
                    searchByVendor(ledger);
                    break;
                case "6":
                    customSearch(ledger);
                    break;
                case "0":
                    viewingReports = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // REPORT: Month to Date
    private static void showMonthToDate(TransactionLedger ledger) {
        System.out.println("\n--- Month To Date Transactions ---");
        int currentYear = java.time.LocalDate.now().getYear();
        int currentMonth = java.time.LocalDate.now().getMonthValue();

        for (Transaction t : ledger.getAllTransactions()) {
            if (t.getDate().getYear() == currentYear && t.getDate().getMonthValue() == currentMonth) {
                System.out.println(t);
            }
        }
    }

    // REPORT: Previous Month
    private static void showPreviousMonth(TransactionLedger ledger) {
        System.out.println("\n--- Previous Month Transactions ---");
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.LocalDate previousMonth = today.minusMonths(1);

        for (Transaction t : ledger.getAllTransactions()) {
            if (t.getDate().getYear() == previousMonth.getYear()
                    && t.getDate().getMonthValue() == previousMonth.getMonthValue()) {
                System.out.println(t);
            }
        }
    }

    // REPORT: Year to Date
    private static void showYearToDate(TransactionLedger ledger) {
        System.out.println("\n--- Year To Date Transactions ---");
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.LocalDate startOfYear = java.time.LocalDate.of(today.getYear(), 1, 1);

        for (Transaction t : ledger.getAllTransactions()) {
            if (!t.getDate().isBefore(startOfYear) && !t.getDate().isAfter(today)) {
                System.out.println(t);
            }
        }
    }

    // REPORT: Previous Year
    private static void showPreviousYear(TransactionLedger ledger) {
        System.out.println("\n--- Previous Year Transactions ---");
        int previousYear = java.time.LocalDate.now().getYear() - 1;

        for (Transaction t : ledger.getAllTransactions()) {
            if (t.getDate().getYear() == previousYear) {
                System.out.println(t);
            }
        }
    }

    // REPORT: Search by Vendor
    private static void searchByVendor(TransactionLedger ledger) {

        while (true) {
            String vendorSearch = ConsoleHelper.prompt("Enter vendor name to search (Input 0 to return): ").toLowerCase();

            if (vendorSearch.equals("0")) return;

            boolean found = false;
            System.out.println("\n--- Transactions Matching Vendor: " + vendorSearch + " ---");

            for (Transaction t : ledger.getAllTransactions()) {
                if (t.getVendor().toLowerCase().contains(vendorSearch)) {
                    System.out.println(t);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No transactions found for vendor: " + vendorSearch);
                System.out.println();  // Adds a blank line before the next prompt
            } else {
                System.out.println("Search complete. Returning to Reports Menu...");
                return;
            }
        }
    }
    // REPORT: Custom Search
    private static void customSearch(TransactionLedger ledger) {
        System.out.println("\n--- Custom Search ---");

        String startDateInput = ConsoleHelper.prompt("Start Date (yyyy-mm-dd, optional): ");
        String endDateInput = ConsoleHelper.prompt("End Date (yyyy-mm-dd, optional): ");
        String descriptionInput = ConsoleHelper.prompt("Description contains (optional): ").toLowerCase();
        String vendorInput = ConsoleHelper.prompt("Vendor contains (optional): ").toLowerCase();
        String amountInput = ConsoleHelper.prompt("Exact Amount (optional): ");

        LocalDate startDate = null, endDate = null;
        Double amount = null;

        try {
            if (!startDateInput.isEmpty()) startDate = LocalDate.parse(startDateInput);
            if (!endDateInput.isEmpty()) endDate = LocalDate.parse(endDateInput);
            if (!amountInput.isEmpty()) amount = Double.parseDouble(amountInput);
        } catch (Exception e) {
            System.out.println("Invalid input format. Please try again.");
            return;
        }

        List<Transaction> allTransactions = ledger.getAllTransactions();
        List<Transaction> filtered = new ArrayList<>();

        for (Transaction t : allTransactions) {
            boolean match = true;

            if (startDate != null && t.getDate().isBefore(startDate)) match = false;
            if (endDate != null && t.getDate().isAfter(endDate)) match = false;
            if (!descriptionInput.isEmpty() && !t.getDescription().toLowerCase().contains(descriptionInput)) match = false;
            if (!vendorInput.isEmpty() && !t.getVendor().toLowerCase().contains(vendorInput)) match = false;
            if (amount != null && t.getAmount() != amount) match = false;

            if (match) filtered.add(t);
        }

        if (filtered.isEmpty()) {
            System.out.println("No transactions match your search criteria.");
        } else {
            System.out.println("\n--- Search Results ---");
            for (Transaction t : filtered) {
                System.out.println(t);
            }
        }
    }
}