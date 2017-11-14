package ru.spaceinvasion.mechanic.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.mechanic.responses.GameInitResponse;
import ru.spaceinvasion.models.GameSession;
import ru.spaceinvasion.services.WebSocketSessionService;
import ru.spaceinvasion.utils.Exceptions;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by egor on 14.11.17.
 */
public class GameSessionService {

    @NotNull
    private final Map<Integer, GameSession> usersMap = new HashMap<>();

    @NotNull
    private final Set<GameSession> gameSessions = new LinkedHashSet<>();

    @NotNull
    private final GameInitService gameInitService;

    public GameSessionService(@NotNull WebSocketSessionService webSocketSessionService) {
        this.gameInitService = new GameInitService(webSocketSessionService);
    }

    public boolean isPlaying(Integer userId) {
        return usersMap.containsKey(userId);
    }

    public GameSession getSessionOfUser(Integer userId) {
        GameSession gameSession = usersMap.get(userId);
        if (gameSession == null) {
            throw new Exceptions.NotFoundSessionForUser();
        }
        return gameSession;
    }

    public void startGame(@NotNull Integer player1Id,
                          @NotNull Integer player2Id) {
        GameSession gameSession = new GameSession(player1Id, player2Id, this);
        gameSessions.add(gameSession);
        usersMap.put(player1Id, gameSession);
        usersMap.put(player2Id, gameSession);
        gameInitService.initGameFor(gameSession);

    }


}
