package ru.spaceinvasion.db;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import javax.sql.DataSource;

public class UserJdbcTemplate implements UserDao {
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }
    
    @Override
    public void create(String name, String password, Integer age) {
        final String sql = "insert into users (name, password, age) values (?, ?, ?)";
        jdbcTemplateObject.update(sql, name, password, age);
        System.out.println("Created Record Name = " + name + " Age = " + age);
    }
    
    @Override
    public User getUser(Integer id) {
        String sql = "select * from users where id = ?";
        User user = jdbcTemplateObject.queryForObject(sql,
                new Object[]{id}, new UserMapper());

        return user;
    }

    @Override
    public List<User> listUsers() {
        String sql = "select * from users";
        List<User> users = jdbcTemplateObject.query(sql, new UserMapper());
        return users;
    }

    @Override
    public void delete(Integer id) {
        String sql = "delete from users where id = ?";
        jdbcTemplateObject.update(sql, id);
        System.out.println("Deleted Record with ID = " + id);
    }

    @Override
    public void update(Integer id, Integer age) {
        String sql = "update users set age = ? where id = ?";
        jdbcTemplateObject.update(sql, age, id);
        System.out.println("Updated Record with ID = " + id);
    }
}