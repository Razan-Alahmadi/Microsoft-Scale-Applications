import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        OrderManager orderManager = new OrderManager();

        Scanner scanner = new Scanner(System.in);

        // Create products 1 and update its status asynchronously
        Product laptop = new Product("Laptop", 1200.00, 20, 5);
        Order laptopOrder = new Order("Razan", laptop.getProductId(), 5);
        orderManager.processOrder(laptopOrder);

        // Create products 2 and update its status asynchronously
        Product smartphone = new Product("Smartphone", 800.00, 10, 3);
        Order smartphoneOrder = new Order("Reham", smartphone.getProductId(), 8);
        orderManager.processOrder(smartphoneOrder);


        // Wait a moment to let the order processing happen (for demonstration purposes)
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Shutdown the order manager after processing
        orderManager.shutdown();

        //----------------------------------------------------
        // Get user input for average daily sales
        System.out.println("✨-- Product details and Display stock prediction and restock suggestion --✨");
        System.out.println("Enter average daily sales for " + laptop.getName() + ": ");
        int laptopSales = scanner.nextInt();

        System.out.print("Enter average daily sales for " + smartphone.getName() + ": ");
        int smartphoneSales = scanner.nextInt();

        // Display product details
        displayProductInfo(laptop);
        displayProductInfo(smartphone);

        // Display stock prediction and restock suggestion
        System.out.println("Stock Prediction for " + laptop.getName() + ":");
        System.out.println("Days until stock out: " + StockPredictor.predictStockDepletion(laptop, laptopSales));
        System.out.println("Restock Suggestion: " + StockPredictor.suggestRestocking(laptop, laptopSales));
        System.out.println("----------------------------");
        System.out.println("Stock Prediction for " + smartphone.getName() + ":");
        System.out.println("Days until stock out: " + StockPredictor.predictStockDepletion(smartphone, smartphoneSales));
        System.out.println("Restock Suggestion: " + StockPredictor.suggestRestocking(smartphone, smartphoneSales));

        scanner.close();
    }

    // display product information
    public static void displayProductInfo(Product product) {
        System.out.println("----------------------------");
        System.out.println("Product ID: " + product.getProductId());
        System.out.println("Name: " + product.getName());
        System.out.println("Price: $" + product.getPrice());
        System.out.println("Stock Level: " + product.getStockLevel());
        System.out.println("Reorder Threshold: " + product.getReorderThreshold());
        System.out.println("----------------------------");

    }
}
