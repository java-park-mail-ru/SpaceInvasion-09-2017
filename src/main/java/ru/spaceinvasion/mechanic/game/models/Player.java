package ru.spaceinvasion.mechanic.game.models;

import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.mechanic.game.GamePart;
import ru.spaceinvasion.mechanic.game.Mediator;
import ru.spaceinvasion.mechanic.game.Race;
import ru.spaceinvasion.mechanic.game.messages.GameMessage;

/**
 * Created by egor on 17.11.17.
 */
public class Player extends GamePart {
    @NotNull
    private Integer userId;
    private Race race;

    public Player(@NotNull Mediator mediator,
                  @NotNull Integer gamePartId,
                  @NotNull Integer userId,
                  @NotNull Race race) {
        super(mediator, gamePartId);
        this.userId = userId;
        this.race = race;
    }

    @Override
    public void notify(GameMessage message) {

    }
}
