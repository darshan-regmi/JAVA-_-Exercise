package QuizGame;

import java.util.*;

/**
 * Simple Quiz Game CLI
 */
public class QuizGameCLI {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Map<String, List<Question>> QUESTION_BANK = new HashMap<>();

    static {
        // Seed some questions
        QUESTION_BANK.put("General", List.of(
                new Question("General", "Capital of France?", new String[]{"Berlin", "Paris", "Rome", "Madrid"}, 1),
                new Question("General", "2 + 2 = ?", new String[]{"3", "4", "5"}, 1),
                new Question("General", "Primary color mixed with blue to get green?", new String[]{"Red", "Yellow", "Orange"}, 1)
        ));
        QUESTION_BANK.put("Science", List.of(
                new Question("Science", "H2O is chemical formula for?", new String[]{"Oxygen", "Hydrogen", "Water"}, 2),
                new Question("Science", "Planet known as Red Planet?", new String[]{"Mars", "Venus", "Jupiter"}, 0)
        ));
    }

    public static void main(String[] args) {
        System.out.println("===== Quiz Game =====");
        while (true) {
            printMenu();
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1 -> playQuiz();
                case 2 -> listCategories();
                case 3 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Start Quiz");
        System.out.println("2. List Categories");
        System.out.println("3. Exit");
    }

    private static void listCategories() {
        System.out.println("Available categories: " + QUESTION_BANK.keySet());
    }

    private static void playQuiz() {
        System.out.print("Enter category (" + QUESTION_BANK.keySet() + "): ");
        String cat = SCANNER.nextLine().trim();
        List<Question> questions = QUESTION_BANK.get(cat);
        if (questions == null || questions.isEmpty()) {
            System.out.println("No questions for that category.");
            return;
        }
        Collections.shuffle(questions);
        int correct = 0;
        List<Boolean> answerTracker = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.printf("Q%d: %s%n", i + 1, q.getText());
            String[] opts = q.getOptions();
            for (int j = 0; j < opts.length; j++) {
                System.out.printf("  %d) %s%n", j + 1, opts[j]);
            }
            int choice = readInt("Your answer (number): ") - 1;
            boolean isCorrect = q.isCorrect(choice);
            answerTracker.add(isCorrect);
            if (isCorrect) {
                System.out.println("Correct!\n");
                correct++;
            } else {
                System.out.printf("Wrong! Correct answer: %s%n%n", opts[q.getCorrectIndex()]);
            }
        }
        long durationSec = (System.currentTimeMillis() - startTime) / 1000;
        int score = calculateScore(answerTracker);
        double accuracy = getAccuracyPercentage(correct, questions.size());
        String difficulty = getDifficultyLevel(accuracy);
        System.out.printf("Quiz finished! Score: %d, Accuracy: %.2f%%, Difficulty Level: %s, Time: %ds%n",
                score, accuracy, difficulty, durationSec);
    }

    // ---- helper calculations ---- //

    private static int calculateScore(List<Boolean> answers) {
        int score = 0;
        for (boolean b : answers) if (b) score += 10; // 10 points per correct answer
        return score;
    }

    private static double getAccuracyPercentage(int correct, int total) {
        return (correct * 100.0) / total;
    }

    private static String getDifficultyLevel(double acc) {
        if (acc >= 90) return "Easy";
        else if (acc >= 70) return "Medium";
        else return "Hard";
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(SCANNER.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter valid integer.");
            }
        }
    }
}
