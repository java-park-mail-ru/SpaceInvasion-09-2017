package ru.spaceinvasion.mechanic.handlers;

import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.mechanic.internal.GameMechanics;
import ru.spaceinvasion.mechanic.requests.JoinRequest;

/**
 * Created by egor on 14.11.17.
 */
public class JoinMessageHandler extends MessageHandler<JoinRequest> {

    @NotNull
    GameMechanics gameMechanics;

    public JoinMessageHandler(@NotNull GameMechanics gameMechanics) {
        super(JoinRequest.class);
        this.gameMechanics = gameMechanics;
    }
    @Override
    public void handle(JoinRequest message,  Integer forUserWithId) {
        gameMechanics.addUser(forUserWithId);
    }
}
