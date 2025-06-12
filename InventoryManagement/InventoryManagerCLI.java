package InventoryManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Inventory Management CLI
 */
public class InventoryManagerCLI {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final List<Product> PRODUCTS = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("===== Inventory Manager =====");
        while (true) {
            printMenu();
            int choice = readInt("Choose an option: ");
            try {
                switch (choice) {
                    case 1 -> addProduct();
                    case 2 -> addStock();
                    case 3 -> removeStock();
                    case 4 -> showLowStock();
                    case 5 -> inventoryValue();
                    case 6 -> listProducts();
                    case 7 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add Product");
        System.out.println("2. Add Stock");
        System.out.println("3. Remove Stock");
        System.out.println("4. Show Low-Stock Items");
        System.out.println("5. Calculate Inventory Value");
        System.out.println("6. List All Products");
        System.out.println("7. Exit");
    }

    private static Product findProduct(int id) {
        return PRODUCTS.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    private static void addProduct() {
        System.out.print("Name: ");
        String name = SCANNER.nextLine().trim();
        int qty = readInt("Initial quantity: ");
        double price = readDouble("Price per unit: ");
        int reorder = readInt("Reorder level: ");
        PRODUCTS.add(new Product(name, qty, price, reorder));
        System.out.println("Product added.");
    }

    private static void addStock() {
        int id = readInt("Product ID: ");
        Product p = findProduct(id);
        if (p == null) { System.out.println("Not found."); return; }
        int qty = readInt("Quantity to add: ");
        p.addStock(qty);
        System.out.println("Updated: " + p);
    }

    private static void removeStock() throws InsufficientStockException {
        int id = readInt("Product ID: ");
        Product p = findProduct(id);
        if (p == null) { System.out.println("Not found."); return; }
        int qty = readInt("Quantity to remove: ");
        p.removeStock(qty);
        System.out.println("Updated: " + p);
    }

    private static void showLowStock() {
        System.out.println("Low-stock products:");
        PRODUCTS.stream().filter(Product::needsReorder).forEach(System.out::println);
    }

    private static void inventoryValue() {
        double total = PRODUCTS.stream().mapToDouble(Product::totalValue).sum();
        System.out.printf("Total inventory value: %.2f%n", total);
    }

    private static void listProducts() {
        if (PRODUCTS.isEmpty()) { System.out.println("No products."); return; }
        PRODUCTS.forEach(System.out::println);
    }

    // helpers
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Integer.parseInt(SCANNER.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Enter integer."); }
        }
    }
    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Double.parseDouble(SCANNER.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Enter number."); }
        }
    }
}
