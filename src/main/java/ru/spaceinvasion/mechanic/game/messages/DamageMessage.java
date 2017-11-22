package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;

/**
 * Created by egor on 22.11.17.
 */
public class DamageMessage extends GameMessage {

    public DamageMessage(GamePart messageCreator, Long messageId) {
        super(messageCreator, messageId);
    }

    public DamageMessage(DamageMessage damageMessage, GamePart messageCreator) {
        super(messageCreator, damageMessage.getMessageId());
    }

}
