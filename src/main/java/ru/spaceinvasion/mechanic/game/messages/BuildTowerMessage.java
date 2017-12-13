package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.Direction;
import ru.spaceinvasion.mechanic.game.GamePart;
import ru.spaceinvasion.models.Coordinates;

/**
 * Created by egor on 17.11.17.
 */

public class BuildTowerMessage extends GameMessage {
    private Direction direction;

    private Coordinates coordinates;

    public BuildTowerMessage(GamePart messageCreator, Long requestId, Direction direction) {
        super(messageCreator, requestId);
        this.direction = direction;
    }

    public BuildTowerMessage(BuildTowerMessage buildTowerMessage, GamePart messageCreator) {
        super(messageCreator, buildTowerMessage.getRequestId());
        this.direction = buildTowerMessage.direction;
    }

    public BuildTowerMessage(BuildTowerMessage buildTowerMessage, GamePart messageCreator, Coordinates coordinates) {
        super(messageCreator, buildTowerMessage.getRequestId());
        this.direction = buildTowerMessage.direction;
        this.coordinates = coordinates;
    }

    public Direction getDirection() {
        return direction;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
