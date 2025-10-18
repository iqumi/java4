package task3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ShoeWarehouse - shoe warehouse using ExecutorService
 * Modified version of Task #2 with thread pools
 */
public class ShoeWarehouse {

    // Public static field with list of product types
    public static final List<String> SHOE_TYPES = Arrays.asList(
            "Nike Air Max",
            "Adidas campus",
            "Puma Suede",
            "Reebok Classic",
            "New Balance 574"
    );

    // Private list of orders (FIFO queue)
    private final List<Order> orderQueue;

    // Maximum queue size
    private final int maxQueueSize;

    // ExecutorService for order processing
    private final ExecutorService consumerExecutor;

    /**
     * Warehouse constructor
     * @param maxQueueSize maximum order queue size
     * @param numberOfConsumers number of threads for order processing
     */
    public ShoeWarehouse(int maxQueueSize, int numberOfConsumers) {
        this.orderQueue = new ArrayList<>();
        this.maxQueueSize = maxQueueSize;
        // Create FixedThreadPool for order processing
        this.consumerExecutor = Executors.newFixedThreadPool(numberOfConsumers);
    }

    /**
     * Method for receiving order (called by Producer)
     * @param order order to add to queue
     * @throws InterruptedException if thread is interrupted
     */
    public synchronized void receiveOrder(Order order) throws InterruptedException {
        // Wait if queue is full
        while (orderQueue.size() >= maxQueueSize) {
            System.out.println("  [!] Queue is FULL. Producer waiting...");
            wait();
        }

        // Add order to queue
        orderQueue.add(order);
        System.out.println("  [+] Order received: " + order);

        // Notify all waiting threads
        notifyAll();
    }

    /**
     * Method for processing order (called by Consumer)
     * @return processed order
     * @throws InterruptedException if thread is interrupted
     */
    public synchronized Order fulfillOrder() throws InterruptedException {
        // Wait if queue is empty
        while (orderQueue.isEmpty()) {
            wait();
        }

        // Extract first order (FIFO)
        Order order = orderQueue.remove(0);
        System.out.println("  [v] Order fulfilled: " + order + " [" + Thread.currentThread().getName() + "]");

        // Notify all waiting threads
        notifyAll();

        return order;
    }

    /**
     * Submit order processing tasks to ExecutorService
     * @param numberOfOrders number of orders to process
     */
    public void submitConsumerTasks(int numberOfOrders) {
        for (int i = 0; i < numberOfOrders; i++) {
            consumerExecutor.submit(() -> {
                try {
                    fulfillOrder();
                    // Simulate order processing
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.err.println(Thread.currentThread().getName() + " interrupted: " + e.getMessage());
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    /**
     * Properly shutdown ExecutorService
     */
    public void shutdown() {
        consumerExecutor.shutdown();
    }

    /**
     * Check if all tasks are completed
     * @return true if all tasks are completed
     */
    public boolean isTerminated() {
        return consumerExecutor.isTerminated();
    }
}
