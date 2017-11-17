package ru.spaceinvasion.mechanic.game.models;

import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.mechanic.game.GamePart;
import ru.spaceinvasion.mechanic.game.Mediator;
import ru.spaceinvasion.mechanic.game.messages.GameMessage;

/**
 * Created by egor on 17.11.17.
 */
public class Bomb extends GamePart {

    public Bomb(@NotNull Mediator mediator, @NotNull Integer gamePartId) {
        super(mediator, gamePartId);
    }

    @Override
    public void notify(GameMessage message) {

    }
}
