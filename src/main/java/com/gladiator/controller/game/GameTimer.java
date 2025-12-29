package com.gladiator.controller.game;

public class GameTimer {
    private final long tickInterval; // Time between ticks in nanoseconds
    private long lastTickTime;

    public GameTimer(int ticksPerSecond) {
        this.tickInterval = 1_000_000_000L / ticksPerSecond; // Convert to nanoseconds for better precision
        this.lastTickTime = 0; // Initialize to 0 so first tick always happens
    }

    public boolean shouldTick() {
        long currentTime = System.nanoTime();
        if (currentTime - lastTickTime >= tickInterval) {
            lastTickTime = currentTime;
            return true;
        }
        return false;
    }

    public void reset() {
        lastTickTime = 0; // Reset to 0 so next tick always happens
    }
}
