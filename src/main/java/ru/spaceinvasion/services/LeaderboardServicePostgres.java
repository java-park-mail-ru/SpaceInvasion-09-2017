package ru.spaceinvasion.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spaceinvasion.models.User;

import java.util.List;

@Service
@Transactional
public class LeaderboardServicePostgres implements LeaderboardService {

    private JdbcTemplate jdbcTemplateObject;

    public LeaderboardServicePostgres(JdbcTemplate jdbcTemplateObject) {
        this.jdbcTemplateObject = jdbcTemplateObject;
    }

    public List<User> getTop(int limit) {
        String sql = "SELECT * FROM users ORDER BY score LIMIT ?";
        return jdbcTemplateObject.query(sql, Mappers.USER_ROW_MAPPER, limit);
    }
}
