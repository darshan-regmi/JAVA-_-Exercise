package ContactDirectory;

import java.util.regex.Pattern;

/**
 * Represents a personal contact.
 */
public class Contact {
    private static int NEXT_ID = 1;

    private final int id;
    private String name;
    private String phone;
    private String email;
    private String address;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\w.-]+@[\w.-]+\\.[A-Za-z]{2,6}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{7,15}$");

    public Contact(String name, String phone, String email, String address) throws InvalidEmailException, InvalidPhoneException {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name required");
        if (!isValidPhone(phone)) throw new InvalidPhoneException("Invalid phone number");
        if (!isValidEmail(email)) throw new InvalidEmailException("Invalid email");
        this.id = NEXT_ID++;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address == null ? "" : address;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }

    public void setPhone(String phone) throws InvalidPhoneException {
        if (!isValidPhone(phone)) throw new InvalidPhoneException("Invalid phone");
        this.phone = phone;
    }

    public void setEmail(String email) throws InvalidEmailException {
        if (!isValidEmail(email)) throw new InvalidEmailException("Invalid email");
        this.email = email;
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    @Override
    public String toString() {
        return String.format("#%d | %s | %s | %s", id, name, phone, email);
    }
}

class InvalidEmailException extends Exception { public InvalidEmailException(String m){super(m);} }
class InvalidPhoneException extends Exception { public InvalidPhoneException(String m){super(m);} }
class DuplicateContactException extends Exception { public DuplicateContactException(String m){super(m);} }
class ContactNotFoundException extends Exception { public ContactNotFoundException(String m){super(m);} }
