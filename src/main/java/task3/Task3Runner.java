package task3;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Task3Runner - class for running Task #3
 * Demonstrates using ExecutorService for Producer-Consumer pattern
 */
public class Task3Runner {

    private static final int TOTAL_ORDERS = 20;
    private static final int MAX_QUEUE_SIZE = 5;
    private static final int NUMBER_OF_CONSUMERS = 4;

    public static void run() {
        System.out.println("Description: Producer-Consumer with ExecutorService");
        System.out.println("   - FixedThreadPool for consumer threads");
        System.out.println("   - SingleThreadExecutor for producer thread");
        System.out.println("   - Proper shutdown via shutdown()");
        System.out.println("   - Total orders: " + TOTAL_ORDERS + "\n");

        // Create warehouse with ExecutorService
        ShoeWarehouse warehouse = new ShoeWarehouse(MAX_QUEUE_SIZE, NUMBER_OF_CONSUMERS);

        // Create ExecutorService for order submission
        ExecutorService producerExecutor = Executors.newSingleThreadExecutor();

        System.out.println(">> Starting ExecutorService...\n");

        // Submit Producer task
        producerExecutor.submit(() -> {
            Random random = new Random();
            try {
                for (int i = 1; i <= TOTAL_ORDERS; i++) {
                    // Generate random order
                    String shoeType = ShoeWarehouse.SHOE_TYPES.get(
                            random.nextInt(ShoeWarehouse.SHOE_TYPES.size())
                    );
                    int quantity = random.nextInt(10) + 1;
                    Order order = new Order(i, shoeType, quantity);

                    // Send order to warehouse
                    warehouse.receiveOrder(order);

                    // Small delay between orders
                    Thread.sleep(50);
                }
                System.out.println("\n  [END] Producer finished producing orders");
            } catch (InterruptedException e) {
                System.err.println("Producer interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        });

        // Submit Consumer tasks via warehouse ExecutorService
        warehouse.submitConsumerTasks(TOTAL_ORDERS);

        // Properly shutdown ExecutorService
        producerExecutor.shutdown();
        warehouse.shutdown();

        try {
            // Wait for all tasks to complete (max 30 seconds)
            if (!producerExecutor.awaitTermination(30, TimeUnit.SECONDS)) {
                System.err.println("Producer executor did not terminate in time");
                producerExecutor.shutdownNow();
            }

            if (!warehouse.isTerminated()) {
                Thread.sleep(5000); // Additional time for completion
            }

            System.out.println("\n+ All orders processed successfully with ExecutorService");
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
