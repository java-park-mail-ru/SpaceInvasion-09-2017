package ru.spaceinvasion.services;

import org.springframework.stereotype.Service;

/**
 * Created by egor on 15.11.17.
 */

@Service
public class TimeService {
    private long millis = 0;

    public void reset() {
        millis = 0;
    }

    public void tick(long delta) {
        millis += delta;
    }

    public long time() {
        return millis;
    }
}
