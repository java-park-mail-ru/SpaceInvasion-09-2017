package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;

/**
 * Created by egor on 17.11.17.
 */
public class TickMessage extends GameMessage {

    public TickMessage(GamePart messageCreator, Long requestId) {
        super(messageCreator, requestId);
    }

}
