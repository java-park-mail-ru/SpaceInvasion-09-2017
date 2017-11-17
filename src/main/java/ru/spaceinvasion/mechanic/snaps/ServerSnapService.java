package ru.spaceinvasion.mechanic.snaps;

import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.models.GameSession;
import ru.spaceinvasion.services.WebSocketSessionService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by egor on 15.11.17.
 */
public class ServerSnapService {

    @NotNull
    private WebSocketSessionService webSocketSessionService;

    public ServerSnapService (@NotNull WebSocketSessionService webSocketSessionService) {
        this.webSocketSessionService = webSocketSessionService;
    }

    public void sendSnapshotsFor(Integer userId, long frameTime) {
        //TODO
    }
}
