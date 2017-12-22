package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;

/**
 * Created by egor on 22.11.17.
 */
public class CollisionMessage extends GameMessage {

    protected GamePart srcOfDamage;

    public CollisionMessage(GamePart messageCreator, Long requestId, GamePart srcOfDamage) {
        super(messageCreator, requestId);
        this.srcOfDamage = srcOfDamage;
    }

    public CollisionMessage(CollisionMessage collisionMessage, GamePart messageCreator) {
        super(messageCreator, collisionMessage.getRequestId());
        this.srcOfDamage = collisionMessage.srcOfDamage;
    }

    public GamePart getSrcOfDamage() {
        return srcOfDamage;
    }
}
