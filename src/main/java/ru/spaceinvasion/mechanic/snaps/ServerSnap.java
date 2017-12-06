package ru.spaceinvasion.mechanic.snaps;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.mechanic.game.Race;
import ru.spaceinvasion.mechanic.game.messages.*;
import ru.spaceinvasion.mechanic.game.models.Player;
import ru.spaceinvasion.models.*;

public class ServerSnap implements Message {

    @JsonProperty
    @NotNull
    private Integer[] data;

//    types:
//    0: state (reserved but not used)
//    1: rollback (rollback to lastAcceptedCommit)
//    2: collision (idOfSource(0 - bomb, 1 - unit),idOfTarget)
//    3: emeny's move
//    4: emeny's tower
//    5: emeny's bomb
//    6: emeny's shot
//    7: game init (using in GameInitResponse)
//    8: change account
//    9: disappearing (coin)
    //TODO Where is appearing coins?


    public ServerSnap(RollbackMessage rollbackMessage) {
        data = new Integer[2];
        data[0] = rollbackMessage.getRequestId().intValue();
        data[1] = 1;
    }

    public ServerSnap(DamageMessage damageMessage) {
        data = new Integer[4];
        data[0] = damageMessage.getRequestId().intValue();
        data[1] = 2;
        data[2] = damageMessage.getSrcOfDamageId().intValue();
        data[3] = (int)damageMessage.getMessageCreator().getGamePartId();
    }

    public ServerSnap(MoveMessage moveMessage) {
        data = new Integer[4];
        data[0] = moveMessage.getRequestId().intValue();
        data[1] = 3;
        data[2] = moveMessage.getCoordinates().getX();
        data[3] = moveMessage.getCoordinates().getY();
    }

    public ServerSnap(BuildTowerMessage buildTowerMessage) {
        data = new Integer[5];
        data[0] = buildTowerMessage.getRequestId().intValue();
        data[1] = 4;
        data[2] = buildTowerMessage.getCoordinates().getX();
        data[3] = buildTowerMessage.getCoordinates().getY();
        data[4] = buildTowerMessage.getDirection().getValue();
    }

    public ServerSnap(Integer idOfLastAcceptedCommitFromUser, BombSnap bombSnap) {
        data = new Integer[2];
        data[0] = idOfLastAcceptedCommitFromUser;
        data[1] = 5;
    }

    public ServerSnap(ShootMessage shootMessage) {
        data = new Integer[5];
        data[0] = shootMessage.getRequestId().intValue();
        data[1] = 6;
        data[2] = shootMessage.getCoordinates().getX();
        data[3] = shootMessage.getCoordinates().getY();
        data[4] = shootMessage.getDirection().getValue();
    }

    public ServerSnap(CashChangeMessage cashChangeMessage) {
        data = new Integer[4];
        data[0] = cashChangeMessage.getRequestId().intValue();
        data[1] = 8;
        data[3] = ((Player)cashChangeMessage.getMessageCreator()).getCoins();
    }

    public ServerSnap(DisappearingMessage disappearingMessage) {
        data = new Integer[4];
        data[0] = disappearingMessage.getRequestId().intValue();
        data[1] = 9;
        data[3] = (int)disappearingMessage.getMessageCreator().getGamePartId();
    }

    //            (CashChangeMessage::class.java) -> {
//        snaps.filter { it.key == message.messageCreator.gamePartId }.forEach { it.value.add(message)}
//    }
}
