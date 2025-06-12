package BankAccount;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Banking Account Manager (CLI)
 * Supports creating accounts, depositing, withdrawing, checking balance, and listing all accounts.
 */
public class BankingAccountManager {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final List<Account> ACCOUNTS = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("===== Banking Account Manager =====");
        while (true) {
            printMenu();
            int choice = readInt("Choose an option: ");
            try {
                switch (choice) {
                    case 1 -> createAccount();
                    case 2 -> deposit();
                    case 3 -> withdraw();
                    case 4 -> checkBalance();
                    case 5 -> listAccounts();
                    case 6 -> {
                        System.out.println("Exiting... Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please select 1-6.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Check Balance");
        System.out.println("5. List All Accounts");
        System.out.println("6. Exit");
    }

    // ---- Operations ---- //

    private static void createAccount() {
        System.out.print("Enter account holder name: ");
        String name = SCANNER.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        double initial = readDouble("Enter initial deposit: ");
        Account acc = new Account(name, initial);
        ACCOUNTS.add(acc);
        System.out.println("Account created: " + acc);
    }

    private static Account findAccount(int accNumber) {
        return ACCOUNTS.stream()
                .filter(a -> a.getAccountNumber() == accNumber)
                .findFirst()
                .orElse(null);
    }

    private static void deposit() {
        int accNum = readInt("Enter account number: ");
        Account acc = findAccount(accNum);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }
        double amt = readDouble("Enter deposit amount: ");
        acc.deposit(amt);
        System.out.println("New balance: " + acc.getBalance());
    }

    private static void withdraw() throws InsufficientFundsException {
        int accNum = readInt("Enter account number: ");
        Account acc = findAccount(accNum);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }
        double amt = readDouble("Enter withdrawal amount: ");
        acc.withdraw(amt);
        System.out.println("New balance: " + acc.getBalance());
    }

    private static void checkBalance() {
        int accNum = readInt("Enter account number: ");
        Account acc = findAccount(accNum);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }
        System.out.printf("Balance for #%d: %.2f%n", acc.getAccountNumber(), acc.getBalance());
    }

    private static void listAccounts() {
        if (ACCOUNTS.isEmpty()) {
            System.out.println("No accounts available.");
            return;
        }
        System.out.println("\n--- Account List ---");
        ACCOUNTS.forEach(System.out::println);
    }

    // ---- Input helpers ---- //

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String line = SCANNER.nextLine();
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String line = SCANNER.nextLine();
                double value = Double.parseDouble(line.trim());
                if (value < 0) {
                    System.out.println("Amount must be non-negative.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a numeric value.");
            }
        }
    }
}
