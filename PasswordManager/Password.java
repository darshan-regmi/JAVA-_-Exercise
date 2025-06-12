package PasswordManager;

import java.util.Base64;

/**
 * Represents a stored password entry.
 */
public class Password {
    private static int NEXT_ID = 1;

    private final int id;
    private final String service;
    private final String username;
    private String encodedPassword; // Base64 encoded for demo purposes

    public Password(String service, String username, String plainPassword) {
        this.id = NEXT_ID++;
        this.service = service;
        this.username = username;
        setPassword(plainPassword);
    }

    public int getId() { return id; }
    public String getService() { return service; }
    public String getUsername() { return username; }

    public void setPassword(String plainPassword) {
        this.encodedPassword = Base64.getEncoder().encodeToString(plainPassword.getBytes());
    }

    public String getDecodedPassword() {
        return new String(Base64.getDecoder().decode(encodedPassword));
    }

    @Override
    public String toString() {
        return String.format("#%d | %s | %s", id, service, username);
    }
}
