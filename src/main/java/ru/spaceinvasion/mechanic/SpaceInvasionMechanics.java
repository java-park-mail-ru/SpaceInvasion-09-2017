package ru.spaceinvasion.mechanic;

import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.mechanic.base.ClientSnap;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by egor on 07.11.17.
 */
public class SpaceInvasionMechanics implements GameMechanics {

    @NotNull
    private final Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();

    public SpaceInvasionMechanics() {
        
    }

    public void addClientSnapshot(Integer userId, ClientSnap clientSnap) {

    }

    public void addUser(Integer userId) {

    }

    public void gmStep(long frameTime) {

    }

    public void reset() {

    }
}
