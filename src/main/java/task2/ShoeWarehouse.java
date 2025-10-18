package task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ShoeWarehouse - shoe warehouse implementing Producer-Consumer pattern
 * Uses wait() and notifyAll() for thread synchronization
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

    /**
     * Warehouse constructor
     * @param maxQueueSize maximum order queue size
     */
    public ShoeWarehouse(int maxQueueSize) {
        this.orderQueue = new ArrayList<>();
        this.maxQueueSize = maxQueueSize;
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
     * Get current queue size
     * @return number of orders in queue
     */
    public synchronized int getQueueSize() {
        return orderQueue.size();
    }
}
