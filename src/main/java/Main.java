import task1.Task1Runner;
import task2.Task2Runner;
import task3.Task3Runner;

import java.util.Scanner;

/**
 * Main - main application class with interactive menu
 * Allows user to select and run any of the three tasks
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    runTask1();
                    break;
                case 2:
                    runTask2();
                    break;
                case 3:
                    runTask3();
                    break;
                case 0:
                    running = false;
                    System.out.println("\nExiting program. Goodbye!");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.\n");
            }

            if (running && choice >= 1 && choice <= 3) {
                waitForUser();
            }
        }

        scanner.close();
    }

    /**
     * Display main menu
     */
    private static void displayMenu() {
        System.out.println("=".repeat(60));
        System.out.println("  1. Task #1: Thread Creation (2 points)");
        System.out.println("     - Thread and Runnable");
        System.out.println();
        System.out.println("  2. Task #2: Producer-Consumer (4 points)");
        System.out.println("     - wait() and notifyAll()");
        System.out.println();
        System.out.println("  3. Task #3: ExecutorService (4 points)");
        System.out.println("     - Thread Pools");
        System.out.println();
        System.out.println("  0. Exit");
        System.out.println("=".repeat(60));
        System.out.print("\nYour choice: ");
    }

    /**
     * Get user choice
     * @return selected menu item number
     */
    private static int getUserChoice() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine(); // Clear buffer
            return -1;
        }
    }

    /**
     * Run Task #1
     */
    private static void runTask1() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("STARTING TASK #1: Thread Creation");
        System.out.println("=".repeat(60) + "\n");

        Task1Runner.run();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("TASK #1 COMPLETED");
        System.out.println("=".repeat(60));
    }

    /**
     * Run Task #2
     */
    private static void runTask2() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("STARTING TASK #2: Producer-Consumer Pattern");
        System.out.println("=".repeat(60) + "\n");

        Task2Runner.run();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("TASK #2 COMPLETED");
        System.out.println("=".repeat(60));
    }

    /**
     * Run Task #3
     */
    private static void runTask3() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("STARTING TASK #3: ExecutorService Implementation");
        System.out.println("=".repeat(60) + "\n");

        Task3Runner.run();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("TASK #3 COMPLETED");
        System.out.println("=".repeat(60));
    }

    /**
     * Wait for user to press Enter
     */
    private static void waitForUser() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine(); // Clear buffer
        scanner.nextLine(); // Wait for Enter
    }
}
