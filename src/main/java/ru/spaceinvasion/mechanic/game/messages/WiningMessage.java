package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;

/**
 * Created by egor on 22.11.17.
 */
public class WiningMessage extends GameMessage {

    public WiningMessage(GamePart messageCreator, Long requestId) {
        super(messageCreator, requestId);
    }

    public WiningMessage(WiningMessage damageMessage, GamePart messageCreator) {
        super(messageCreator, damageMessage.getRequestId());
    }

}
