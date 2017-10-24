package ru.spaceinvasion.services;

import org.springframework.jdbc.core.RowMapper;
import ru.spaceinvasion.models.User;

class Mappers {

    static final RowMapper<User> USER_ROW_MAPPER = (res, num) -> new User(
            res.getString("username"),
            res.getString("password"),
            res.getString("email")
    );

}
