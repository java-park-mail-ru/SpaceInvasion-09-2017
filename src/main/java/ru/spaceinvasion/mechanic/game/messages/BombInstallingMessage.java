package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;

/**
 * Created by egor on 22.11.17.
 */
public class BombInstallingMessage extends GameMessage {

    public BombInstallingMessage(GamePart messageCreator, Long messageId) {
        super(messageCreator, messageId);
    }

    public BombInstallingMessage(BombInstallingMessage bombInstallingMessage, GamePart messageCreator) {
        super(messageCreator, bombInstallingMessage.getMessageId());
    }

}
