package ru.spaceinvasion.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

public class User {

    @JsonIgnore
    @Nullable
    private String password;
    @Nullable
    private String username;
    @Nullable
    private String email;
    private int score;
    private int id;

    public User() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        return username != null ? username.equals(user.username) : user.username == null;
    }

    @JsonCreator
    public User(@Nullable @JsonProperty(value = "username", required = true) String username,
                @Nullable@JsonProperty(value = "password", required = true) String password,
                @JsonProperty("email") String email) {
        this.username = username;
        this.password = password;
        this.email = (email != null ? email : "");
        this.score = 0;
    }

    @Nullable
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Nullable
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

    @Nullable
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
