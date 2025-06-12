package Hangman;

import java.util.*;

/**
 * CLI Hangman Game with three difficulty levels.
 */
public class HangmanGame {

    private static final Scanner SCANNER = new Scanner(System.in);

    private static final String[] EASY = {"cat", "dog", "sun", "car", "book"};
    private static final String[] MEDIUM = {"orange", "guitar", "planet", "castle", "garden"};
    private static final String[] HARD = {"elephant", "programming", "challenge", "encyclopedia", "microprocessor"};

    private static final String[] HANGMAN_STAGES = {
            " +---+\n     |   |\n         |\n         |\n         |\n         |\n  =========",
            " +---+\n     |   |\n     O   |\n         |\n         |\n         |\n  =========",
            " +---+\n     |   |\n     O   |\n     |   |\n         |\n         |\n  =========",
            " +---+\n     |   |\n     O   |\n    /|   |\n         |\n         |\n  =========",
            " +---+\n     |   |\n     O   |\n    /|\\  |\n         |\n         |\n  =========",
            " +---+\n     |   |\n     O   |\n    /|\\  |\n    /    |\n         |\n  =========",
            " +---+\n     |   |\n     O   |\n    /|\\  |\n    / \\  |\n         |\n  ========="};

    private static final int MAX_WRONG = HANGMAN_STAGES.length - 1;

    public static void main(String[] args) {
        System.out.println("===== Hangman =====");
        while (true) {
            String word = setupGame();
            if (word == null) return; // exit
            play(word);
            System.out.print("Play again? (y/n): ");
            if (!SCANNER.nextLine().trim().equalsIgnoreCase("y")) break;
        }
    }

    private static String setupGame() {
        System.out.print("Choose difficulty (easy/medium/hard or exit): ");
        String diff = SCANNER.nextLine().trim().toLowerCase();
        String[] bank;
        switch (diff) {
            case "easy" -> bank = EASY;
            case "medium" -> bank = MEDIUM;
            case "hard" -> bank = HARD;
            case "exit" -> { return null; }
            default -> {
                System.out.println("Unknown difficulty.");
                return setupGame();
            }
        }
        return selectRandomWord(bank);
    }

    private static void play(String word) {
        Set<Character> guessed = new HashSet<>();
        int wrong = 0;
        while (true) {
            drawHangman(wrong);
            System.out.println("Word: " + displayWordProgress(word, guessed));
            System.out.println("Guessed letters: " + guessed);
            System.out.print("Enter a letter: ");
            String input = SCANNER.nextLine().trim().toLowerCase();
            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println("Invalid input. Enter a single letter.");
                continue;
            }
            char guess = input.charAt(0);
            if (guessed.contains(guess)) {
                System.out.println("Already guessed.");
                continue;
            }
            guessed.add(guess);
            if (isLetterInWord(guess, word)) {
                System.out.println("Correct!");
                if (isGameWon(word, guessed)) {
                    System.out.println("You win! The word was '" + word + "'.");
                    int score = calculateScore(word, MAX_WRONG - wrong);
                    System.out.println("Score: " + score);
                    break;
                }
            } else {
                wrong++;
                System.out.println("Wrong! Attempts left: " + (MAX_WRONG - wrong));
                if (wrong >= MAX_WRONG) {
                    drawHangman(wrong);
                    System.out.println("You lost. Word was '" + word + "'.");
                    break;
                }
            }
        }
    }

    // ----- Methods from spec ----- //

    public static String selectRandomWord(String[] wordBank) {
        return wordBank[new Random().nextInt(wordBank.length)];
    }

    public static boolean isLetterInWord(char guess, String word) {
        return word.indexOf(guess) >= 0;
    }

    public static String displayWordProgress(String word, Set<Character> guessed) {
        StringBuilder sb = new StringBuilder();
        for (char c : word.toCharArray()) {
            if (guessed.contains(c)) sb.append(c).append(' ');
            else sb.append('_').append(' ');
        }
        return sb.toString().trim();
    }

    public static int calculateScore(String word, int attemptsRemaining) {
        return word.length() * 10 + attemptsRemaining * 5;
    }

    public static void drawHangman(int wrongAttempts) {
        System.out.println(HANGMAN_STAGES[Math.min(wrongAttempts, MAX_WRONG)]);
    }

    public static boolean isGameWon(String word, Set<Character> guessed) {
        for (char c : word.toCharArray()) if (!guessed.contains(c)) return false;
        return true;
    }
}
