package ru.spaceinvasion.mechanic.internal.tasks;

import ru.spaceinvasion.mechanic.internal.GameTaskScheduler;
import ru.spaceinvasion.mechanic.snaps.ServerSnap;
import ru.spaceinvasion.services.WebSocketSessionService;

import static ru.spaceinvasion.utils.Constants.GameMechanicConstants.PING_MILLIS;

/**
 * Created by egor on 18.12.17.
 */
public class PingTask extends GameTaskScheduler.GameSessionTask {

    private final WebSocketSessionService webSocketSessionService;
    private final GameTaskScheduler gameTaskScheduler;

    public PingTask(WebSocketSessionService webSocketSessionService, GameTaskScheduler gameTaskScheduler) {
        this.webSocketSessionService = webSocketSessionService;
        this.gameTaskScheduler = gameTaskScheduler;
    }

    @Override
    public void operate() {
        webSocketSessionService.sendMessageToAll(new ServerSnap());
        gameTaskScheduler.schedule(PING_MILLIS, new PingTask(webSocketSessionService,gameTaskScheduler));
    }
}
