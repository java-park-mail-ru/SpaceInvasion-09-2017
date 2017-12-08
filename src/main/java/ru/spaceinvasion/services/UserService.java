package ru.spaceinvasion.services;

import ru.spaceinvasion.models.User;

import java.util.List;

public interface UserService {


    User create(User user);

    Boolean authenticate(User user);

    User getUser(Integer userId);

    User getUser(String username);

    @SuppressWarnings("unused")
    List<User> getUsers();

    void dropAll();

    User changeScore(User user, Integer dScore);

}
