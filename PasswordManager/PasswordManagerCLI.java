package PasswordManager;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Password Manager CLI (basic)
 */
public class PasswordManagerCLI {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final List<Password> STORED = new ArrayList<>();

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()-_=+[]{};:,.<>/?";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static void main(String[] args) {
        System.out.println("===== Password Manager =====");
        while (true) {
            printMenu();
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1 -> generatePasswordFlow();
                case 2 -> addPasswordEntry();
                case 3 -> listPasswords();
                case 4 -> findWeakPasswords();
                case 5 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Generate Password");
        System.out.println("2. Add Password Entry");
        System.out.println("3. List Stored Passwords");
        System.out.println("4. Find Weak Passwords");
        System.out.println("5. Exit");
    }

    // ---- operations ---- //

    private static void generatePasswordFlow() {
        int len = readInt("Length: ");
        System.out.print("Include special characters? (y/n): ");
        boolean includeSpecial = SCANNER.nextLine().trim().equalsIgnoreCase("y");
        String pwd = generatePassword(len, includeSpecial);
        System.out.println("Generated password: " + pwd);
        System.out.println("Strength score: " + calculateStrength(pwd));
    }

    private static void addPasswordEntry() {
        System.out.print("Service: ");
        String service = SCANNER.nextLine().trim();
        System.out.print("Username: ");
        String user = SCANNER.nextLine().trim();
        System.out.print("Password (leave blank to generate): ");
        String pwd = SCANNER.nextLine().trim();
        if (pwd.isBlank()) {
            pwd = generatePassword(12, true);
            System.out.println("Generated: " + pwd);
        }
        STORED.add(new Password(service, user, pwd));
        System.out.println("Saved entry.");
    }

    private static void listPasswords() {
        if (STORED.isEmpty()) {
            System.out.println("No passwords stored.");
            return;
        }
        System.out.println("Stored passwords (decoded displayed for demo):");
        STORED.forEach(p -> System.out.printf("%s | %s%n", p, p.getDecodedPassword()));
    }

    private static void findWeakPasswords() {
        List<String> weak = new ArrayList<>();
        for (Password p : STORED) {
            if (!isPasswordSecure(p.getDecodedPassword())) weak.add(p.toString());
        }
        if (weak.isEmpty()) System.out.println("No weak passwords.");
        else {
            System.out.println("Weak entries:");
            weak.forEach(System.out::println);
        }
    }

    // ---- password utils ---- //

    public static String generatePassword(int length, boolean includeSpecial) {
        String chars = UPPER + LOWER + DIGITS + (includeSpecial ? SPECIAL : "");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(RANDOM.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static int calculateStrength(String pwd) {
        int score = 0;
        if (pwd.length() >= 8) score += 2;
        if (pwd.length() >= 12) score += 2;
        if (pwd.chars().anyMatch(Character::isUpperCase)) score += 2;
        if (pwd.chars().anyMatch(Character::isLowerCase)) score += 2;
        if (pwd.chars().anyMatch(Character::isDigit)) score += 2;
        if (pwd.chars().anyMatch(ch -> SPECIAL.indexOf(ch) != -1)) score += 2;
        return score; // up to 12
    }

    public static boolean isPasswordSecure(String pwd) {
        return calculateStrength(pwd) >= 8;
    }
}
