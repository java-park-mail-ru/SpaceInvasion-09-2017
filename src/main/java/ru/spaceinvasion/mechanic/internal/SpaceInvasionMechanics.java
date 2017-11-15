package ru.spaceinvasion.mechanic.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.CloseStatus;
import ru.spaceinvasion.mechanic.snaps.ClientSnap;
import ru.spaceinvasion.mechanic.snaps.ClientSnapService;
import ru.spaceinvasion.mechanic.snaps.ServerSnap;
import ru.spaceinvasion.mechanic.snaps.ServerSnapService;
import ru.spaceinvasion.models.GameSession;
import ru.spaceinvasion.services.TimeService;
import ru.spaceinvasion.services.WebSocketSessionService;

import java.util.*;
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

    @NotNull
    private final ClientSnapService clientSnapService;

    @NotNull
    private final ServerSnapService serverSnapService;

    @NotNull
    private final GameTaskScheduler gameTaskScheduler;

    @NotNull
    private final TimeService timeService;

    @NotNull
    private final WebSocketSessionService webSocketSessionService;

    public SpaceInvasionMechanics(@NotNull GameSessionService gameSessionService,
                                  @NotNull ClientSnapService clientSnapService,
                                  @NotNull ServerSnapService serverSnapService,
                                  @NotNull GameTaskScheduler gameTaskScheduler,
                                  @NotNull TimeService timeService,
                                  @NotNull WebSocketSessionService webSocketSessionService) {
        this.gameSessionService = gameSessionService;
        this.clientSnapService = clientSnapService;
        this.serverSnapService = serverSnapService;
        this.gameTaskScheduler = gameTaskScheduler;
        this.timeService = timeService;
        this.webSocketSessionService = webSocketSessionService;
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
        while (!tasks.isEmpty()) {
            final Runnable nextTask = tasks.poll();
            if (nextTask != null) {
                try {
                    nextTask.run();
                } catch (RuntimeException ex) {
                    //TODO
                }
            }
        }

        for (GameSession session : gameSessionService.getSessions()) {
            clientSnapService.processSnapshotsForSession(session);
        }

        gameTaskScheduler.tick();

        final List<GameSession> sessionsToTerminate = new ArrayList<>();

        for (GameSession session : gameSessionService.getSessions()) {

            try {
                serverSnapService.sendSnapshotsFor(session, frameTime);
            } catch (RuntimeException ex) {
                sessionsToTerminate.add(session);
            }
        }
        //TODO: Rewrite
        sessionsToTerminate
                .forEach(session ->
                        webSocketSessionService.closeConnection(session.getPlayer1(), CloseStatus.NORMAL));
        sessionsToTerminate
                .forEach(session ->
                        webSocketSessionService.closeConnection(session.getPlayer2(), CloseStatus.NORMAL));
        tryStartGames();
        timeService.tick(frameTime);

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
