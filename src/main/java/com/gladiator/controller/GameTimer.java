package com.gladiator.controller;

public class GameTimer {
    private final long tickInterval; // Time between ticks in milliseconds
    private long lastTickTime;

    public GameTimer(int ticksPerSecond) {
        this.tickInterval = 1000 / ticksPerSecond; // Convert to ms
        this.lastTickTime = System.currentTimeMillis();
    }

    public boolean shouldTick() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTickTime >= tickInterval) {
            lastTickTime = currentTime;
            return true;
        }
        return false;
    }

    public void reset() {
        lastTickTime = System.currentTimeMillis();
    }
}
}
