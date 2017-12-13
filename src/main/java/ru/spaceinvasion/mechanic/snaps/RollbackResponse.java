package ru.spaceinvasion.mechanic.snaps;

import ru.spaceinvasion.models.Message;

public class RollbackResponse implements Message {
    private final Integer id;

    public RollbackResponse(Integer idOfLastProcessedSnap) {
        this.id = idOfLastProcessedSnap;
    }
}
