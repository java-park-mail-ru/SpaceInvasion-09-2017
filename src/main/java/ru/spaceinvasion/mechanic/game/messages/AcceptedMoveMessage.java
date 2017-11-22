package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;
import ru.spaceinvasion.models.Coordinates;
import ru.spaceinvasion.models.Move;

/**
 * Created by egor on 17.11.17.
 */
public class AcceptedMoveMessage extends MoveMessage {

    //coordinates is new coordinates
    public AcceptedMoveMessage(GamePart messageCreator, Long messageId, Coordinates coordinates) {
        super(messageCreator, messageId, coordinates);
    }
}
