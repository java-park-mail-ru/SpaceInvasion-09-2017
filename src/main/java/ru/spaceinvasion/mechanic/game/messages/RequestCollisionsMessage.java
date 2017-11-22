package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;
import ru.spaceinvasion.models.Coordinates;

/**
 * Created by egor on 17.11.17.
 */
public class RequestCollisionsMessage extends GameMessage {
    private Coordinates potentialCoordinates;

    public RequestCollisionsMessage(GamePart messageCreator, Long messageId, Coordinates potentialCoordinates) {
        super(messageCreator, messageId);
        this.potentialCoordinates = potentialCoordinates;
    }

    public Coordinates getPotentialCoordinates() {
        return potentialCoordinates;
    }
}
