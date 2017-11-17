package ru.spaceinvasion.mechanic.snaps;

import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.models.GameSession;
import ru.spaceinvasion.utils.Constants;
import ru.spaceinvasion.utils.Exceptions;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

/**
 * Created by egor on 15.11.17.
 */
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

    public void processSnapshotsForSession(GameSession gameSession) {
        processSnapshotsForUser(gameSession.getPlayer1());
        processSnapshotsForUser(gameSession.getPlayer2());
    }
    private void processSnapshotsForUser(Integer userId) {
        final List<ClientSnap> playerSnaps = getSnapForUser(userId);
        Integer lastSnapId = playerSnaps.get(playerSnaps.size() - 1).getIdOfRequest();
        for (Integer processedPerTick = 0;
             processedPerTick < Constants.GameMechanicConstants.NUM_OF_PROCESSED_SNAPS_PER_SERVER_TICK;
             processedPerTick++) {
            ClientSnap snap = playerSnaps.get(0);
            switch (snap.getType()) {
                case "move": processMove(snap); break;
                case "tower": processTowerBuild(snap); break;
                case "bomb": processBombAdding(snap); break;
                case "shot": processShot(snap); break;
                case "state": processStateRequest(snap); break;
            }
            lastSnapId = snap.getIdOfRequest();
            playerSnaps.remove(0);
        }
        if(playerSnaps.size() > 0) {
            playerSnaps.clear();
            throw new Exceptions.NumberOfRequestsHasExceeded(lastSnapId);
        }
    }

    private void processMove(ClientSnap snap) {
        //TODO
    }
    private void processTowerBuild(ClientSnap snap) {
        //TODO
    }
    private void processBombAdding(ClientSnap snap) {
        //TODO
    }
    private void processShot(ClientSnap snap) {
        //TODO
    }
    private void processStateRequest(ClientSnap snap) {
        throw new NotImplementedException();
    }
}
