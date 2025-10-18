package task1;

/**
 * OddRunnable - class implementing Runnable interface
 * Prints 5 odd numbers
 */
public class OddRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("  [OddRunnable] Thread started");
        for (int i = 0; i < 5; i++) {
            int oddNumber = i * 2 + 1;
            System.out.println("  [OddRunnable] Odd number: " + oddNumber);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.err.println("  [OddRunnable] Interrupted: " + e.getMessage());
            }
        }
        System.out.println("  [OddRunnable] Thread finished");
    }
}
