package ru.spaceinvasion.mechanic.handlers;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.spaceinvasion.mechanic.internal.GameMechanics;
import ru.spaceinvasion.mechanic.snaps.JoinRequest;
import ru.spaceinvasion.websocket.MessageHandlerContainer;

import javax.annotation.PostConstruct;

/**
 * Created by egor on 14.11.17.
 */

@Component
public class JoinMessageHandler extends MessageHandler<JoinRequest> {

    private GameMechanics gameMechanics;
    private MessageHandlerContainer messageHandlerContainer;

    public JoinMessageHandler(@NotNull GameMechanics gameMechanics,
                              @NotNull MessageHandlerContainer messageHandlerContainer) {
        super(JoinRequest.class);
        this.gameMechanics = gameMechanics;
        this.messageHandlerContainer = messageHandlerContainer;
    }

    @PostConstruct
    private void init() {
        messageHandlerContainer.registerHandler(JoinRequest.class, this);
    }

    @Override
    public void handle(JoinRequest message,  Integer forUserWithId) {
        gameMechanics.addUser(forUserWithId);
    }
}
