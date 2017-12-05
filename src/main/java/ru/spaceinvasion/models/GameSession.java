package ru.spaceinvasion.models;

import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.mechanic.game.GamePart;
import ru.spaceinvasion.mechanic.game.GamePartMediator;
import ru.spaceinvasion.mechanic.game.Mediator;
import ru.spaceinvasion.mechanic.game.messages.GameMessage;
import ru.spaceinvasion.mechanic.game.models.Server;
import ru.spaceinvasion.mechanic.internal.GameSessionService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    @NotNull
    private final Server server;

    public GameSession(@NotNull Integer player1Id,
                       @NotNull Integer player2Id,
                       @NotNull GameSessionService gameSessionService,
                       @NotNull Server server) {
        this.sessionId = ID_GENERATOR.getAndIncrement();
        this.player1 = player1Id;
        this.player2 = player2Id;
        this.gameSessionService = gameSessionService;
        this.server = server;
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

    public static AtomicLong getIdGenerator() {
        return ID_GENERATOR;
    }

    public Server getServer() {
        return server;
    }

    public void terminateSession() {
        throw new NotImplementedException();
    }
}