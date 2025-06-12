package ExpenseTracker;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Expense Tracker CLI
 * Features: add expense, category total, monthly list, budget check, list all, exit
 */
public class ExpenseTrackerCLI {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final List<Expense> EXPENSES = new ArrayList<>();

    // simple category budgets
    private static final List<String> CATEGORIES = List.of("Food", "Transport", "Entertainment", "Bills", "Other");
    private static final double[] CATEGORY_BUDGETS = {300, 150, 200, 400, 100};

    public static void main(String[] args) {
        System.out.println("===== Expense Tracker =====");
        while (true) {
            printMenu();
            int choice = readInt("Choose an option: ");
            try {
                switch (choice) {
                    case 1 -> addExpense();
                    case 2 -> categoryTotal();
                    case 3 -> monthlyExpenses();
                    case 4 -> budgetStatus();
                    case 5 -> listExpenses();
                    case 6 -> {
                        System.out.println("Exiting... Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Select 1-6");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add Expense");
        System.out.println("2. Get Total by Category");
        System.out.println("3. View Expenses by Month");
        System.out.println("4. Check Budget Status");
        System.out.println("5. List All Expenses");
        System.out.println("6. Exit");
    }

    // ---- operations ---- //

    private static void addExpense() {
        double amount = readDouble("Enter amount: ");
        System.out.print("Enter category (Food/Transport/Entertainment/Bills/Other): ");
        String category = SCANNER.nextLine().trim();
        if (!CATEGORIES.contains(category)) {
            System.out.println("Unknown category. Using 'Other'.");
            category = "Other";
        }
        System.out.print("Enter date (YYYY-MM-DD) or leave blank for today: ");
        String dateStr = SCANNER.nextLine().trim();
        LocalDate date = LocalDate.now();
        if (!dateStr.isEmpty()) {
            try {
                date = LocalDate.parse(dateStr);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Using today.");
            }
        }
        System.out.print("Enter description (optional): ");
        String desc = SCANNER.nextLine().trim();

        Expense exp = new Expense(amount, category, date, desc);
        EXPENSES.add(exp);
        System.out.println("Added: " + exp);
    }

    private static void categoryTotal() {
        System.out.print("Enter category: ");
        String cat = SCANNER.nextLine().trim();
        double total = EXPENSES.stream()
                .filter(e -> e.getCategory().equalsIgnoreCase(cat))
                .mapToDouble(Expense::getAmount)
                .sum();
        System.out.printf("Total for %s: %.2f%n", cat, total);
    }

    private static void monthlyExpenses() {
        int month = readInt("Enter month (1-12): ");
        int year = readInt("Enter year (e.g., 2025): ");
        List<Expense> list = new ArrayList<>();
        for (Expense e : EXPENSES) {
            if (e.getDate().getMonthValue() == month && e.getDate().getYear() == year) {
                list.add(e);
            }
        }
        if (list.isEmpty()) {
            System.out.println("No expenses for specified month.");
        } else {
            System.out.println("\n--- Expenses ---");
            list.forEach(System.out::println);
        }
    }

    private static void budgetStatus() {
        System.out.print("Enter category: ");
        String cat = SCANNER.nextLine().trim();
        int idx = CATEGORIES.indexOf(cat);
        if (idx == -1) {
            System.out.println("Unknown category.");
            return;
        }
        double total = EXPENSES.stream()
                .filter(e -> e.getCategory().equalsIgnoreCase(cat))
                .mapToDouble(Expense::getAmount)
                .sum();
        double budget = CATEGORY_BUDGETS[idx];
        System.out.printf("Spent %.2f / %.2f on %s (%s) %n", total, budget, cat, total > budget ? "Over budget" : "Within budget");
    }

    private static void listExpenses() {
        if (EXPENSES.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }
        System.out.println("\n--- All Expenses ---");
        EXPENSES.forEach(System.out::println);
    }

    // ---- helpers ---- //

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(SCANNER.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter valid integer.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = Double.parseDouble(SCANNER.nextLine().trim());
                if (v <= 0) {
                    System.out.println("Amount must be positive.");
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Enter numeric value.");
            }
        }
    }
}
