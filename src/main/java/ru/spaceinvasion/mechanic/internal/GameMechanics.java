package ru.spaceinvasion.mechanic.internal;

import ru.spaceinvasion.mechanic.snaps.ClientSnap;

/**
 * Created by egor on 07.11.17.
 */
public interface GameMechanics {

    void addClientSnapshot(Integer userId, ClientSnap clientSnap);

    void addUser(Integer userId);

    void gmStep(long frameTime);

    void reset();
}
