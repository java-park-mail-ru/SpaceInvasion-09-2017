package ru.spaceinvasion.mechanic.game;

import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.mechanic.game.messages.GameMessage;

public abstract class GamePart {

    @NotNull
    private Boolean isExisted = true;
    @NotNull
    protected Mediator mediator;
    @NotNull
    protected final Integer gamePartId;

    public GamePart(@NotNull Mediator mediator,
                    @NotNull Integer gamePartId) {
        this.mediator = mediator;
        this.gamePartId = gamePartId;
    }

    public Integer getGamePartId() {
        return gamePartId;
    }

    public Boolean getExisted() {
        return isExisted;
    }

    public void setExisted(Boolean existed) {
        isExisted = existed;
    }

    public abstract void notify(GameMessage message);
}
