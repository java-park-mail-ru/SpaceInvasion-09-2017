package ru.spaceinvasion.mechanic.internal;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import ru.spaceinvasion.mechanic.game.GamePartMediator;
import ru.spaceinvasion.mechanic.game.Side;
import ru.spaceinvasion.mechanic.game.models.Server;
import ru.spaceinvasion.mechanic.internal.tasks.CoinCreationTask;
import ru.spaceinvasion.mechanic.snaps.ServerSnap;
import ru.spaceinvasion.models.GameSession;
import ru.spaceinvasion.services.WebSocketSessionService;
import ru.spaceinvasion.utils.Constants;
import ru.spaceinvasion.utils.Exceptions;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

import static ru.spaceinvasion.resources.Constants.MILLIS_TO_CREATING_COIN;

/**
 * Created by egor on 14.11.17.
 */

@Service
public class GameSessionService {

    private final Map<Integer, GameSession> usersMap = new HashMap<>();

    private final Set<GameSession> gameSessions = new LinkedHashSet<>();

    private final WebSocketSessionService webSocketSessionService;

    private final Set<Integer> usersWhoseSessionFinish = new HashSet<>();

    private final GameTaskScheduler gameTaskScheduler;

    public GameSessionService(@NotNull WebSocketSessionService webSocketSessionService,
                              @NotNull GameTaskScheduler gameTaskScheduler) {
        this.webSocketSessionService = webSocketSessionService;
        this.gameTaskScheduler = gameTaskScheduler;
    }

    public boolean isPlaying(Integer userId) {
        return usersMap.containsKey(userId);
    }

    @SuppressWarnings("unused")
    public GameSession getSessionOfUser(Integer userId) {
        GameSession gameSession = usersMap.get(userId);
        if (gameSession == null) {
            throw new Exceptions.NotFoundSessionForUser();
        }
        return gameSession;
    }

    public void playerIsGoingAway(Integer userId) {
        forceTerminate(usersMap.get(userId),false);
    }

    public void startGame(@NotNull Integer player1Id,
                          @NotNull Integer player2Id) {
        GamePartMediator gamePartMediator = new GamePartMediator();
        Server server = new Server(gamePartMediator,0L, new AtomicLong());
        gamePartMediator.registerColleague(Server.class, server);
        GameSession gameSession = new GameSession(
                player1Id,
                player2Id,
                this,
                server);
        gameSessions.add(gameSession);
        usersMap.put(player1Id, gameSession);
        usersMap.put(player2Id, gameSession);
        final List<Integer> players = new ArrayList<>();
        players.add(gameSession.getPlayer1());
        players.add(gameSession.getPlayer2());
        try {
            webSocketSessionService.sendMessageToUser(players.get(0), new ServerSnap(Side.LEFT, player2Id));
            webSocketSessionService.sendMessageToUser(players.get(1), new ServerSnap(Side.RIGHT, player1Id));
        } catch (IOException e) {
            players.stream().forEach(playerToCutOff -> webSocketSessionService.closeConnection(playerToCutOff,
                    CloseStatus.SERVER_ERROR));
        }
        gameSession.run();
        gameTaskScheduler.schedule(MILLIS_TO_CREATING_COIN, new CoinCreationTask(gameSession.getServer(), gameTaskScheduler));

    }

    public Set<Integer> getUsersWhoseSessionFinish() {
        return usersWhoseSessionFinish;
    }

    public Set<GameSession> getSessions() {
        return gameSessions;
    }

    public void forceTerminate(GameSession gameSession, boolean err) {
        final boolean exists = gameSessions.remove(gameSession);
        if (exists && usersMap.remove(gameSession.getPlayer1()) != null) {
            usersWhoseSessionFinish.add(gameSession.getPlayer1());
        }
        if (exists && usersMap.remove(gameSession.getPlayer2()) != null) {
            usersWhoseSessionFinish.add(gameSession.getPlayer2());
        }
    }
}
