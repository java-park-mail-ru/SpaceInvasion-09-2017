package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.Direction;
import ru.spaceinvasion.mechanic.game.GamePart;
import ru.spaceinvasion.models.Coordinates;

/**
 * Created by egor on 17.11.17.
 */
public class BuildTowerMessage extends GameMessage {
    public Coordinates coordinates;
    public Direction direction;

    public BuildTowerMessage(GamePart messageCreator, Long messageId, Coordinates coordinates, Direction direction) {
        super(messageCreator, messageId);
        this.coordinates = coordinates;
        this.direction = direction;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Direction getDirection() {
        return direction;
    }
}
