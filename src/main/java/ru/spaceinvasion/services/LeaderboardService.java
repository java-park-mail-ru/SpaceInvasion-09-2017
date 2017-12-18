package ru.spaceinvasion.services;

import ru.spaceinvasion.models.User;

import java.util.List;

public interface LeaderboardService {

    List<User> getTop (int limit);

    List<User> getPage (int limit, int offset);

}
