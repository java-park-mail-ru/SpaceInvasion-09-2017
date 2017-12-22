package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;

/**
 * Created by egor on 22.11.17.
 */
public class FinishMessage extends GameMessage {

    public FinishMessage(GamePart messageCreator, Long requestId) {
        super(messageCreator, requestId);
    }

}
