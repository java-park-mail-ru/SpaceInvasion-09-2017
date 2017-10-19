package ru.spaceinvasion.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spaceinvasion.models.User;

import javax.sql.DataSource;
import java.util.List;

@Service
@Transactional
public interface LiderboardService {

    List<User> getTop (int limit);

}
