package ru.spaceinvasion.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spaceinvasion.Constants;
import ru.spaceinvasion.models.User;
import ru.spaceinvasion.models.UserComparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(Constants.ApiConstants.SCOREBOARD_API_PATH)
public class ScoreboardController {
    // Mocked database
    private final HashMap<String, User> registeredUsers = new HashMap<>();

    // Fill with fake data (for frontend testing)
    public ScoreboardController() {
        final ArrayList<User> users = new ArrayList<>();
        users.add(new User("egor", "egor12345", "https://Egor_Kurakov"));
        users.add(new User("vasidmi", "vasidmi12345", "https://t.me/vasidmi"));
        users.add(new User("ChocolateSwan", "ChocolateSwan12345", "https://t.me/ChocolateSwan"));
        users.add(new User("boyanik", "boyanik12345", "https://t.me/Nikita_Boyarskikh"));
        for (User u : users) {
            registeredUsers.put(u.getUsername(), u);
        }
    }

    @GetMapping
    public List<User> getScoreBoardAll() {
        final ArrayList<User> users = new ArrayList<>(registeredUsers.values());
        users.sort(new UserComparator());

        int toIndex = users.size();
        if (Constants.ApiConstants.SCOREBOARD_SIZE < toIndex) {
            toIndex = Constants.ApiConstants.SCOREBOARD_SIZE;
        }

        return users.subList(0, toIndex);
    }
}
