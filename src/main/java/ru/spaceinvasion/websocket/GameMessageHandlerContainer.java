package ru.spaceinvasion.websocket;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.spaceinvasion.mechanic.handlers.MessageHandler;
import ru.spaceinvasion.models.Message;
import ru.spaceinvasion.utils.Exceptions;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameMessageHandlerContainer implements MessageHandlerContainer {

    private static final @NotNull Logger LOGGER = LoggerFactory.getLogger(GameMessageHandlerContainer.class);
    final Map<Class<?>, MessageHandler<?>> handlerMap = new HashMap<>();

    @Override
    public void collect(Message message, Integer userId) {
        final MessageHandler<?> messageHandler = handlerMap.get(message.getClass());
        if (messageHandler == null) {
            throw new Exceptions.HandleException("no handler for message of " + message.getClass().getName() + " type");
        }
        messageHandler.handleMessage(message, userId);
        LOGGER.trace("message handled: type =[" + message.getClass().getName()+ ']');
    }

    @Override
    public <T extends Message> void registerHandler(Class<T> clazz, MessageHandler<T> handler) {
        handlerMap.put(clazz, handler);
    }
}
