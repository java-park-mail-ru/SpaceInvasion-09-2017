package ru.spaceinvasion.mechanic.game.models;

import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.mechanic.game.Mediator;
import ru.spaceinvasion.mechanic.game.messages.GameMessage;
import ru.spaceinvasion.mechanic.game.models.Damaging;

import static ru.spaceinvasion.resources.Constants.HEALTH_OF_UNIT;

/**
 * Created by egor on 17.11.17.
 */
public class Unit extends Damaging {

    public Unit(@NotNull Mediator mediator, @NotNull Integer gamePartId) {
        super(mediator, gamePartId, HEALTH_OF_UNIT);
    }

    @Override
    public void notify(GameMessage message) {

    }
}
