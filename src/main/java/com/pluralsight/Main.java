package com.pluralsight;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TransactionLedger ledger = new TransactionLedger();

        boolean running = true;
        while (running) {
            System.out.println("\n==== Home Menu ====");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make a Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim().toUpperCase();

            //todo make it look cleaner with Methods, (Reports Menu)
            switch (choice) {
                case "D":
                    System.out.print("Enter description: ");
                    String depositDescription = scanner.nextLine();

                    System.out.print("Enter vendor: ");
                    String depositVendor = scanner.nextLine();

                    System.out.print("Enter amount: ");
                    double depositAmount = Double.parseDouble(scanner.nextLine());
                    //todo fix error

                    Transaction deposit = new Transaction(
                            java.time.LocalDate.now(),
                            java.time.LocalTime.now(),
                            depositDescription,
                            depositVendor,
                            depositAmount
                    );
                    ledger.addTransaction(deposit);
                    System.out.println("Deposit added successfully!");
                    break;

                case "P":
                    System.out.print("Enter description: ");
                    String paymentDescription = scanner.nextLine();

                    System.out.print("Enter vendor: ");
                    String paymentVendor = scanner.nextLine();

                    System.out.print("Enter amount: ");
                    double paymentAmount = Double.parseDouble(scanner.nextLine());
                    //todo fix error

                    // Make sure the amount is stored as a negative value
                    if (paymentAmount > 0) {
                        paymentAmount *= -1;
                    }
                    Transaction payment = new Transaction(
                            java.time.LocalDate.now(),
                            java.time.LocalTime.now(),
                            paymentDescription,
                            paymentVendor,
                            paymentAmount
                    );

                    ledger.addTransaction(payment);
                    System.out.println("Payment added successfully!");
                    break;
                case "L":
                    showLedgerMenu(ledger, scanner);
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        System.out.println("Thanks for choosing Tanner's Financial Ledger, Have a nice day!");
    }

    private static void showLedgerMenu(TransactionLedger ledger, Scanner scanner) {
        boolean viewingLedger = true;

        while (viewingLedger) {
            System.out.println("\n==== Ledger Menu ====");
            System.out.println("A) Show All Transactions");
            System.out.println("D) Show Deposits Only");
            System.out.println("P) Show Payments Only");
            System.out.println("R) Show Reports");
            System.out.println("H) Home");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim().toUpperCase();

            // Get transactions in reverse order (newest first)
            List<Transaction> allTransactions = new ArrayList<>(ledger.getAllTransactions());
            java.util.Collections.reverse(allTransactions);

            switch (choice) {
                case "A":
                    System.out.println("\n--- All Transactions ---");
                    for (Transaction t : allTransactions) {
                        System.out.println(t);
                    }
                    break;

                case "D":
                    System.out.println("\n--- Deposits Only ---");
                    for (Transaction t : allTransactions) {
                        if (t.getAmount() > 0) {
                            System.out.println(t);
                        }
                    }
                    break;

                case "P":
                    System.out.println("\n--- Payments Only ---");
                    for (Transaction t : allTransactions) {
                        if (t.getAmount() < 0) {
                            System.out.println(t);
                        }
                    }
                    break;

                case "R":
                    showReportsMenu(ledger, scanner);

                    break;

                case "H":
                    viewingLedger = false;
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void showReportsMenu(TransactionLedger ledger, Scanner scanner) {
        boolean viewingReports = true;

        while (viewingReports) {
            System.out.println("\n==== Reports Menu ====");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back to Ledger Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    showMonthToDate(ledger);
                    break;
                case "2":
                    showPreviousMonth(ledger);
                    break;
                case "3":
                    //showYearToDate(ledger);
                    break;
                case "4":
                    //showPreviousYear(ledger);
                    break;
                case "5":
                    //searchByVendor(ledger, scanner);
                    break;
                case "0":
                    viewingReports = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void showMonthToDate(TransactionLedger ledger) {
        System.out.println("\n--- Month To Date Transactions ---");

        int currentYear = java.time.LocalDate.now().getYear();
        int currentMonth = java.time.LocalDate.now().getMonthValue();

        List<Transaction> allTransactions = ledger.getAllTransactions();

        for (Transaction t : allTransactions) {
            if (t.getDate().getYear() == currentYear && t.getDate().getMonthValue() == currentMonth) {
                System.out.println(t);
            }
        }
    }

    private static void showPreviousMonth(TransactionLedger ledger) {
        System.out.println("\n--- Previous Month Transactions ---");

        //Get current date and subtract one month
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.LocalDate previousMonthDate = today.minusMonths(1);

        int prevMonth = previousMonthDate.getMonthValue();
        int prevYear = previousMonthDate.getYear();

        //Filter and print transactions from the previous month
        for (Transaction t : ledger.getAllTransactions()) {
            if (t.getDate().getMonthValue() == prevMonth && t.getDate().getYear() == prevYear) {
                System.out.println(t);
            }
        }
    }
}