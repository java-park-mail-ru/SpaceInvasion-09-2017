package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;

/**
 * Created by egor on 22.11.17.
 */
public class DamageShotMessage extends CollisionMessage {

    public DamageShotMessage(GamePart messageCreator, Long requestId, GamePart srcOfDamage) {
        super(messageCreator, requestId, srcOfDamage);
    }

    public DamageShotMessage(DamageShotMessage damageMessage, GamePart messageCreator) {
        super(messageCreator, damageMessage.getRequestId(), damageMessage.getSrcOfDamage());
    }
}
