package ru.spaceinvasion.mechanic.game.messages;

import ru.spaceinvasion.mechanic.game.GamePart;

/**
 * Created by egor on 17.11.17.
 */
public class StartGameMessage extends GameMessage {

    private Integer playerPeopleId;
    private Integer playerAliensId;

    public StartGameMessage(GamePart messageCreator, Long messageId, Integer playerPeopleId, Integer playerAliensId) {
        super(messageCreator, messageId);
        this.playerPeopleId = playerPeopleId;
        this.playerAliensId = playerAliensId;
    }

    public Integer getPlayerPeopleId() {
        return playerPeopleId;
    }

    public void setPlayerPeopleId(Integer playerPeopleId) {
        this.playerPeopleId = playerPeopleId;
    }

    public Integer getPlayerAliensId() {
        return playerAliensId;
    }

    public void setPlayer2Id(Integer playerAliensId) {
        this.playerAliensId = playerAliensId;
    }
}
