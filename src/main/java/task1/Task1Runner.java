package task1;

/**
 * Task1Runner - class for running Task #1
 * Demonstrates thread creation via Thread extension and Runnable implementation
 */
public class Task1Runner {

    public static void run() {
        System.out.println("Description: Creating two threads");
        System.out.println("   - First thread extends Thread class");
        System.out.println("   - Second thread implements Runnable interface");
        System.out.println("   - Both threads execute asynchronously\n");

        // Create first thread by extending Thread
        Thread evenThread = new EvenThread();

        // Create second thread via Runnable
        Thread oddThread = new Thread(new OddRunnable());

        // Start threads asynchronously
        evenThread.start();
        oddThread.start();

        System.out.println("+ Both threads started asynchronously\n");

        // Wait for threads to complete
        try {
            evenThread.join();
            oddThread.join();
            System.out.println("\n+ Both threads completed execution");
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
        }
    }
}
