package com.pluralsight;

import java.util.Scanner;

public class ConsoleHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    public static double promptForDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    public static Scanner getScanner() {
        return scanner;
    }
}
