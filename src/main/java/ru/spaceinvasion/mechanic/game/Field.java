package ru.spaceinvasion.mechanic.game;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spaceinvasion.mechanic.game.messages.GameMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by egor on 17.11.17.
 */
public class Field extends GamePart {

    @NotNull
    private Player player1;
    @NotNull
    private Player player2;
    @Nullable
    private Unit unit1;
    @Nullable
    private Unit unit2;
    @NotNull
    private List<Tower> towers = new ArrayList<>();
    @NotNull
    private List<Shot> shots = new ArrayList<>();
    @NotNull
    private Boolean installedBombOn1 = false;
    @NotNull
    private Boolean installedBombOn2 = false;

    public Field(@NotNull Mediator mediator,
                 @NotNull Integer gamePartId,
                 @NotNull Player player1,
                 @NotNull Player player2) {
        super(mediator, gamePartId);
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void notify(GameMessage message) {

    }
}
