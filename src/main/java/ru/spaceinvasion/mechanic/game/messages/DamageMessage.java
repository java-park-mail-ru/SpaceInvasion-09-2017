package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;

/**
 * Created by egor on 22.11.17.
 */
public class DamageMessage extends GameMessage {

    private GamePart srcOfDamage;
    private Long numOfShot = null;

    public DamageMessage(GamePart messageCreator, Long requestId, GamePart srcOfDamage) {
        super(messageCreator, requestId);
        this.srcOfDamage = srcOfDamage;
    }

    public DamageMessage(GamePart messageCreator, Long requestId, GamePart srcOfDamage, Long numOfShot) {
        super(messageCreator, requestId);
        this.srcOfDamage = srcOfDamage;
        this.numOfShot = numOfShot;
    }

    public DamageMessage(DamageMessage damageMessage, GamePart messageCreator) {
        super(messageCreator, damageMessage.getRequestId());
        this.srcOfDamage = damageMessage.getSrcOfDamage();
    }

    public GamePart getSrcOfDamage() {
        return srcOfDamage;
    }

    public Long getNumOfShot() {
        return numOfShot;
    }
}
