import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import java.util.UUID;

// Here OrderManager class , Written by User Razan

public class OrderManager {
    private static final Logger logger = Logger.getLogger(OrderManager.class.getName());
    private ExecutorService executorService;
    private static final String FILE_NAME = "orders.txt";
    private final List<Order> orders;

    public OrderManager() {
        executorService = Executors.newFixedThreadPool(5); // Handles multiple orders simultaneously
        orders = loadOrdersFromFile();
    }

    public synchronized void processOrder(Order order) {
        orders.add(order);
        saveOrdersToFile();

        executorService.submit(() -> {
            try {
                System.out.println("✨ Processing order: " + order.getOrderId() + " ✨");
                Thread.sleep(2000);

                synchronized (this) {
                    order.setOrderStatus(Order.OrderStatus.SHIPPED);
                    logger.info("1- Order " + order.getOrderId() + " status updated to SHIPPED.");
                }
                Thread.sleep(1000);

                synchronized (this) {
                    order.setOrderStatus(Order.OrderStatus.DELIVERED);
                    logger.info("2- Order " + order.getOrderId() + " status updated to DELIVERED.");
                }

                saveOrdersToFile();
            } catch (InterruptedException e) {
                logger.severe("Error processing order " + order.getOrderId() + ": " + e.getMessage());
            }
        });
    }

    private synchronized void saveOrdersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Order order : orders) {
                writer.write("Order ID: " + order.getOrderId() + "\n");
                writer.write("Customer: " + order.getCustomerName() + "\n");
                writer.write("Product ID: " + order.getProductId() + "\n");
                writer.write("Quantity: " + order.getQuantity() + "\n");
                writer.write("Status: " + order.getOrderStatus() + "\n");
                writer.write("-------------------------------\n");
            }
        } catch (IOException e) {
            logger.severe("Error saving orders to file: " + e.getMessage());
        }
    }


    private List<Order> loadOrdersFromFile() {
        List<Order> loadedOrders = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) return loadedOrders;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    Order order = new Order(data[1], UUID.fromString(data[2]), Integer.parseInt(data[3]));
                    order.setOrderStatus(Order.OrderStatus.valueOf(data[4]));
                    loadedOrders.add(order);
                }
            }
        } catch (IOException e) {
            logger.severe("Error loading orders from file: " + e.getMessage());
        }
        return loadedOrders;
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
