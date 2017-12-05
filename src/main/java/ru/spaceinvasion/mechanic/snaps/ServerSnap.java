package ru.spaceinvasion.mechanic.snaps;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.mechanic.game.Race;
import ru.spaceinvasion.mechanic.game.messages.BuildTowerMessage;
import ru.spaceinvasion.mechanic.game.messages.MoveMessage;
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


    public ServerSnap(Integer idOfLastAcceptedCommitFromUser) {
        data = new Integer[2];
        data[0] = idOfLastAcceptedCommitFromUser;
        data[1] = 1;
    }

    public ServerSnap(Integer idOfLastAcceptedCommitFromUser,
                      Integer idOfDamageSource,
                      Integer idOfDamageTarget) {
        data = new Integer[3];
        data[0] = idOfLastAcceptedCommitFromUser;
        data[1] = 2;
        data[2] = idOfDamageSource;
        data[3] = idOfDamageTarget;
    }

    public ServerSnap(Integer idOfLastAcceptedCommitFromUser, MoveMessage moveSnap) {
        data = new Integer[3];
        data[0] = idOfLastAcceptedCommitFromUser;
        data[1] = 3;
        data[2] = moveSnap.getCoordinates().getX();
        data[3] = moveSnap.getCoordinates().getY();
    }

    public ServerSnap(Integer idOfLastAcceptedCommitFromUser, BuildTowerMessage towerSnap) {
        data = new Integer[4];
        data[0] = idOfLastAcceptedCommitFromUser;
        data[1] = 4;
        data[2] = towerSnap.getCoordinates().getX();
        data[3] = towerSnap.getCoordinates().getY();
        data[4] = towerSnap.getDirection().getValue();
    }

    public ServerSnap(Integer idOfLastAcceptedCommitFromUser, BombSnap bombSnap) {
        data = new Integer[1];
        data[0] = idOfLastAcceptedCommitFromUser;
        data[1] = 5;
    }

    public ServerSnap(Integer idOfLastAcceptedCommitFromUser, ShotSnap shotSnap) {
        data = new Integer[4];
        data[0] = idOfLastAcceptedCommitFromUser;
        data[1] = 6;
        data[2] = shotSnap.getCoordinates().getX();
        data[3] = shotSnap.getCoordinates().getY();
        data[4] = shotSnap.getDirection();
    }

    public ServerSnap(Race race) {
        data = new Integer[1];
        if (race == Race.PEOPLE) {
            data[0] = 0;
        } else {
            data[0] = 1;
        }
    }
}
