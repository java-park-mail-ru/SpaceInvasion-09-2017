package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;
import ru.spaceinvasion.models.Coordinates;

/**
 * Created by egor on 17.11.17.
 */
public class CoinAppearanceMessage extends GameMessage {
    private Coordinates coordinates;

    public CoinAppearanceMessage(GamePart messageCreator, Long requestId, Coordinates coordinates) {
        super(messageCreator, requestId);
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
