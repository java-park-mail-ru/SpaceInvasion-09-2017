package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;

/**
 * Created by egor on 22.11.17.
 */
public class BombInstallingMessage extends GameMessage {

    private Long installedOnBaseOfPlayer;

    public BombInstallingMessage(GamePart messageCreator, Long requestId) {
        super(messageCreator, requestId);
    }

    public BombInstallingMessage(GamePart messageCreator, Long requestId, Long installedOnBaseOfPlayer) {
        super(messageCreator, requestId);
        this.installedOnBaseOfPlayer = installedOnBaseOfPlayer;
    }

    public Long getInstalledOnBaseOfPlayer() {
        return installedOnBaseOfPlayer;
    }
}
