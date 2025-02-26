public class StockPredictor {

    // Predicts how many days before stock runs out
    public static int predictStockDepletion(Product product, int avgDailySales) {
        if (avgDailySales <= 0) return Integer.MAX_VALUE; // Avoid division by zero
        return product.getStockLevel() / avgDailySales;
    }

    // Suggests when to restock based on stock level and sales
    public static String suggestRestocking(Product product, int avgDailySales) {
        int daysRemaining = predictStockDepletion(product, avgDailySales);

        if (daysRemaining == Integer.MAX_VALUE) {
            return "⚠️ Error: Invalid sales data.";
        }

        if (daysRemaining <= 5) {
            return "🚨 Urgent: Restock immediately!";
        } else if (product.getStockLevel() <= product.getReorderThreshold()) {
            return "🔄 Moderate: Consider restocking soon.";
        }
        return "✅ Stock is sufficient.";
    }
}
