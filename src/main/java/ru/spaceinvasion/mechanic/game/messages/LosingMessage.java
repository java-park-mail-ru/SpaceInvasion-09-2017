package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;

/**
 * Created by egor on 22.11.17.
 */
public class LosingMessage extends GameMessage {

    public LosingMessage(GamePart messageCreator, Long messageId) {
        super(messageCreator, messageId);
    }

    public LosingMessage(LosingMessage damageMessage, GamePart messageCreator) {
        super(messageCreator, damageMessage.getMessageId());
    }

}
