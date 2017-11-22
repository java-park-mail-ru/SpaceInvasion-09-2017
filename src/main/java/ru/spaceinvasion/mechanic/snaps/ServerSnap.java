package ru.spaceinvasion.mechanic.snaps;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.models.*;

public class ServerSnap implements Message {

    @JsonProperty
    @NotNull
    private Integer[] response;

//    types:
//    0: state (reserved but not used)
//    1: rollback (rollback to lastAcceptedCommit)
//    2: damage (idOfSource(0 - bomb, 1 - unit),idOfTarget)
//    3: emeny's move
//    4: emeny's tower
//    5: emeny's bomb
//    6: emeny's shot

    public ServerSnap(Integer idOfLastAcceptedCommitFromUser) {
        response = new Integer[2];
        response[0] = idOfLastAcceptedCommitFromUser;
        response[1] = 1;
    }

    public ServerSnap(Integer idOfLastAcceptedCommitFromUser,
                      Integer idOfDamageSource,
                      Integer idOfDamageTarget) {
        response = new Integer[3];
        response[0] = idOfLastAcceptedCommitFromUser;
        response[1] = 2;
        response[2] = idOfDamageSource;
        response[3] = idOfDamageTarget;
    }

    public ServerSnap(Integer idOfLastAcceptedCommitFromUser, MoveSnap moveSnap) {
        response = new Integer[3];
        response[0] = idOfLastAcceptedCommitFromUser;
        response[1] = 3;
        response[2] = moveSnap.getCoordinates().getX();
        response[3] = moveSnap.getCoordinates().getY();
    }

    public ServerSnap(Integer idOfLastAcceptedCommitFromUser, TowerSnap towerSnap) {
        response = new Integer[4];
        response[0] = idOfLastAcceptedCommitFromUser;
        response[1] = 4;
        response[2] = towerSnap.getCoordinates().getX();
        response[3] = towerSnap.getCoordinates().getY();
        response[4] = towerSnap.getDirection();
    }

    public ServerSnap(Integer idOfLastAcceptedCommitFromUser, BombSnap bombSnap) {
        response = new Integer[1];
        response[0] = idOfLastAcceptedCommitFromUser;
        response[1] = 5;
    }

    public ServerSnap(Integer idOfLastAcceptedCommitFromUser, ShotSnap shotSnap) {
        response = new Integer[4];
        response[0] = idOfLastAcceptedCommitFromUser;
        response[1] = 6;
        response[2] = shotSnap.getCoordinates().getX();
        response[3] = shotSnap.getCoordinates().getY();
        response[4] = shotSnap.getDirection();
    }
}
