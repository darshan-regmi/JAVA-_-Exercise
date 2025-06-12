package ContactDirectory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Contact Directory CLI
 */
public class ContactDirectoryCLI {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final List<Contact> CONTACTS = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("===== Contact Directory =====");
        while (true) {
            printMenu();
            int choice = readInt("Choose an option: ");
            try {
                switch (choice) {
                    case 1 -> addContact();
                    case 2 -> searchContacts();
                    case 3 -> editContact();
                    case 4 -> listContacts();
                    case 5 -> {
                        System.out.println("Exiting... Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Select 1-5.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add Contact");
        System.out.println("2. Search Contacts");
        System.out.println("3. Edit Contact (phone/email)");
        System.out.println("4. List All Contacts");
        System.out.println("5. Exit");
    }

    // ---- operations ---- //

    private static void addContact() throws DuplicateContactException, InvalidEmailException, InvalidPhoneException {
        System.out.print("Enter name: ");
        String name = SCANNER.nextLine().trim();
        System.out.print("Enter phone (+XXXXXXXXXXX): ");
        String phone = SCANNER.nextLine().trim();
        System.out.print("Enter email: ");
        String email = SCANNER.nextLine().trim();
        System.out.print("Enter address (optional): ");
        String addr = SCANNER.nextLine().trim();

        // duplicate check by name & phone
        for (Contact c : CONTACTS) {
            if (c.getName().equalsIgnoreCase(name) || c.getPhone().equals(phone)) {
                throw new DuplicateContactException("Contact already exists.");
            }
        }
        Contact c = new Contact(name, phone, email, addr);
        CONTACTS.add(c);
        System.out.println("Added: " + c);
    }

    private static List<Contact> searchByKeyword(String keyword) {
        keyword = keyword.toLowerCase();
        List<Contact> res = new ArrayList<>();
        for (Contact c : CONTACTS) {
            if (c.getName().toLowerCase().contains(keyword) ||
                    c.getPhone().contains(keyword) ||
                    c.getEmail().toLowerCase().contains(keyword)) {
                res.add(c);
            }
        }
        return res;
    }

    private static void searchContacts() {
        System.out.print("Enter search keyword: ");
        String kw = SCANNER.nextLine().trim();
        List<Contact> res = searchByKeyword(kw);
        if (res.isEmpty()) System.out.println("No contacts found.");
        else res.forEach(System.out::println);
    }

    private static Contact findById(int id) {
        return CONTACTS.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    private static void editContact() throws ContactNotFoundException, InvalidEmailException, InvalidPhoneException {
        int id = readInt("Enter contact ID to edit: ");
        Contact c = findById(id);
        if (c == null) throw new ContactNotFoundException("ID not found");
        System.out.println("Editing " + c);
        System.out.print("New phone (leave blank to keep): ");
        String phone = SCANNER.nextLine().trim();
        if (!phone.isBlank()) c.setPhone(phone);
        System.out.print("New email (leave blank to keep): ");
        String email = SCANNER.nextLine().trim();
        if (!email.isBlank()) c.setEmail(email);
        System.out.println("Updated: " + c);
    }

    private static void listContacts() {
        if (CONTACTS.isEmpty()) {
            System.out.println("No contacts.");
            return;
        }
        CONTACTS.forEach(System.out::println);
    }

    // ---- helpers ---- //

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
