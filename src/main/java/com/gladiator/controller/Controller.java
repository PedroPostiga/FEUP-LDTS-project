package com.gladiator.controller;

import com.gladiator.controller.game.GameTimer;
import com.gladiator.gui.GUI;
import java.io.IOException;

public abstract class Controller {

    public boolean running = true;
    protected final GameTimer timer;

    protected Controller(int ticksPerSecond) {
        this.timer = new GameTimer(ticksPerSecond);
    }

    public final void run(GUI gui) throws IOException {
        long lastFrameTime = System.nanoTime();
        final long targetFrameTime = 1_000_000_000L / 60; // 60 FPS in nanoseconds
        
        while (running) {
            long currentTime = System.nanoTime();

            processInput(gui);

            if (timer.shouldTick()) {
                update();
            }

            draw(gui);
            
            // Better FPS limiting using nanoTime for precision
            long frameTime = System.nanoTime() - currentTime;
            long sleepTime = (targetFrameTime - frameTime) / 1_000_000; // Convert to milliseconds
            
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            
            lastFrameTime = currentTime;
        }

    }

    protected abstract void processInput(GUI gui) throws IOException;
    protected abstract void update();
    protected abstract void draw(GUI gui) throws IOException;

    protected void stop() {
        running = false;
    }
}
