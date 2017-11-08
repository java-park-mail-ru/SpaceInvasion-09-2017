package ru.spaceinvasion.mechanic;

import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.mechanic.base.ClientSnap;

/**
 * Created by egor on 07.11.17.
 */
public interface GameMechanics {

    void addClientSnapshot(Integer userId, ClientSnap clientSnap);

    void addUser(Integer userId);

    void gmStep(long frameTime);

    void reset();
}
