package ru.spaceinvasion.mechanic.game;

import org.jetbrains.annotations.Nullable;
import ru.spaceinvasion.mechanic.game.messages.GameMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePartMediator implements Mediator<GamePart>{

    private Map<Class<?>, List<GamePart>> colleagues = new HashMap();

    @Override
    public <T extends GamePart> void registerColleague(Class<T> clazz, GamePart gamePart) {
        List<GamePart> gameParts;
        if (colleagues.containsKey(clazz)) {
            gameParts = colleagues.get(clazz);
            gameParts.add(gamePart);
        } else {
            gameParts = new ArrayList<>();
            gameParts.add(gamePart);
            colleagues.put(clazz,gameParts);
        }
    }

    @Override
    public void send(GameMessage message, GamePart gamePart, @Nullable Integer gamePartId) {
        List<GamePart> gameParts = colleagues.get(gamePart.getClass());
        for(GamePart _gamePart : gameParts) {
            if (gamePartId == null || _gamePart.getGamePartId().equals(gamePartId)) {
                _gamePart.notify(message);
            }
        }
    }
}
