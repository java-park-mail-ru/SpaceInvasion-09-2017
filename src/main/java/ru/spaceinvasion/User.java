package ru.spaceinvasion;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class User {

    @JsonIgnore
    @NotEmpty
    private String password;
    @NotEmpty
    private final String username;
    @NotNull
    private final String email;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }

        final User user = (User) obj;

        return username.equals(user.username) && password.equals(user.password) && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        int result = password.hashCode();
        final int hashBase = 31;
        result = hashBase * result + username.hashCode();
        result = hashBase * result + email.hashCode();
        return result;
    }

    @SuppressWarnings("unused")
    @JsonCreator
    public User(@JsonProperty(value = "username", required = true) String username,
                @JsonProperty(value = "password", required = true) String password,
                @JsonProperty("email") String email) {
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
