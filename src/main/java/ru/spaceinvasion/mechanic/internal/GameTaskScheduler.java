package ru.spaceinvasion.mechanic.internal;

import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.services.TimeService;

/**
 * Created by egor on 15.11.17.
 */
public class GameTaskScheduler {

    @NotNull private final TimeService timeService;

    public GameTaskScheduler(@NotNull TimeService timeService) {
        this.timeService = timeService;
    }

}
