import java.util.UUID;

public class Product {
    private UUID productId;
    private String name;
    private double price;
    private int stockLevel;
    private int reorderThreshold;

    public Product(String name, double price, int stockLevel, int reorderThreshold) {
        this.productId = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.stockLevel = Math.max(stockLevel, 0);
        this.reorderThreshold = Math.max(reorderThreshold, 0);
    }

    // Getters
    public UUID getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStockLevel() { return stockLevel; }
    public int getReorderThreshold() { return reorderThreshold; }

    // Setters
    public void setStockLevel(int stockLevel) {
        this.stockLevel = Math.max(stockLevel, 0);
    }
}
