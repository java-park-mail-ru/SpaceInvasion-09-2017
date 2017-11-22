package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;
import ru.spaceinvasion.models.Coordinates;

/**
 * Created by egor on 17.11.17.
 */
public class DisappearingMessage extends GameMessage {
    public DisappearingMessage(GamePart messageCreator, Long messageId) {
        super(messageCreator, messageId);
    }
}
