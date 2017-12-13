package ru.spaceinvasion.mechanic.internal;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import ru.spaceinvasion.mechanic.snaps.ClientSnap;
import ru.spaceinvasion.mechanic.snaps.ClientSnapService;
import ru.spaceinvasion.mechanic.snaps.ServerSnapService;
import ru.spaceinvasion.models.GameSession;
import ru.spaceinvasion.services.TimeService;
import ru.spaceinvasion.services.WebSocketSessionService;
import ru.spaceinvasion.utils.Exceptions;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by egor on 07.11.17.
 */
@Service
public class SpaceInvasionMechanics implements GameMechanics {


    private final @NotNull Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();


    private final @NotNull Queue<Integer> guysWhoWaitOpponent = new ConcurrentLinkedQueue<>();

    private final @NotNull GameSessionService gameSessionService;

    private final @NotNull ClientSnapService clientSnapService;

    private final @NotNull ServerSnapService serverSnapService;

    private final @NotNull GameTaskScheduler gameTaskScheduler;

    private final @NotNull TimeService timeService;

    private final @NotNull WebSocketSessionService webSocketSessionService;

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

    @Override
    public void addClientSnapshot(Integer userId, ClientSnap clientSnap) {
        tasks.add(() -> clientSnapService.pushClientSnap(userId, clientSnap));
    }

    @Override
    public void addUser(Integer userId) {
        if(gameSessionService.isPlaying(userId)) {
            throw new Exceptions.UserAlreadyIsPlaying();
        }
        guysWhoWaitOpponent.add(userId);
    }

    @Override
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
            session.getServer().tick();
            if (session.getServer().getGameIsEnded()) {
                gameSessionService.forceTerminate(session,false);
            }
        }

        gameTaskScheduler.tick();

        final List<GameSession> sessionsToTerminate = new ArrayList<>();

        for (GameSession session : gameSessionService.getSessions()) {

            try {
                serverSnapService.sendSnapshotsFor(
                        session.getPlayer1(),
                        session.getServer().getSnaps().get(new Long(session.getPlayer1()))
                );
                serverSnapService.sendSnapshotsFor(
                        session.getPlayer2(),
                        session.getServer().getSnaps().get(new Long(session.getPlayer2()))
                );
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

    @Override
    public void reset() {
        for (GameSession session : gameSessionService.getSessions()) {
            gameSessionService.forceTerminate(session, true);
        }
        guysWhoWaitOpponent.forEach(user -> webSocketSessionService.closeConnection(user, CloseStatus.SERVER_ERROR));
        guysWhoWaitOpponent.clear();
        tasks.clear();
        clientSnapService.reset();
        timeService.reset();
        gameTaskScheduler.reset();
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
        guysWhoWaitOpponent.addAll(matchedPlayers);
    }
}
