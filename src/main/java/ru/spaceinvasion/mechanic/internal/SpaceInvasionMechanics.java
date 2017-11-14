package ru.spaceinvasion.mechanic.internal;

import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.mechanic.snaps.ClientSnap;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by egor on 07.11.17.
 */
public class SpaceInvasionMechanics implements GameMechanics {

    @NotNull
    private final Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();

    @NotNull
    private final Queue<Integer> guysWhoWaitOpponent = new ConcurrentLinkedQueue<>();

    @NotNull
    private final GameSessionService gameSessionService;

    public SpaceInvasionMechanics(@NotNull GameSessionService gameSessionService) {
        this.gameSessionService = gameSessionService;
    }

    public void addClientSnapshot(Integer userId, ClientSnap clientSnap) {

    }

    public void addUser(Integer userId) {
        if(gameSessionService.isPlaying(userId)) {
            return; //Or throw exception?
        }
        guysWhoWaitOpponent.add(userId);
    }

    public void gmStep(long frameTime) {

    }

    public void reset() {

    }

    private void tryStartGames() {
        final Set<Integer> matchedPlayers = new LinkedHashSet<>();

        while (guysWhoWaitOpponent.size() >= 2 || guysWhoWaitOpponent.size() >= 1 && matchedPlayers.size() >= 1) {
            final Integer candidateId = guysWhoWaitOpponent.poll();
            matchedPlayers.add(candidateId);
            if (matchedPlayers.size() == 2) {
                final Iterator<Integer> iterator = matchedPlayers.iterator();
                gameSessionService.startGame(iterator.next(), iterator.next());
                matchedPlayers.clear();
            }
        }
        matchedPlayers.stream().forEach(guysWhoWaitOpponent::add);
    }
}
