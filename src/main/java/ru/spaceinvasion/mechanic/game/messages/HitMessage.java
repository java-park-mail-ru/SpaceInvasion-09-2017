package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;

/**
 * Created by egor on 17.11.17.
 */
public class HitMessage extends GameMessage {
    private GamePart target;

    public HitMessage(GamePart messageCreator, Long requestId, GamePart target) {
        super(messageCreator, requestId);
        this.target = target;
    }

    public GamePart getTarget() {
        return target;
    }
}
