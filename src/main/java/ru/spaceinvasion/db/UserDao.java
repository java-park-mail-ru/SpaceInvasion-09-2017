package ru.spaceinvasion.db;

import java.util.List;
import javax.sql.DataSource;

public interface UserDao {
    /**
     * This is the method to be used to initialize
     * database resources ie. connection.
     */
    void setDataSource(DataSource ds);

    /**
     * This is the method to be used to create
     * a record in the users table.
     */
    void create(String name, String password, Integer age);

    /**
     * This is the method to be used to list down
     * a record from the users table corresponding
     * to a passed user id.
     */
    User getUser(Integer id);

    /**
     * This is the method to be used to list down
     * all the records from the Student table.
     */
    List<User> listUsers();

    /**
     * This is the method to be used to delete
     * a record from the Student table corresponding
     * to a passed student id.
     */
    void delete(Integer id);

    /**
     * This is the method to be used to update
     * a record into the Student table.
     */
    void update(Integer id, Integer age);
}
