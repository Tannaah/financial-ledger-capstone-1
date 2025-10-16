package com.pluralsight;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TransactionLedger ledger = new TransactionLedger();

        showHomeMenu(ledger, scanner);
    }
    // HOME MENU
    private static void showHomeMenu(TransactionLedger ledger, Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("\n==== Home Menu ====");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make a Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String choice = ConsoleHelper.prompt("Choose an option: ").toUpperCase();

            switch (choice) {
                case "D":
                    addDeposit(ledger);
                    break;
                case "P":
                    makePayment(ledger);
                    break;
                case "L":
                    showLedgerMenu(ledger, scanner);
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
            System.out.println("Thanks for choosing Tanner's Financial Ledger. Have a nice day!");
        }
    }

    // ADD DEPOSIT
    private static void addDeposit(TransactionLedger ledger) {
        while (true) {
            String description = ConsoleHelper.prompt("Enter description (Input 0 to return): ");
            if (description.equals("0")) return;

            String vendor = ConsoleHelper.prompt("Enter vendor: ");

            double amount;
            try {
                amount = Double.parseDouble(ConsoleHelper.prompt("Enter amount: "));
            } catch (NumberFormatException e) {
                System.out.println(); // Add this line to create a blank line
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            Transaction deposit = new Transaction(
                    java.time.LocalDate.now(),
                    java.time.LocalTime.now(),
                    description,
                    vendor,
                    amount
            );

            ledger.addTransaction(deposit);
            System.out.println("Deposit added successfully!");
        }
    }

    // MAKE PAYMENT
    private static void makePayment(TransactionLedger ledger) {
        while (true) {
            String description = ConsoleHelper.prompt("Enter description (Input 0 to return): ");
            if (description.equals("0")) return;

            String vendor = ConsoleHelper.prompt("Enter vendor: ");

            double amount;
            try {
                amount = Double.parseDouble(ConsoleHelper.prompt("Enter amount: "));
            } catch (NumberFormatException e) {
                System.out.println(); // Adds a blank line before the error message
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            if (amount > 0) amount *= -1;

            Transaction payment = new Transaction(
                    java.time.LocalDate.now(),
                    java.time.LocalTime.now(),
                    description,
                    vendor,
                    amount
            );

            ledger.addTransaction(payment);
            System.out.println("Payment added successfully!");
        }
    }

    // LEDGER MENU
    private static void showLedgerMenu(TransactionLedger ledger, Scanner scanner) {
        boolean viewingLedger = true;

        while (viewingLedger) {
            System.out.println("\n==== Ledger Menu ====");
            System.out.println("A) Show All Transactions");
            System.out.println("D) Show Deposits Only");
            System.out.println("P) Show Payments Only");
            System.out.println("R) Show Reports");
            System.out.println("H) Home");

            String choice = ConsoleHelper.prompt("Choose an option: ").toUpperCase();

            List<Transaction> allTransactions = new ArrayList<>(ledger.getAllTransactions());
            java.util.Collections.reverse(allTransactions);

            switch (choice) {
                case "A":
                    System.out.println("\n--- All Transactions ---");
                    for (Transaction t : allTransactions) System.out.println(t);
                    break;
                case "D":
                    System.out.println("\n--- Deposits Only ---");
                    for (Transaction t : allTransactions)
                        if (t.getAmount() > 0) System.out.println(t);
                    break;
                case "P":
                    System.out.println("\n--- Payments Only ---");
                    for (Transaction t : allTransactions)
                        if (t.getAmount() < 0) System.out.println(t);
                    break;
                case "R":
                    showReportsMenu(ledger);
                    break;
                case "H":
                    viewingLedger = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // REPORTS MENU
    private static void showReportsMenu(TransactionLedger ledger) {
        boolean viewingReports = true;

        while (viewingReports) {
            System.out.println("\n==== Reports Menu ====");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back to Ledger Menu");

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
}