package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.Direction;
import ru.spaceinvasion.mechanic.game.GamePart;

public class UnitStatusMessage extends GameMessage {
    public Boolean isAlive;

    public UnitStatusMessage(GamePart messageCreator, Long requestId, Boolean isAlive) {
        super(messageCreator, requestId);
        this.isAlive = isAlive;
    }

    public UnitStatusMessage(UnitStatusMessage unitStatusMessage, GamePart messageCreator) {
        super(messageCreator, unitStatusMessage.getRequestId());
        this.isAlive = unitStatusMessage.getIsAlive();
    }

    public Boolean getIsAlive() {
        return isAlive;
    }
}
