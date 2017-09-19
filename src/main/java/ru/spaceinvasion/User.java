package ru.spaceinvasion;

public class User {

    private final String username;
    private final String password;
    private final String email;

    @SuppressWarnings("unused")
    public User() {
        this.username = "";
        this.password = "";
        this.email = "";
    }

    @SuppressWarnings("unused")
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @SuppressWarnings("unused")
    public String getUsername() {
        return username;
    }

    @SuppressWarnings("unused")
    public String getPassword() {
        return password;
    }

    @SuppressWarnings("unused")
    public String getEmail() {
        return email;
    }
}
