package ru.spaceinvasion.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spaceinvasion.utils.Constants;
import ru.spaceinvasion.models.User;
import ru.spaceinvasion.services.LeaderboardService;

import java.util.List;

@RestController
@RequestMapping(
        path = Constants.ApiConstants.LEADERBOARD_API_PATH,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LeaderboardController {

    private LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @GetMapping(path = "", consumes = MediaType.ALL_VALUE)
    public List<User> getScoreBoardAll() {

        return leaderboardService.getTop(
                Constants.ApiConstants.LEADERBOARD_SIZE);

    }
}
