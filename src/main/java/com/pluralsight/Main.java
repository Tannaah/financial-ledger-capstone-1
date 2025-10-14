package com.pluralsight;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TransactionLedger ledger = new TransactionLedger();

        boolean running = true;
        while (running) {
            System.out.println("\n==== Home Menu ====");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
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
                    // Make payment
                    break;
                case "L":
                    // Show ledger
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
}