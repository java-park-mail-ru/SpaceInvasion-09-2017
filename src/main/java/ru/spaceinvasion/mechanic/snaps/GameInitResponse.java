package ru.spaceinvasion.mechanic.snaps;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.spaceinvasion.mechanic.game.Race;
import ru.spaceinvasion.models.Message;

public class GameInitResponse implements Message {
    @JsonProperty
    private final Integer[] data = new Integer[4];

    //data[0] = idOfLastAcceptedSnap
    //data[1] = type
    //data[2] = side (0 - left,1 - right)
    //data[3] = enemyId
    public GameInitResponse(Race race, Integer enemyId) {
        data[0] = -1;
        data[1] = 7;
        data[3] = enemyId;
        data[2] = (race == Race.PEOPLE) ? 0 : 1;
    }
}
