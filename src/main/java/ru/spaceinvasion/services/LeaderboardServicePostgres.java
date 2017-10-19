package ru.spaceinvasion.services;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.spaceinvasion.models.User;

import java.util.List;

public class LeaderboardServicePostgres implements LiderboardService {

    private JdbcTemplate jdbcTemplateObject;

    public List<User> getTop(int limit) {
        String sql = "SELECT * FROM users ORDER BY score LIMIT ?";
        List<User> users = jdbcTemplateObject.query(sql, Mappers.USER_ROW_MAPPER, limit);
        return users;
    }
}
