package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.Direction;
import ru.spaceinvasion.mechanic.game.GamePart;

/**
 * Created by egor on 17.11.17.
 */
public class TickMessage extends GameMessage {

    public TickMessage(GamePart messageCreator, Long messageId) {
        super(messageCreator, messageId);
    }

    public TickMessage(TickMessage buildTowerMessage, GamePart messageCreator) {
        super(messageCreator, buildTowerMessage.getMessageId());
    }
}
