package com.gladiator.controller;

public class GameTimer {
    private final long tickInterval; // Time between ticks in nanoseconds
    private long lastTickTime;

    public GameTimer(int ticksPerSecond) {
        this.tickInterval = 1_000_000_000L / ticksPerSecond; // Convert to nanoseconds for better precision
        this.lastTickTime = System.nanoTime();
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
        lastTickTime = System.nanoTime();
    }
}
