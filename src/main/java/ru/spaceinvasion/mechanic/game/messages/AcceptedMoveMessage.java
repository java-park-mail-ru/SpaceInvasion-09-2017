package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;
import ru.spaceinvasion.models.Coordinates;

/**
 * Created by egor on 17.11.17.
 */
public class AcceptedMoveMessage extends MoveMessage {

    //coordinates is new coordinates
    public AcceptedMoveMessage(GamePart messageCreator, Long requestId, Coordinates coordinates) {
        super(messageCreator, requestId, coordinates);
    }
}
