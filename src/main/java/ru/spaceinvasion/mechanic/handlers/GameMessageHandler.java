package ru.spaceinvasion.mechanic.handlers;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.spaceinvasion.mechanic.internal.GameMechanics;
import ru.spaceinvasion.mechanic.snaps.ClientSnap;
import ru.spaceinvasion.websocket.MessageHandlerContainer;

import javax.annotation.PostConstruct;

/**
 * Created by egor on 17.11.17.
 */

@Component
public class GameMessageHandler extends MessageHandler<ClientSnap> {
    private GameMechanics gameMechanics;
    private MessageHandlerContainer messageHandlerContainer;

    public GameMessageHandler(@NotNull GameMechanics gameMechanics, @NotNull MessageHandlerContainer messageHandlerContainer) {
        super(ClientSnap.class);
        this.gameMechanics = gameMechanics;
        this.messageHandlerContainer = messageHandlerContainer;
    }

    @PostConstruct
    private void init() {
        messageHandlerContainer.registerHandler(ClientSnap.class, this);
    }

    @Override
    public void handle(ClientSnap message, Integer userId) {
        gameMechanics.addClientSnapshot(userId, message);
    }
}
