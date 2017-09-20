package ru.spaceinvasion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonIgnore
    private String password;
    private final String username;
    private final String email;

    @SuppressWarnings("unused")
    public User() {
        this.username = "";
        this.password = "";
        this.email = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        final User user = (User) o;

        return password.equals(user.password) && password.equals(user.password) && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        int result = password.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + email.hashCode();
        return result;
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

    @JsonIgnore
    @SuppressWarnings("unused")
    public String getPassword() {
        return password;
    }

    @JsonProperty
    @SuppressWarnings("unused")
    public void setPassword(String password) {
        this.password = password;
    }

    @SuppressWarnings("unused")
    public String getEmail() {
        return email;
    }
}
