package ru.spaceinvasion.mechanic.internal;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import ru.spaceinvasion.mechanic.game.GamePartMediator;
import ru.spaceinvasion.mechanic.game.Side;
import ru.spaceinvasion.mechanic.game.models.Server;
import ru.spaceinvasion.mechanic.snaps.ClientSnapService;
import ru.spaceinvasion.mechanic.snaps.ServerSnap;
import ru.spaceinvasion.models.GameSession;
import ru.spaceinvasion.services.WebSocketSessionService;
import ru.spaceinvasion.utils.Exceptions;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by egor on 14.11.17.
 */

@Service
public class GameSessionService {

    private final Map<Integer, GameSession> usersMap = new HashMap<>();

    private final Set<GameSession> gameSessions = new LinkedHashSet<>();

    private final WebSocketSessionService webSocketSessionService;

    @SuppressWarnings("unused")
    private final ClientSnapService clientSnapService;

    public GameSessionService(@NotNull WebSocketSessionService webSocketSessionService,
                              @NotNull ClientSnapService clientSnapService) {
        this.webSocketSessionService = webSocketSessionService;
        this.clientSnapService = clientSnapService;
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
        gameSession.getServer().startGame(gameSession.getPlayer1(), gameSession.getPlayer2());

    }


    public Set<GameSession> getSessions() {
        return gameSessions;
    }

    public void forceTerminate(GameSession gameSession, boolean err) {
        final boolean exists = gameSessions.remove(gameSession);
        usersMap.remove(gameSession.getPlayer1());
        usersMap.remove(gameSession.getPlayer2());
        final CloseStatus status = err ? CloseStatus.SERVER_ERROR : CloseStatus.NORMAL;
        if (exists) {
            webSocketSessionService.closeConnection(gameSession.getPlayer1(), status);
            webSocketSessionService.closeConnection(gameSession.getPlayer2(), status);
        }
    }
}
