package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;
import ru.spaceinvasion.models.Coordinates;

/**
 * Created by egor on 17.11.17.
 */
public class CashChangeMessage extends GameMessage {
    private Integer dCash;

    public CashChangeMessage(GamePart messageCreator, Long messageId, Integer dCash) {
        super(messageCreator, messageId);
        this.dCash = dCash;
    }

    public Integer getdCash() {
        return dCash;
    }
}
