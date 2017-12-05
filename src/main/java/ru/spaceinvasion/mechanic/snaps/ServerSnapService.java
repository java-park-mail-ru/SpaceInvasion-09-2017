package ru.spaceinvasion.mechanic.snaps;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.spaceinvasion.models.GameSession;
import ru.spaceinvasion.models.Message;
import ru.spaceinvasion.services.WebSocketSessionService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by egor on 15.11.17.
 */

@Service
public class ServerSnapService {

    @NotNull
    private WebSocketSessionService webSocketSessionService;

    public ServerSnapService (@NotNull WebSocketSessionService webSocketSessionService) {
        this.webSocketSessionService = webSocketSessionService;
    }

    public void sendSnapshotsFor(Integer userId, List<Message> serverSnaps) {
        for( Message message : serverSnaps ) {
            sendSnapshotsFor(userId, message);
        }
    }

    public void sendSnapshotsFor(Integer userId, Message serverSnap) {
        try {
            webSocketSessionService.sendMessageToUser(userId, serverSnap);
        } catch (IOException ex) {
            throw new RuntimeException("Failed  sending snapshot", ex);
        }
    }
}
