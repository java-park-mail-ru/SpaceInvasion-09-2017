package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.Direction;
import ru.spaceinvasion.mechanic.game.GamePart;
import ru.spaceinvasion.models.Coordinates;

/**
 * Created by egor on 17.11.17.
 */
public class ShootMessage extends GameMessage {
    Direction direction;
    Coordinates coordinatesOfShotAppearance;

    public ShootMessage(GamePart messageCreator, Long requestId, Direction direction) {
        super(messageCreator, requestId);
        this.direction = direction;
    }

    public ShootMessage(GamePart messageCreator, Long requestId, Direction direction, Coordinates coordinates) {
        super(messageCreator, requestId);
        this.direction = direction;
        this.coordinatesOfShotAppearance = coordinates;
    }



    public ShootMessage(ShootMessage buildTowerMessage, GamePart messageCreator) {
        super(messageCreator, buildTowerMessage.getRequestId());
        this.direction = buildTowerMessage.direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public Coordinates getCoordinates() {
        return coordinatesOfShotAppearance;
    }
}
