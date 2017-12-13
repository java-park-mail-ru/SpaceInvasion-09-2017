package ru.spaceinvasion.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

public class User {

    @JsonIgnore
    private String password;

    private String username;
    private String email;
    private int score;
    private Integer id;

    public User() { }

    public User(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final User user = (User) o;

        return id.equals(user.id) && (username != null ? username.equals(user.username) : user.username == null);
    }

    @JsonCreator
    public User(@Nullable @JsonProperty(value = "username", required = true) String username,
                @Nullable @JsonProperty(value = "password", required = true) String password,
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
    public void setUsername(@Nullable String username) { this.username = username; }

    @JsonIgnore
    public void setEmail(@Nullable  String email) { this.email = email; }

    @JsonProperty
    public void setPassword(@Nullable  String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    @JsonProperty
    public Integer getId() {
        return id;
    }

    @JsonProperty
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int result = password != null ? password.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + score;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
