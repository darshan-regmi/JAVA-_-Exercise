package LibraryManagement;
/**
 * Represents a library book.
 */
public class Book {
    private static int NEXT_ID = 1;

    private final int id;
    private String title;
    private String author;
    private String isbn;
    private boolean available = true;

    public Book(String title, String author, String isbn) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title cannot be empty");
        if (author == null || author.isBlank()) throw new IllegalArgumentException("Author cannot be empty");
        if (isbn == null || isbn.isBlank()) throw new IllegalArgumentException("ISBN cannot be empty");
        this.id = NEXT_ID++;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return available;
    }

    public void borrow() throws BookAlreadyBorrowedException {
        if (!available) throw new BookAlreadyBorrowedException("Book already borrowed");
        available = false;
    }

    public void giveBack() {
        available = true;
    }

    @Override
    public String toString() {
        return String.format("#%d | %s by %s | ISBN:%s | %s", id, title, author, isbn, available ? "Available" : "Borrowed");
    }
}

class BookAlreadyBorrowedException extends Exception {
    public BookAlreadyBorrowedException(String msg) { super(msg);} }

class BookNotFoundException extends Exception {
    public BookNotFoundException(String msg) { super(msg);} }
