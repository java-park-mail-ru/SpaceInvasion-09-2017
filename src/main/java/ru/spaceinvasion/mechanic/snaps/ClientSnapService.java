package ru.spaceinvasion.mechanic.snaps;

import org.springframework.stereotype.Service;
import ru.spaceinvasion.models.GameSession;
import ru.spaceinvasion.utils.Constants;
import ru.spaceinvasion.utils.Exceptions;

import java.util.*;

/**
 * Created by egor on 15.11.17.
 */

@Service
public class ClientSnapService {


    private static Integer debugSigmaDX = 0;
    private static Integer debugSigmaDY = 0;

    private final Map<Integer, List<ClientSnap>> snaps = new HashMap<>();

    public void pushClientSnap(Integer userId, ClientSnap snap) {
        this.snaps.putIfAbsent(userId, new ArrayList<>());
        final List<ClientSnap> clientSnaps = snaps.get(userId);
        clientSnaps.add(snap);
    }

    public List<ClientSnap> getSnapForUser(Integer userId) {
        return snaps.get(userId) != null ? snaps.get(userId) : Collections.emptyList();
    }

    public void processSnapshotsForSession(GameSession gameSession) {
        processSnapshotsForUserInSession(gameSession.getPlayer1(), gameSession);
        processSnapshotsForUserInSession(gameSession.getPlayer2(), gameSession);
    }

    @SuppressWarnings("OverlyComplexMethod")
    private void processSnapshotsForUserInSession(Integer userId, GameSession session) {
        final List<ClientSnap> playerSnaps = getSnapForUser(userId);
        Integer lastSnapId;
        try {
            lastSnapId = playerSnaps.get(playerSnaps.size() - 1).getIdOfRequest();
        } catch (IndexOutOfBoundsException ex) {
            lastSnapId = -1;
        }
        for (Integer processedPerTick = 0;
             processedPerTick < Constants.GameMechanicConstants.NUM_OF_PROCESSED_SNAPS_PER_SERVER_TICK;
             processedPerTick++) {
            final ClientSnap snap;
            try {
                snap = playerSnaps.get(0);
            } catch (IndexOutOfBoundsException ex) {
                break;
            }
            switch (snap.getType()) {
                case "move":
                    processMove(snap, userId, session);
                    break;
                case "tower":
                    processTowerBuild(snap, userId, session);
                    break;
                case "bomb":
                    processBombAdding(snap, userId, session);
                    break;
                case "shot":
                    processShot(snap, userId, session);
                    break;
                case "state":
                    processStateRequest(snap, userId, session);
                    break;
                case "accept_rollback":
                    processAcceptRollback(snap, userId, session);
                    break;
                default:
                    break;
            }
            lastSnapId = snap.getIdOfRequest();
            playerSnaps.remove(0);
        }
        if (!playerSnaps.isEmpty()) {
            playerSnaps.clear();
            processExceedingRequests(userId, session, lastSnapId);
        }
    }

    private void processMove(ClientSnap snap, Integer userId, GameSession session) {
        session.getServer().newClientMove(userId, snap.getIdOfRequest(), snap.getCoordinates());
    }

    private void processTowerBuild(ClientSnap snap, Integer userId, GameSession session) {
        session.getServer().newClientTower(userId, snap.getIdOfRequest(), snap.getDirection());
    }

    private void processBombAdding(ClientSnap snap, Integer userId, GameSession session) {
        session.getServer().newClientBomb(userId, snap.getIdOfRequest());
    }

    private void processShot(ClientSnap snap, Integer userId, GameSession session) {
        session.getServer().newClientShot(userId, snap.getIdOfRequest(), snap.getDirection());
    }

    private void processStateRequest(ClientSnap snap, Integer userId, GameSession session) {
        session.getServer().newClientStateRequest(userId, snap.getIdOfRequest());
    }

    private void processExceedingRequests(Integer userId, GameSession session, Integer lastSnapId) {
        session.getServer().newRollback(userId, lastSnapId);
    }

    private void processAcceptRollback(ClientSnap snap, Integer userId, GameSession session) {
        session.getServer().newAcceptRollback(userId);
    }

    public void reset() {
        //TODO
    }

}
