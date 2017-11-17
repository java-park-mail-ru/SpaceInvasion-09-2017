package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.models.Message;

/**
 * Created by egor on 17.11.17.
 */

public abstract class GameMessage implements Message {
    private Integer messageId;

    public GameMessage(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
}
