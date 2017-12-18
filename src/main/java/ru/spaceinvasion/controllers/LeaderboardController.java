package ru.spaceinvasion.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spaceinvasion.models.Page;
import ru.spaceinvasion.utils.Constants;
import ru.spaceinvasion.models.User;
import ru.spaceinvasion.services.LeaderboardService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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

    @GetMapping(path = "/top")
    public List<User> getScoreBoardAll() {
        return leaderboardService.getTop(
                Constants.ApiConstants.LEADERBOARD_SIZE);

    }

    @GetMapping(path = "/page")
    public List<User> getScoreBoardAll(@RequestBody @NotNull Page page) {
        return leaderboardService.getPage(page.getLimit(), page.getOffset());

    }
}
