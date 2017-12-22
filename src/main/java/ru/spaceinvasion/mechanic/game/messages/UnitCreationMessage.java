package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;

/**
 * Created by egor on 22.11.17.
 */
public class UnitCreationMessage extends GameMessage {

    public UnitCreationMessage(GamePart messageCreator, Long requestId) {
        super(messageCreator, requestId);
    }

}
