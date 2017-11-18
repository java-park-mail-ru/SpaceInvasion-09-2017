package ru.spaceinvasion.mechanic.internal;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.CloseStatus;
import ru.spaceinvasion.mechanic.game.messages.StartGameMessage;
import ru.spaceinvasion.mechanic.game.models.Mechanics;
import ru.spaceinvasion.mechanic.game.models.Server;
import ru.spaceinvasion.mechanic.responses.GameInitResponse;
import ru.spaceinvasion.models.GameSession;
import ru.spaceinvasion.services.WebSocketSessionService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by egor on 14.11.17.
 */
public class GameInitService {

    @NotNull
    private final WebSocketSessionService webSocketSessionService;

    public GameInitService (@NotNull WebSocketSessionService webSocketSessionService) {
        this.webSocketSessionService = webSocketSessionService;
    }

    public void initGameFor(GameSession gameSession) {
        final List<Integer> players = new ArrayList<>();
        players.add(gameSession.getPlayer1());
        players.add(gameSession.getPlayer2());
        try {
            for (Integer player : players) {
                webSocketSessionService.sendMessageToUser(
                        player, new GameInitResponse());
            }
        } catch (IOException e) {
            players.stream().forEach(playerToCutOff -> webSocketSessionService.closeConnection(playerToCutOff,
                    CloseStatus.SERVER_ERROR));
        }
        gameSession.getServer().startGame(gameSession.getPlayer1(), gameSession.getPlayer2());
    }
}
