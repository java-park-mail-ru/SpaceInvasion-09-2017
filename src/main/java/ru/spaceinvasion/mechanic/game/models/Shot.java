package ru.spaceinvasion.mechanic.game.models;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spaceinvasion.mechanic.game.Direction;
import ru.spaceinvasion.mechanic.game.GamePart;
import ru.spaceinvasion.mechanic.game.Mediator;
import ru.spaceinvasion.mechanic.game.messages.GameMessage;
import ru.spaceinvasion.models.Coordinates;

/**
 * Created by egor on 17.11.17.
 */
public class Shot extends GamePart {

    @Nullable
    private Coordinates coordinates;

    @Nullable
    private Direction direction;

    public Shot(@NotNull Mediator mediator,
                @NotNull Integer gamePartId,
                @NotNull Coordinates coordinates,
                @NotNull Direction direction) {
        super(mediator, gamePartId);
        this.coordinates = coordinates;
        this.direction = direction;
    }

    @Override
    public void notify(GameMessage message) {

    }
}
