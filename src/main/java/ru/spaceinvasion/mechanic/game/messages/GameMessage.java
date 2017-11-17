package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;
import ru.spaceinvasion.models.Message;

/**
 * Created by egor on 17.11.17.
 */

public abstract class GameMessage implements Message {
    private Long messageId;

    private GamePart messageCreator;

    public GameMessage(GamePart messageCreator, Long messageId) {
        this.messageCreator = messageCreator;
        this.messageId = messageId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public GamePart getMessageCreator() {
        return messageCreator;
    }
}
