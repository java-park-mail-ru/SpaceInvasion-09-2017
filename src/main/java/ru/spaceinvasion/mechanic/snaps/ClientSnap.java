package ru.spaceinvasion.mechanic.snaps;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spaceinvasion.mechanic.game.Direction;
import ru.spaceinvasion.models.Coordinates;
import ru.spaceinvasion.models.Message;
import ru.spaceinvasion.utils.Exceptions;


public class ClientSnap implements Message {

    @JsonProperty
    Integer[] request;

    public ClientSnap(Integer[] request) {
        this.request = request;
    }

    public Integer getIdOfRequest() {
        try {
            return request[0];
        } catch (IndexOutOfBoundsException e) { }
        throw new Exceptions.NotValidData();
    }

    public String getType() {
        try {
            switch (request[1]) {
                case 0:
                    return "move";
                case 1:
                    return "tower";
                case 2:
                    return "bomb";
                case 3:
                    return "shot";
                case 4:
                    return "state"; //Reserved but not used yet
            }
        } catch (IndexOutOfBoundsException e) { }
        throw new Exceptions.NotValidData();
    }

    public Coordinates getCoordinates() {
        try {
            Coordinates coords =  new Coordinates(request[2],request[3]);
            return coords;
        } catch (IndexOutOfBoundsException e) { }
        throw new Exceptions.NotValidData();
    }

    public Direction getDirection() {
        try {
            switch (request[2]) {
                case 0: return Direction.UP;
                case 1: return Direction.UP_RIGHT;
                case 2: return Direction.RIGHT;
                case 3: return Direction.DOWN_RIGHT;
                case 4: return Direction.DOWN;
                case 5: return Direction.DOWN_LEFT;
                case 6: return Direction.LEFT;
                case 7: return Direction.UP_LEFT;
            }
        } catch (IndexOutOfBoundsException e) { }
        throw new Exceptions.NotValidData();
    }


}
