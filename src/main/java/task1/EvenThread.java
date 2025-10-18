package task1;

/**
 * EvenThread - thread extending Thread class
 * Prints 5 even numbers
 */
public class EvenThread extends Thread {

    @Override
    public void run() {
        System.out.println("  [EvenThread] Thread started");
        for (int i = 0; i < 5; i++) {
            int evenNumber = i * 2;
            System.out.println("  [EvenThread] Even number: " + evenNumber);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.err.println("  [EvenThread] Interrupted: " + e.getMessage());
            }
        }
        System.out.println("  [EvenThread] Thread finished");
    }
}
