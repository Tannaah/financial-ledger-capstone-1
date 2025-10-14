package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionLedger {
    private List<Transaction> transactions = new ArrayList<>();
    private final String fileName = "transactions.csv";

    public TransactionLedger() {
        loadTransactions();
    }

    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        saveTransactionToFile(transaction);
    }

    private void loadTransactions() {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);

                    Transaction t = new Transaction(date, time, description, vendor, amount);
                    transactions.add(t);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading transactions from file: " + e.getMessage());
        }
    }
    private void saveTransactionToFile(Transaction transaction) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.printf("\n%s|%s|%s|%s|%.2f%n",
                    transaction.getDate(),
                    transaction.getTime(),
                    transaction.getDescription(),
                    transaction.getVendor(),
                    transaction.getAmount());
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }
}