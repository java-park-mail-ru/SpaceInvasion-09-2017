package ru.spaceinvasion.websocket;

import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.models.Message;

/**
 * Created by egor on 06.11.17.
 */
public interface MessageHandlerContainer {

    void collect(Message message, Integer userId);

    <T extends Message> void registerHandler(Class<T> clazz, MessageHandler<T> handler);

}
