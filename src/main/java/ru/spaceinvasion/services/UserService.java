package ru.spaceinvasion.services;

import ru.spaceinvasion.models.User;

import java.util.List;

public interface UserService {


    User create(User user);

    Boolean validate(User user);

    User getUser(Integer userId);

    User getUser(String username);

    @SuppressWarnings("unused")
    List<User> getUsers();

    void delete(User user);

    User update(User user, String newUsername, String newEmail, String newPassword);

    void dropAll();

    User changeScore(User user, Integer dScore);

}
