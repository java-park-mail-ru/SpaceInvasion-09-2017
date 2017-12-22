package ru.spaceinvasion.mechanic.internal.tasks;

import ru.spaceinvasion.mechanic.game.models.Server;
import ru.spaceinvasion.mechanic.internal.GameTaskScheduler;

import static ru.spaceinvasion.resources.Constants.MILLIS_TO_CREATING_COIN;

/**
 * Created by egor on 18.12.17.
 */
public class CoinCreationTask extends GameTaskScheduler.GameSessionTask {

    private final Server server;
    private final GameTaskScheduler gameTaskScheduler;

    public CoinCreationTask(Server server, GameTaskScheduler gameTaskScheduler) {
        this.server = server;
        this.gameTaskScheduler = gameTaskScheduler;
    }

    @Override
    public void operate() {
        server.newCoin();
        if (!server.getGameIsEnded())
            gameTaskScheduler.schedule(MILLIS_TO_CREATING_COIN, new CoinCreationTask(server, gameTaskScheduler));
    }
}
