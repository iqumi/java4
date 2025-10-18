package task2;

import java.util.Random;

/**
 * Task2Runner - class for running Task #2
 * Demonstrates Producer-Consumer pattern using wait() and notifyAll()
 */
public class Task2Runner {

    private static final int TOTAL_ORDERS = 15;
    private static final int ORDERS_PER_CONSUMER = 5;
    private static final int MAX_QUEUE_SIZE = 5;

    public static void run() {
        System.out.println("Description: Producer-Consumer pattern for shoe warehouse");
        System.out.println("   - Using wait() and notifyAll()");
        System.out.println("   - FIFO queue with limited size");
        System.out.println("   - 1 Producer, 3 Consumer threads");
        System.out.println("   - Total orders: " + TOTAL_ORDERS + "\n");

        // Create warehouse
        ShoeWarehouse warehouse = new ShoeWarehouse(MAX_QUEUE_SIZE);

        // Create Producer thread
        Thread producer = new Thread(new Producer(warehouse, TOTAL_ORDERS), "Producer-1");

        // Create Consumer threads
        int numberOfConsumers = TOTAL_ORDERS / ORDERS_PER_CONSUMER;
        Thread[] consumers = new Thread[numberOfConsumers];
        for (int i = 0; i < numberOfConsumers; i++) {
            consumers[i] = new Thread(
                    new Consumer(warehouse, ORDERS_PER_CONSUMER),
                    "Consumer-" + (i + 1)
            );
        }

        // Start threads
        System.out.println(">> Starting threads...\n");
        producer.start();
        for (Thread consumer : consumers) {
            consumer.start();
        }

        // Wait for all threads to complete
        try {
            producer.join();
            for (Thread consumer : consumers) {
                consumer.join();
            }
            System.out.println("\n+ All orders processed successfully");
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
        }
    }

    /**
     * Producer - class that generates orders
     */
    static class Producer implements Runnable {
        private final ShoeWarehouse warehouse;
        private final int numberOfOrders;
        private final Random random;

        public Producer(ShoeWarehouse warehouse, int numberOfOrders) {
            this.warehouse = warehouse;
            this.numberOfOrders = numberOfOrders;
            this.random = new Random();
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= numberOfOrders; i++) {
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
                System.out.println("\n  [END] " + Thread.currentThread().getName() + " finished producing orders");
            } catch (InterruptedException e) {
                System.err.println(Thread.currentThread().getName() + " interrupted: " + e.getMessage());
            }
        }
    }

    /**
     * Consumer - class that processes orders
     */
    static class Consumer implements Runnable {
        private final ShoeWarehouse warehouse;
        private final int ordersToProcess;

        public Consumer(ShoeWarehouse warehouse, int ordersToProcess) {
            this.warehouse = warehouse;
            this.ordersToProcess = ordersToProcess;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < ordersToProcess; i++) {
                    // Process order
                    Order order = warehouse.fulfillOrder();

                    // Simulate order processing
                    Thread.sleep(100);
                }
                System.out.println("  [END] " + Thread.currentThread().getName() + " finished processing orders");
            } catch (InterruptedException e) {
                System.err.println(Thread.currentThread().getName() + " interrupted: " + e.getMessage());
            }
        }
    }
}
