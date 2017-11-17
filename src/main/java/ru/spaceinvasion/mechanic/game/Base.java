package ru.spaceinvasion.mechanic.game;

import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.mechanic.game.messages.GameMessage;

import static ru.spaceinvasion.resources.Constants.HEALTH_OF_BASE;

/**
 * Created by egor on 17.11.17.
 */
public class Base extends Damaging{

    public Base(@NotNull Mediator mediator,
                @NotNull Integer gamePartId) {
        super(mediator, gamePartId, HEALTH_OF_BASE);
    }

    @Override
    public void notify(GameMessage message) {

    }
}
