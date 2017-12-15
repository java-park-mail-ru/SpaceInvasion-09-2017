package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;

/**
 * Created by egor on 22.11.17.
 */
public class RollbackMessage extends GameMessage {
    private Long problemSnapId;
    private String description;

    public RollbackMessage(GamePart messageCreator, Long requestId, String description) {
        super(messageCreator, requestId);
        this.problemSnapId = requestId;
        this.description = description;
    }

    public RollbackMessage(RollbackMessage rollbackMessage, GamePart messageCreator) {
        super(messageCreator, rollbackMessage.getRequestId());
        this.problemSnapId = rollbackMessage.problemSnapId;
        this.description = rollbackMessage.description;
    }


}
