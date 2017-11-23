package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;

/**
 * Created by egor on 22.11.17.
 */
public class DamageMessage extends GameMessage {

    private Long srcOfDamageId;

    public DamageMessage(GamePart messageCreator, Long messageId, Long srcOfDamageId) {
        super(messageCreator, messageId);
        this.srcOfDamageId = srcOfDamageId;
    }

    public DamageMessage(DamageMessage damageMessage, GamePart messageCreator) {
        super(messageCreator, damageMessage.getMessageId());
        this.srcOfDamageId = damageMessage.getSrcOfDamageId();
    }

    public Long getSrcOfDamageId() {
        return srcOfDamageId;
    }
}
