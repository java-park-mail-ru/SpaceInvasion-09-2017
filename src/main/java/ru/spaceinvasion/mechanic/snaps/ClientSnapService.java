package ru.spaceinvasion.mechanic.snaps;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.spaceinvasion.mechanic.responses.RollbackResponse;
import ru.spaceinvasion.models.GameSession;
import ru.spaceinvasion.utils.Constants;
import ru.spaceinvasion.utils.Exceptions;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

/**
 * Created by egor on 15.11.17.
 */

@Service
public class ClientSnapService {

    private final Map<Integer, List<ClientSnap>> snaps = new HashMap<>();

    public void pushClientSnap(Integer userId, ClientSnap snap) {
        this.snaps.putIfAbsent(userId, new ArrayList<>());
        final List<ClientSnap> clientSnaps = snaps.get(userId);
        clientSnaps.add(snap);
    }

    public List<ClientSnap> getSnapForUser(Integer userId) {
        return snaps.get(userId) != null ? snaps.get(userId) : Collections.emptyList();
    }

    public List<Map.Entry<Integer, RollbackResponse>> processSnapshotsForSession(GameSession gameSession) {
        List<Map.Entry<Integer, RollbackResponse>> rollbacks = new ArrayList<>();
        try {
            processSnapshotsForUserInSession(gameSession.getPlayer1(), gameSession);
        } catch (Exceptions.NumberOfRequestsHasExceeded e) {
            rollbacks.add(new AbstractMap.SimpleEntry<Integer, RollbackResponse>(gameSession.getPlayer1(), new RollbackResponse(e.getIdOfLastProcessedSnap())));
        }
        try {
            processSnapshotsForUserInSession(gameSession.getPlayer2(), gameSession);
        } catch (Exceptions.NumberOfRequestsHasExceeded e) {
            rollbacks.add(new AbstractMap.SimpleEntry<Integer, RollbackResponse>(gameSession.getPlayer2(), new RollbackResponse(e.getIdOfLastProcessedSnap())));
        }
        return rollbacks;
    }
    private void processSnapshotsForUserInSession(Integer userId, GameSession session) {
        final List<ClientSnap> playerSnaps = getSnapForUser(userId);
        Integer lastSnapId = playerSnaps.get(playerSnaps.size() - 1).getIdOfRequest();
        for (Integer processedPerTick = 0;
             processedPerTick < Constants.GameMechanicConstants.NUM_OF_PROCESSED_SNAPS_PER_SERVER_TICK;
             processedPerTick++) {
            ClientSnap snap = playerSnaps.get(processedPerTick);
            switch (snap.getType()) {
                case "move": processMove(snap, userId, session); break;
                case "tower": processTowerBuild(snap, userId, session); break;
                case "bomb": processBombAdding(snap, userId, session); break;
                case "shot": processShot(snap, userId, session); break;
                case "state": processStateRequest(snap, userId, session); break;
            }
            lastSnapId = snap.getIdOfRequest();
            playerSnaps.remove(0);
        }
        if(playerSnaps.size() > 0) {
            playerSnaps.clear();
            throw new Exceptions.NumberOfRequestsHasExceeded(userId, lastSnapId);
        }
    }

    private void processMove(ClientSnap snap, Integer userId, GameSession session) {
        session.getServer().newClientMove(userId, snap.getIdOfRequest(), snap.getCoordinates());
    }
    private void processTowerBuild(ClientSnap snap, Integer userId, GameSession session) {
        session.getServer().newClientTower(userId, snap.getIdOfRequest(), snap.getCoordinates(), snap.getDirection());
    }
    private void processBombAdding(ClientSnap snap, Integer userId, GameSession session) {
        session.getServer().newClientBomb(userId, snap.getIdOfRequest());
    }
    private void processShot(ClientSnap snap, Integer userId, GameSession session) {
        session.getServer().newClientShot(userId, snap.getIdOfRequest(), snap.getCoordinates(), snap.getDirection());
    }
    private void processStateRequest(ClientSnap snap, Integer userId, GameSession session) {
        session.getServer().newClientStateRequest(userId, snap.getIdOfRequest());
    }
}
