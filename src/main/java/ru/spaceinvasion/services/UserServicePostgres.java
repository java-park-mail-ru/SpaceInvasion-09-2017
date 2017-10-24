package ru.spaceinvasion.services;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spaceinvasion.utils.Exceptions;
import ru.spaceinvasion.models.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

import static ru.spaceinvasion.services.Mappers.USER_ROW_MAPPER;

@Service
@Transactional
public class UserServicePostgres implements UserService {
    private JdbcTemplate jdbcTemplateObject;

    public UserServicePostgres(JdbcTemplate jdbcTemplateObject) {
        this.jdbcTemplateObject = jdbcTemplateObject;
    }

    @Override
    public User create(User user) throws DuplicateKeyException{
        final String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?) RETURNING  *";

        try {
            user = jdbcTemplateObject.queryForObject(sql,
                    USER_ROW_MAPPER, user.getUsername(),
                    user.getEmail(), user.getPassword());
        } catch (DuplicateKeyException e) {
            throw e;
        }
        return user;
    }

    @Override
    public Boolean validate(User user) {
        final String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            jdbcTemplateObject.queryForObject(sql, USER_ROW_MAPPER,
                    user.getUsername(), user.getPassword());
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public User getUser(User user) {
        String sql;
        if (user == null) {
            throw new Exceptions.NotFoundUser();
        }
        if (user.getUsername() != null) {
            sql = "SELECT * FROM users WHERE username = ?";
            try {
                user = jdbcTemplateObject.queryForObject(sql, USER_ROW_MAPPER, user.getUsername());
            } catch (EmptyResultDataAccessException e) {
                throw new Exceptions.NotFoundUser();
            }
        }

        return user;
    }

    @Override
    public List<User> getUsers() {
        String sql = "SELECT * FROM users";
        List<User> users = jdbcTemplateObject.query(sql, USER_ROW_MAPPER);
        return users;
    }

    @Override
    public void delete(User user) {
        String sql = "DELETE FROM users WHERE id = ?";
        try {
            jdbcTemplateObject.update(sql, user.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new Exceptions.NotFoundUser();
        }
    }

    @Override
    public void dropAll() {
        String sql = "DELETE FROM users *";
        jdbcTemplateObject.update(sql);
    }

    @Override
    public User update(User user, String newUsername,
                       String newEmail, String newPassword) {
        throw new NotImplementedException();
    }

    @Override
    public User changeScore(User user, Integer dScore) {
        String sql = "UPDATE users SET score = (score + ?) WHERE username = ? RETURNING *";
        try {
            return jdbcTemplateObject.queryForObject(sql, USER_ROW_MAPPER, dScore, user.getUsername());
        } catch (EmptyResultDataAccessException e) {
            throw e;
        }
    }
}

