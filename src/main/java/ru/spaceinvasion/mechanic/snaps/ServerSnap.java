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

    public ServerSnap(Integer idOfLastAcceptedCommitFromUser, Move move) {
        response = new Integer[3];
        response[0] = idOfLastAcceptedCommitFromUser;
        response[1] = 3;
        response[2] = move.getCoordinates().getX();
        response[3] = move.getCoordinates().getY();
    }

    public ServerSnap(Integer idOfLastAcceptedCommitFromUser, Tower tower) {
        response = new Integer[4];
        response[0] = idOfLastAcceptedCommitFromUser;
        response[1] = 4;
        response[2] = tower.getCoordinates().getX();
        response[3] = tower.getCoordinates().getY();
        response[4] = tower.getDirection();
    }

    public ServerSnap(Integer idOfLastAcceptedCommitFromUser, Bomb bomb) {
        response = new Integer[1];
        response[0] = idOfLastAcceptedCommitFromUser;
        response[1] = 5;
    }

    public ServerSnap(Integer idOfLastAcceptedCommitFromUser, Shot shot) {
        response = new Integer[4];
        response[0] = idOfLastAcceptedCommitFromUser;
        response[1] = 6;
        response[2] = shot.getCoordinates().getX();
        response[3] = shot.getCoordinates().getY();
        response[4] = shot.getDirection();
    }
}
