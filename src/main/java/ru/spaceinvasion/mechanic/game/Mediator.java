package ru.spaceinvasion.mechanic.game;

import org.jetbrains.annotations.Nullable;
import ru.spaceinvasion.mechanic.game.messages.GameMessage;

/**
 * Created by egor on 17.11.17.
 */

public interface Mediator<T> {
    void send(GameMessage message, T colleague, @Nullable Integer colleagueId);

    <_T extends T> void registerColleague(Class<_T> clazz, T colleague);
}
