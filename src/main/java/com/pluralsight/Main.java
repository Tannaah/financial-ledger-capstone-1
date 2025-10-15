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

            switch (choice) {
                case "D":
                    System.out.print("Enter description: ");
                    String depositDescription = scanner.nextLine();

                    System.out.print("Enter vendor: ");
                    String depositVendor = scanner.nextLine();

                    System.out.print("Enter amount: ");
                    double depositAmount = Double.parseDouble(scanner.nextLine());

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
        } System.out.println("Thanks for choosing Tanner's Financial Ledger, Have a nice day!");
    }

    private static void showLedgerMenu(TransactionLedger ledger, Scanner scanner) {
        boolean viewingLedger = true;

        while (viewingLedger) {
            System.out.println("\n==== Ledger Menu ====");
            System.out.println("A) Show All Transactions");
            System.out.println("D) Show Deposits Only");
            System.out.println("P) Show Payments Only");
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

                case "H":
                    viewingLedger = false;
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

}

