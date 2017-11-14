package ru.spaceinvasion.models;

import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.mechanic.internal.GameSessionService;

import java.util.concurrent.atomic.AtomicLong;

public class GameSession {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

    @NotNull
    private Long sessionId;

    @NotNull
    private Integer player1;

    @NotNull
    private Integer player2;

    @NotNull
    private GameSessionService gameSessionService;

    public GameSession(@NotNull Integer player1Id,
                       @NotNull Integer player2Id,
                       @NotNull GameSessionService gameSessionService) {
        this.sessionId = ID_GENERATOR.getAndIncrement();
        this.player1 = player1Id;
        this.player2 = player2Id;
        this.gameSessionService = gameSessionService;
    }

    public Integer getPlayer1() {
        return player1;
    }

    public void setPlayer1(Integer player1) {
        this.player1 = player1;
    }

    public Integer getPlayer2() {
        return player2;
    }

    public void setPlayer2(Integer player2) {
        this.player2 = player2;
    }
}
