package ru.spaceinvasion.mechanic.internal;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.spaceinvasion.mechanic.internal.tasks.PingTask;
import ru.spaceinvasion.services.TimeService;
import ru.spaceinvasion.services.WebSocketSessionService;

import java.util.*;

import static ru.spaceinvasion.utils.Constants.GameMechanicConstants.PING_MILLIS;

/**
 * Created by egor on 15.11.17.
 */

@Service
public class GameTaskScheduler {

    private final @NotNull TimeService timeService;

    private final TreeMap<Long, ScheduledTask> scheduledTasks = new TreeMap<>();

    private final WebSocketSessionService webSocketSessionService;

    public GameTaskScheduler(@NotNull TimeService timeService, @NotNull WebSocketSessionService webSocketSessionService) {
        this.timeService = timeService;
        this.webSocketSessionService = webSocketSessionService;
        //schedule(PING_MILLIS,new PingTask(webSocketSessionService,this));
    }

    public void schedule(long timerMillis, ScheduledTask task) {
        final long scheduleTime = timeService.time() + timerMillis;
        final ScheduledTask existedTask = scheduledTasks.get(scheduleTime);
        if (existedTask != null) {
            final ScheduledTaskList container = new ScheduledTaskList(existedTask);
            container.addTask(task);
            scheduledTasks.put(timerMillis, container);
            return;
        }
        scheduledTasks.put(scheduleTime, task);
    }

    public void tick() {
        final long now = timeService.time();

        Map.Entry<Long, ScheduledTask> entry;
        while ((entry = scheduledTasks.headMap(now, true).pollFirstEntry()) != null) {
            performOperation(entry.getValue());
        }
    }

    public void reset() {
        scheduledTasks.clear();
    }

    private static void performOperation(@NotNull ScheduledTask task) {
        try {
            task.operate();
        } catch (RuntimeException ex) {
            task.onError();
        }
    }

    private interface ScheduledTask {
        void operate();
        default void onError() {}
    }

    public abstract static class GameSessionTask implements ScheduledTask{

    }

    private static final class ScheduledTaskList implements ScheduledTask {
        final List<ScheduledTask> tasks;

        private ScheduledTaskList(@NotNull ScheduledTask... tasks) {
            this.tasks = new ArrayList<>(Arrays.asList(tasks));
        }

        private ScheduledTaskList(@NotNull ScheduledTaskList task) {
            this.tasks = task.tasks;
        }

        void addTask(@NotNull ScheduledTask task) {
            tasks.add(task);
        }

        @Override
        public void operate() {
            tasks.forEach(GameTaskScheduler::performOperation);
        }
    }

}
