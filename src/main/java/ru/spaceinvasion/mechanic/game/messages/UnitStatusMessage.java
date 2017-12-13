package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;

public class UnitStatusMessage extends GameMessage {
    private Boolean isAlive;

    public UnitStatusMessage(GamePart messageCreator, Long requestId, Boolean isAlive) {
        super(messageCreator, requestId);
        this.isAlive = isAlive;
    }

    public Boolean getIsAlive() {
        return isAlive;
    }
}
