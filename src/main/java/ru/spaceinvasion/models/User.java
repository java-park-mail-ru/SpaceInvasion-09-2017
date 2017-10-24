package ru.spaceinvasion.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class User {

    @JsonIgnore
    private String password;
    private String username;
    private String email;
    @JsonIgnore
    private int score;
    private int id;

    //CHECKSTYLE:OFF
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        final User user = (User) o;

        return (password != null ? password.equals(user.password) : user.password == null)
                && (username != null ? username.equals(user.username) : user.username == null)
                && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        int result = password != null ? password.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + email.hashCode();
        return result;
    }
    //CHECKSTYLE:ON

    public User() { }

    @JsonCreator
    public User(@JsonProperty(value = "username", required = true) String username,
                @JsonProperty(value = "password", required = true) String password,
                @JsonProperty("email") String email) {
        this.username = username;
        this.password = password;
        this.email = (email != null ? email : "");
        this.score = 0;
    }

    public String getUsername() {
        return username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public int getScore() {
        return score;
    }

    @JsonIgnore
    public void setScore(int score) {
        this.score = score;
    }

    @JsonIgnore
    public void setUsername(String username) { this.username = username; }

    @JsonIgnore
    public void setEmail(String email) { this.email = email; }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    int compareTo(User user) {
        return this.score - user.score;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    @JsonIgnore
    @SuppressWarnings("unused")
    public void setId(int id) {
        this.id = id;
    }
}
