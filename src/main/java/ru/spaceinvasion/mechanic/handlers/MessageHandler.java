package ru.spaceinvasion.mechanic.handlers;

import ru.spaceinvasion.models.Message;
import ru.spaceinvasion.utils.Exceptions;

public abstract class MessageHandler<T extends Message> {
    private final Class<T> clazz;

    public MessageHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void handleMessage(Message message, Integer userId) {
        try {
            handle(clazz.cast(message), userId);
        } catch (ClassCastException e) {
            throw new Exceptions.HandleException("Can't read incoming message of type " + message.getClass());
        }
    }

    public abstract void handle(T message, Integer userId);
}
