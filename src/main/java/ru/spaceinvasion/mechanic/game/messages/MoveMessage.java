package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;

/**
 * Created by egor on 17.11.17.
 */
public class MoveMessage extends GameMessage {
    public MoveMessage(GamePart messageCreator, Long messageId) {
        super(messageCreator, messageId);
    }
}
