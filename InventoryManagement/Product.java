package InventoryManagement;

/**
 * Represents a warehouse product item.
 */
public class Product {
    private static int NEXT_ID = 1;

    private final int id;
    private String name;
    private int quantity;
    private double price;
    private int reorderLevel;

    public Product(String name, int quantity, double price, int reorderLevel) {
        if (quantity < 0) throw new IllegalArgumentException("Quantity cannot be negative");
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative");
        if (reorderLevel < 0) throw new IllegalArgumentException("Reorder level cannot be negative");
        this.id = NEXT_ID++;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.reorderLevel = reorderLevel;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public int getReorderLevel() { return reorderLevel; }

    public void addStock(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Add quantity must be positive");
        quantity += qty;
    }

    public void removeStock(int qty) throws InsufficientStockException {
        if (qty <= 0) throw new IllegalArgumentException("Remove quantity must be positive");
        if (qty > quantity) throw new InsufficientStockException("Not enough stock.");
        quantity -= qty;
    }

    public boolean needsReorder() {
        return quantity <= reorderLevel;
    }

    public double totalValue() {
        return quantity * price;
    }

    @Override
    public String toString() {
        return String.format("#%d | %s | Qty:%d | Price:%.2f | Reorder:%d", id, name, quantity, price, reorderLevel);
    }
}

class InsufficientStockException extends Exception { public InsufficientStockException(String m){super(m);} }
class ProductNotFoundException extends Exception { public ProductNotFoundException(String m){super(m);} }
