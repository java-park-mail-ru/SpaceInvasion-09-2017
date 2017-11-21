package ru.spaceinvasion.mechanic.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.spaceinvasion.mechanic.game.Race;
import ru.spaceinvasion.models.Message;

public class RollbackResponse implements Message {
    private final Integer id;

    public RollbackResponse(Integer idOfLastProcessedSnap) {
        this.id = idOfLastProcessedSnap;
    }
}
