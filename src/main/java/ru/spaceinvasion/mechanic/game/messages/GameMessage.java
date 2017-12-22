package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;
import ru.spaceinvasion.models.Message;

/**
 * Created by egor on 17.11.17.
 */

public abstract class GameMessage implements Message {
    private Long requestId;

    private GamePart messageCreator;

    public GameMessage(GamePart messageCreator, Long requestId) {
        this.messageCreator = messageCreator;
        this.requestId = requestId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public GamePart getMessageCreator() {
        return messageCreator;
    }
}
