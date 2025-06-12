package ExpenseTracker;

import java.time.LocalDate;

/**
 * Represents a single expense entry.
 */
public class Expense {
    private static int NEXT_ID = 1;

    private final int id;
    private final double amount;
    private final String category;
    private final LocalDate date;
    private final String description;

    public Expense(double amount, String category, LocalDate date, String description) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (category == null || category.isBlank()) throw new IllegalArgumentException("Category required");
        this.id = NEXT_ID++;
        this.amount = amount;
        this.category = category;
        this.date = date == null ? LocalDate.now() : date;
        this.description = description == null ? "" : description;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("#%d | %-10s | %s | %.2f", id, category, date, amount);
    }
}
