package ru.spaceinvasion.services;

import ru.spaceinvasion.models.User;

import java.util.List;

public interface UserService {

    /**
     * This is the method to be used to create
     * a record in the users table.
     */
    User create(User user);

    Boolean validate(User user);

    /**
     * This is the method to be used to list down
     * a record from the users table corresponding
     * to a passed user id.
     */
    User getUser(User user);

    /**
     * This is the method to be used to list down
     * all the records from the Student table.
     */
    @SuppressWarnings("unused")
    List<User> getUsers();

    /**
     * This is the method to be used to delete
     * a record from the Student table corresponding
     * to a passed student id.
     */
    void delete(User user);

    /**
     * This is the method to be used to update
     * a record into the Student table.
     */
    User update(User user, String newUsername, String newEmail, String newPassword);

    void dropAll();

    User changeScore(User user, Integer dScore);

}
