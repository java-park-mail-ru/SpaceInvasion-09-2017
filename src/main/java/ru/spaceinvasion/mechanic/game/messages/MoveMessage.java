package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;
import ru.spaceinvasion.models.Coordinates;

/**
 * Created by egor on 17.11.17.
 */
public class MoveMessage extends GameMessage {

    //deltaCoordinates
    private Coordinates coordinates;

    public MoveMessage(GamePart messageCreator, Long messageId, Coordinates coordinates) {
        super(messageCreator, messageId);
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
