package com.gladiator.controller;

import com.gladiator.gui.GUI;
import java.io.IOException;

public abstract class Controller {

    protected boolean running = true;
    protected final GameTimer timer;

    protected Controller(int ticksPerSecond) {
        this.timer = new GameTimer(ticksPerSecond);
    }

    public final void run(GUI gui) throws IOException {
        while (running) {
            long startTime = System.currentTimeMillis();

            processInput(gui);

            if (timer.shouldTick()) {
                update();
            }

            draw(gui);
            limitFPS(startTime, 60);
        }

    }

    protected abstract void processInput(GUI gui) throws IOException;
    protected abstract void update();
    protected abstract void draw(GUI gui) throws IOException;

    protected void stop() {
        running = false;
    }

    private void limitFPS(long startTime, int targetFPS) {
        long frameTime = System.currentTimeMillis() - startTime;
        long targetFrameTime = 1000 / targetFPS;

        if (frameTime < targetFrameTime) {
            try {
                Thread.sleep(targetFrameTime - frameTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
