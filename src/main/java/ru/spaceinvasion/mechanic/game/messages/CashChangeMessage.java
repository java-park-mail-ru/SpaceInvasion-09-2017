package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;

/**
 * Created by egor on 17.11.17.
 */
public class CashChangeMessage extends GameMessage {
    private Integer dCash;

    public CashChangeMessage(GamePart messageCreator, Long requestId, Integer dCash) {
        super(messageCreator, requestId);
        this.dCash = dCash;
    }

    public Integer getdCash() {
        return dCash;
    }
}
