package ru.spaceinvasion.mechanic.snaps;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.mechanic.game.Race;
import ru.spaceinvasion.mechanic.game.messages.*;
import ru.spaceinvasion.mechanic.game.models.CollisionEngine;
import ru.spaceinvasion.mechanic.game.models.Player;
import ru.spaceinvasion.mechanic.game.models.Tower;
import ru.spaceinvasion.mechanic.game.models.Unit;
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
//    7: game init
//    10: unit creation
//    11: coin creation
    //TODO Where is appearing coins?

    public ServerSnap(Race race, Integer enemyId) {
        data = new Integer[4];
        data[0] = 0;
        data[1] = 7;
        data[2] = (race == Race.PEOPLE) ? 0 : 1;
        data[3] = enemyId;
}

    public ServerSnap(RollbackMessage rollbackMessage) {
        data = new Integer[2];
        data[0] = rollbackMessage.getRequestId().intValue();
        data[1] = 1;
    }

    public ServerSnap(CollisionMessage collisionMessage) {
        data = new Integer[4];
        data[0] = collisionMessage.getRequestId().intValue();
        data[1] = 2;
        data[2] = (int) collisionMessage.getSrcOfDamage().getGamePartId();
        data[3] = (int) collisionMessage.getMessageCreator().getGamePartId();
    }

    public ServerSnap(DamageTowerMessage damageTowerMessage) {
        data = new Integer[5];
        data[0] = damageTowerMessage.getRequestId().intValue();
        data[1] = 2;
        data[2] = (int) damageTowerMessage.getSrcOfDamage().getGamePartId();
        data[3] = (int) damageTowerMessage.getMessageCreator().getGamePartId();
        data[4] = damageTowerMessage.getNumOfShot().intValue();
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

    public ServerSnap(BombInstallingMessage bombInstallingMessage) {
        data = new Integer[3];
        data[0] = bombInstallingMessage.getRequestId().intValue();
        data[1] = 5;
        data[2] = bombInstallingMessage.getInstalledOnBaseOfPlayer().intValue() * (-1);
    }

    public ServerSnap(ShootMessage shootMessage) {
        data = new Integer[6];
        data[0] = shootMessage.getRequestId().intValue();
        data[1] = 6;
        data[2] = shootMessage.getCoordinates().getX();
        data[3] = shootMessage.getCoordinates().getY();
        data[4] = shootMessage.getDirection().getValue();
        data[5] = (int)shootMessage.getMessageCreator().getGamePartId();
    }

    @Deprecated
    public ServerSnap(CashChangeMessage cashChangeMessage) {
        data = new Integer[3];
        data[0] = cashChangeMessage.getRequestId().intValue();
        data[1] = 8;
        data[2] = ((Player)cashChangeMessage.getMessageCreator()).getCoins();
    }

    @Deprecated
    public ServerSnap(DisappearingMessage disappearingMessage) {
        data = new Integer[3];
        data[0] = disappearingMessage.getRequestId().intValue();
        data[1] = 9;
        data[2] = (int)disappearingMessage.getMessageCreator().getGamePartId();
    }

    public ServerSnap(UnitCreationMessage unitCreationMessage) {
        data = new Integer[4];
        data[0] = unitCreationMessage.getRequestId().intValue();
        data[1] = 10;
        data[2] = (int)((Unit)unitCreationMessage.getMessageCreator()).getOwner().getGamePartId() * (-1);
        data[3] = (int)unitCreationMessage.getMessageCreator().getGamePartId();
    }

    public ServerSnap(CoinAppearanceMessage coinAppearanceMessage) {
        data = new Integer[4];
        data[0] = coinAppearanceMessage.getRequestId().intValue();
        data[1] = 11;
        data[2] = coinAppearanceMessage.getCoordinates().getX();
        data[3] = coinAppearanceMessage.getCoordinates().getY();
    }

}
