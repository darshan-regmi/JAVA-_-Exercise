package TaskManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Task Management CLI
 */
public class TaskManagerCLI {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final List<Task> TASKS = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("===== Task Manager =====");
        while (true) {
            printMenu();
            int choice = readInt("Choose an option: ");
            try {
                switch (choice) {
                    case 1 -> addTask();
                    case 2 -> listTasks();
                    case 3 -> markComplete();
                    case 4 -> showOverdue();
                    case 5 -> showCompletionRate();
                    case 6 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add Task");
        System.out.println("2. List Tasks");
        System.out.println("3. Mark Task Complete");
        System.out.println("4. Show Overdue Tasks");
        System.out.println("5. Show Completion Rate");
        System.out.println("6. Exit");
    }

    private static Task findTask(int id) {
        return TASKS.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    private static void addTask() throws InvalidPriorityException, DateParseException {
        System.out.print("Description: ");
        String desc = SCANNER.nextLine().trim();
        int priority = readInt("Priority (1-5): ");
        System.out.print("Due date (YYYY-MM-DD): ");
        String date = SCANNER.nextLine().trim();
        TASKS.add(new Task(desc, priority, date));
        System.out.println("Task added.");
    }

    private static void listTasks() {
        if (TASKS.isEmpty()) { System.out.println("No tasks."); return; }
        TASKS.forEach(System.out::println);
    }

    private static void markComplete() throws TaskNotFoundException, TaskAlreadyCompletedException {
        int id = readInt("Task ID to complete: ");
        Task t = findTask(id);
        if (t == null) throw new TaskNotFoundException("ID not found");
        t.markComplete();
        System.out.println("Marked complete.");
    }

    private static void showOverdue() {
        System.out.println("Overdue tasks:");
        TASKS.stream().filter(Task::isOverdue).forEach(System.out::println);
    }

    private static void showCompletionRate() {
        long total = TASKS.size();
        long done = TASKS.stream().filter(Task::isCompleted).count();
        if (total == 0) { System.out.println("No tasks."); return; }
        double rate = (done * 100.0) / total;
        System.out.printf("Completion rate: %.2f%%%n", rate);
    }

    // helpers
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Integer.parseInt(SCANNER.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Enter integer."); }
        }
    }
}
