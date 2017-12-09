package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;
import ru.spaceinvasion.models.Coordinates;

/**
 * Created by egor on 17.11.17.
 */
public class DisappearingMessage extends GameMessage {
    private final GamePart destroyer;
    public DisappearingMessage(GamePart messageCreator, Long requestId, GamePart destroyer) {
        super(messageCreator, requestId);
        this.destroyer = destroyer;
    }

    public GamePart getDestroyer() {
        return destroyer;
    }
}
