package ru.spaceinvasion.mechanic.internal;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static ru.spaceinvasion.resources.Constants.SERVER_FRAME_MILLIS;
import java.time.Clock;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by egor on 02.12.17.
 */
@Service
public class MechanicExecutor implements Runnable {
    private Clock clock = Clock.systemDefaultZone();

    private static final Logger LOGGER = LoggerFactory.getLogger(MechanicExecutor.class);

    private Executor tickExecutor = Executors.newSingleThreadExecutor();

    @Autowired
    public MechanicExecutor(@NotNull GameMechanics gameMechanics) {
        this.gameMechanics = gameMechanics;
    }

    private final GameMechanics gameMechanics;

    @PostConstruct
    public void initAfterStartup() {
        tickExecutor.execute(this);
    }

    private void mainCycle() {
        long lastFrameMillis = SERVER_FRAME_MILLIS;
        while (true) {
            try {
                final long before = clock.millis();

                gameMechanics.gmStep(lastFrameMillis);

                final long after = clock.millis();
                try {
                    final long sleepingTime = Math.max(0, SERVER_FRAME_MILLIS - (after - before));
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException ignore) {

                }

                if (Thread.currentThread().isInterrupted()) {
                    gameMechanics.reset();
                    return;
                }
                final long afterSleep = clock.millis();
                lastFrameMillis = afterSleep - before;
            } catch (RuntimeException e) {
                LOGGER.error("error: ",e);
                gameMechanics.reset();
            }
        }
    }

    @Override
    public void run() {
        try {
            mainCycle();
        } finally {

        }
    }
}
