package ru.spaceinvasion.mechanic.game.messages;

import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.mechanic.game.GamePart;
import ru.spaceinvasion.models.Coordinates;

/**
 * Created by egor on 22.11.17.
 */
public class RollbackMessage extends GameMessage {
    @NotNull
    private Long problemSnapId;
    @NotNull
    private String description;

    public RollbackMessage(GamePart messageCreator, Long requestId, Long problemSnapId) {
        super(messageCreator, requestId);
        this.problemSnapId = problemSnapId;
        this.description = "";

    }

    public RollbackMessage(GamePart messageCreator, Long requestId, Long problemSnapId, String description) {
        super(messageCreator, requestId);
        this.problemSnapId = problemSnapId;
        this.description = description;
    }

    public RollbackMessage(RollbackMessage rollbackMessage, GamePart messageCreator) {
        super(messageCreator, rollbackMessage.getRequestId());
        this.problemSnapId = rollbackMessage.getProblemSnapId();
        this.description = rollbackMessage.getDescription();
    }

    public Long getProblemSnapId() {
        return problemSnapId;
    }

    public String getDescription() {
        return description;
    }
}
