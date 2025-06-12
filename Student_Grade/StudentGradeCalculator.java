package Student_Grade;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Student Grade Calculator (CLI)
 *
 * Features:
 * 1. Add student name and grade (0-100)
 * 2. Display class average and corresponding grade letter
 * 3. Show top‐scoring student
 * 4. List all students with their scores & grade letters
 * 5. Exit program
 *
 * Only standard JDK classes are used; no external dependencies.
 */
public class StudentGradeCalculator {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        List<String> studentNames = new ArrayList<>();
        List<Double> studentGrades = new ArrayList<>();

        System.out.println("===== Student Grade Calculator =====");
        while (true) {
            printMenu();
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1 -> addStudent(studentNames, studentGrades);
                case 2 -> showClassAverage(studentGrades);
                case 3 -> showTopStudent(studentNames, studentGrades);
                case 4 -> listAllStudents(studentNames, studentGrades);
                case 5 -> {
                    System.out.println("Exiting... Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please select 1-5.");
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add Student");
        System.out.println("2. Show Class Average");
        System.out.println("3. Show Top Student");
        System.out.println("4. List All Students");
        System.out.println("5. Exit");
    }

    // ---- Core Operations ---- //

    /**
     * Adds a student name and grade to the provided lists with validation.
     */
    private static void addStudent(List<String> names, List<Double> grades) {
        System.out.print("Enter student name: ");
        String name = SCANNER.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        double score;
        try {
            score = readDouble("Enter grade (0-100): ");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        names.add(name);
        grades.add(score);
        System.out.printf("Added %s with grade %.2f (%c)%n", name, score, getGradeLetter(score));
    }

    /**
     * Calculates and prints class average.
     */
    private static void showClassAverage(List<Double> grades) {
        if (grades.isEmpty()) {
            System.out.println("No students in the list.");
            return;
        }
        double avg = calculateAverage(grades);
        System.out.printf("Class Average: %.2f (%c)%n", avg, getGradeLetter(avg));
    }

    /**
     * Finds and prints the student with the highest grade.
     */
    private static void showTopStudent(List<String> names, List<Double> grades) {
        if (grades.isEmpty()) {
            System.out.println("No students in the list.");
            return;
        }
        String topStudent = findTopStudent(names, grades);
        double topScore = grades.get(names.indexOf(topStudent));
        System.out.printf("Top Student: %s with %.2f (%c)%n", topStudent, topScore, getGradeLetter(topScore));
    }

    /**
     * Lists all students with their scores and grade letters.
     */
    private static void listAllStudents(List<String> names, List<Double> grades) {
        if (grades.isEmpty()) {
            System.out.println("No students in the list.");
            return;
        }
        System.out.println("\n--- Student List ---");
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            double grade = grades.get(i);
            System.out.printf("%d. %s — %.2f (%c)%n", i + 1, name, grade, getGradeLetter(grade));
        }
    }

    // ---- Utility Methods ---- //

    private static double calculateAverage(List<Double> grades) {
        double sum = 0;
        for (double g : grades) {
            sum += g;
        }
        return sum / grades.size();
    }

    private static char getGradeLetter(double score) {
        if (score >= 90) return 'A';
        else if (score >= 80) return 'B';
        else if (score >= 70) return 'C';
        else if (score >= 60) return 'D';
        else return 'F';
    }

    private static String findTopStudent(List<String> names, List<Double> grades) {
        double max = -1;
        int index = -1;
        for (int i = 0; i < grades.size(); i++) {
            if (grades.get(i) > max) {
                max = grades.get(i);
                index = i;
            }
        }
        return names.get(index);
    }

    // ---- Input Helpers with Validation ---- //

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String line = SCANNER.nextLine();
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String line = SCANNER.nextLine();
                double value = Double.parseDouble(line.trim());
                if (value < 0 || value > 100) {
                    throw new IllegalArgumentException("Grade must be between 0 and 100.");
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a numeric value.");
            }
        }
    }
}
