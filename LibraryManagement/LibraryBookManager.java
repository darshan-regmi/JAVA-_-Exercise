package LibraryManagement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Library Book Manager (CLI)
 * Features: add book, search, borrow, return, list all books, exit
 */
public class LibraryBookManager {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final List<Book> BOOKS = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("===== Library Book Manager =====");
        while (true) {
            printMenu();
            int choice = readInt("Choose an option: ");
            try {
                switch (choice) {
                    case 1 -> addBook();
                    case 2 -> searchBooks();
                    case 3 -> borrowBook();
                    case 4 -> returnBook();
                    case 5 -> listBooks();
                    case 6 -> {
                        System.out.println("Exiting... Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please select 1-6.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add Book");
        System.out.println("2. Search Books");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. List All Books");
        System.out.println("6. Exit");
    }

    // ---- Operations ---- //

    private static void addBook() {
        System.out.print("Enter title: ");
        String title = SCANNER.nextLine().trim();
        System.out.print("Enter author: ");
        String author = SCANNER.nextLine().trim();
        System.out.print("Enter ISBN: ");
        String isbn = SCANNER.nextLine().trim();

        Book book = new Book(title, author, isbn);
        BOOKS.add(book);
        System.out.println("Book added: " + book);
    }

    private static void searchBooks() {
        System.out.print("Enter keyword (title/author/ISBN): ");
        String keyword = SCANNER.nextLine().trim().toLowerCase();
        List<Book> results = new ArrayList<>();
        for (Book b : BOOKS) {
            if (b.getTitle().toLowerCase().contains(keyword) ||
                    b.getAuthor().toLowerCase().contains(keyword) ||
                    b.getIsbn().toLowerCase().contains(keyword)) {
                results.add(b);
            }
        }
        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("\n--- Search Results ---");
            results.forEach(System.out::println);
        }
    }

    private static Book findBookById(int id) {
        return BOOKS.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    private static void borrowBook() throws BookAlreadyBorrowedException, BookNotFoundException {
        int id = readInt("Enter book ID to borrow: ");
        Book b = findBookById(id);
        if (b == null) throw new BookNotFoundException("Book ID not found.");
        b.borrow();
        System.out.println("You borrowed: " + b);
    }

    private static void returnBook() throws BookNotFoundException {
        int id = readInt("Enter book ID to return: ");
        Book b = findBookById(id);
        if (b == null) throw new BookNotFoundException("Book ID not found.");
        b.giveBack();
        System.out.println("Book returned: " + b);
    }

    private static void listBooks() {
        if (BOOKS.isEmpty()) {
            System.out.println("No books in library.");
            return;
        }
        System.out.println("\n--- Book List ---");
        BOOKS.forEach(System.out::println);
    }

    // ---- Input helpers ---- //

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
}
