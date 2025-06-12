package QuizGame;

/**
 * Represents a multiple-choice question.
 */
public class Question {
    private final String category;
    private final String text;
    private final String[] options;
    private final int correctIndex; // 0-based

    public Question(String category, String text, String[] options, int correctIndex) {
        if (options.length < 2) throw new IllegalArgumentException("Need at least 2 options");
        if (correctIndex < 0 || correctIndex >= options.length) throw new IllegalArgumentException("Correct index out of bounds");
        this.category = category;
        this.text = text;
        this.options = options;
        this.correctIndex = correctIndex;
    }

    public String getCategory() { return category; }
    public String getText() { return text; }
    public String[] getOptions() { return options; }
    public int getCorrectIndex() { return correctIndex; }

    public boolean isCorrect(int choice) { return choice == correctIndex; }
}
