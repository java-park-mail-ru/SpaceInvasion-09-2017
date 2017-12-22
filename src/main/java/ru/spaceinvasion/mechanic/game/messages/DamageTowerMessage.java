package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;

/**
 * Created by egor on 22.11.17.
 */
public class DamageTowerMessage extends CollisionMessage {

    private final Long numOfShot;

    public DamageTowerMessage(GamePart messageCreator, Long requestId, GamePart srcOfDamage, Long numOfShot) {
        super(messageCreator, requestId, srcOfDamage);
        this.numOfShot = numOfShot;
    }

    public DamageTowerMessage(DamageTowerMessage damageMessage, GamePart messageCreator) {
        super(messageCreator, damageMessage.getRequestId(), damageMessage.getSrcOfDamage());
        this.numOfShot = damageMessage.numOfShot;
    }

    public Long getNumOfShot() {
        return numOfShot;
    }
}
