package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;

/**
 * Created by egor on 17.11.17.
 */
public class NewUnitGameMessage extends GameMessage {

    public NewUnitGameMessage(GamePart messageCreator, Long messageId) {
        super(messageCreator, messageId);
    }
}
