package ru.spaceinvasion.mechanic.snaps;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.mechanic.game.Side;
import ru.spaceinvasion.mechanic.game.messages.*;
import ru.spaceinvasion.mechanic.game.models.Unit;
import ru.spaceinvasion.models.*;

@SuppressWarnings("ClassWithTooManyConstructors")
public class ServerSnap implements Message {

    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    @JsonProperty
    private @NotNull Integer[] data;

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

    public ServerSnap(Side side, Integer enemyId) {
        data = new Integer[4];
        data[0] = 0;
        data[1] = 7;
        data[2] = (side == Side.LEFT) ? 0 : 1;
        data[3] = enemyId;
}

    public ServerSnap(Long lastSnapId, RollbackMessage rollbackMessage) {
        data = new Integer[2];
        data[0] = lastSnapId.intValue();
        data[1] = 1;
    }

    public ServerSnap(Long lastSnapId, CollisionMessage collisionMessage) {
        data = new Integer[5];
        data[0] = lastSnapId.intValue();
        data[1] = 2;
        data[2] = (int) collisionMessage.getSrcOfDamage().getGamePartId();
        data[3] = (int) collisionMessage.getMessageCreator().getGamePartId();
        data[4] = collisionMessage.getRequestId().intValue();
    }

    public ServerSnap(Long lastSnapId, DamageTowerMessage damageTowerMessage) {
        data = new Integer[6];
        data[0] = lastSnapId.intValue();
        data[1] = 2;
        data[2] = (int) damageTowerMessage.getSrcOfDamage().getGamePartId();
        data[3] = (int) damageTowerMessage.getMessageCreator().getGamePartId();
        data[4] = damageTowerMessage.getNumOfShot().intValue();
        data[5] = damageTowerMessage.getRequestId().intValue();
    }

    public ServerSnap(Long lastSnapId, MoveMessage moveMessage) {
        data = new Integer[4];
        data[0] = lastSnapId.intValue();
        data[1] = 3;
        data[2] = moveMessage.getCoordinates().getX();
        data[3] = moveMessage.getCoordinates().getY();
    }

    public ServerSnap(Long lastSnapId, BuildTowerMessage buildTowerMessage) {
        data = new Integer[6];
        data[0] = lastSnapId.intValue();
        data[1] = 4;
        data[2] = buildTowerMessage.getCoordinates().getX();
        data[3] = buildTowerMessage.getCoordinates().getY();
        data[4] = buildTowerMessage.getDirection().getValue();
        data[5] = buildTowerMessage.getRequestId().intValue();
    }

    public ServerSnap(Long lastSnapId, BombInstallingMessage bombInstallingMessage) {
        data = new Integer[3];
        data[0] = lastSnapId.intValue();
        data[1] = 5;
        data[2] = bombInstallingMessage.getInstalledOnBaseOfPlayer().intValue() * (-1);
    }

    public ServerSnap(Long lastSnapId, ShootMessage shootMessage) {
        data = new Integer[7];
        data[0] = lastSnapId.intValue();
        data[1] = 6;
        data[2] = shootMessage.getCoordinates().getX();
        data[3] = shootMessage.getCoordinates().getY();
        data[4] = shootMessage.getDirection().getValue();
        data[5] = (int)shootMessage.getMessageCreator().getGamePartId();
        data[6] = shootMessage.getRequestId().intValue();
    }

    public ServerSnap(Long lastSnapId, UnitCreationMessage unitCreationMessage) {
        data = new Integer[5];
        data[0] = lastSnapId.intValue();
        data[1] = 10;
        data[2] = (int)((Unit)unitCreationMessage.getMessageCreator()).getOwner().getGamePartId() * (-1);
        data[3] = (int)unitCreationMessage.getMessageCreator().getGamePartId();
        data[4] = unitCreationMessage.getRequestId().intValue();
    }

    public ServerSnap(Long lastSnapId, CoinAppearanceMessage coinAppearanceMessage) {
        data = new Integer[6];
        data[0] = lastSnapId.intValue();
        data[1] = 11;
        data[2] = coinAppearanceMessage.getCoordinates().getX();
        data[3] = coinAppearanceMessage.getCoordinates().getY();
        data[4] = (int)coinAppearanceMessage.getMessageCreator().getGamePartId();
        data[5] = coinAppearanceMessage.getRequestId().intValue();
    }

}
