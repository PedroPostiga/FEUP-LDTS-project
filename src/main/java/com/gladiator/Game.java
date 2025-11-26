package com.gladiator;

import com.gladiator.gui.LanternaGUI;
import com.gladiator.model.ArenaBuilder;
import com.gladiator.state.State;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Game {
    private final LanternaGUI gui;
    private State state;

    public Game() throws IOException, URISyntaxException, FontFormatException {
        this.gui = new LanternaGUI(20, 20);
        this.state = new GameState(new ArenaBuilder.createArena());
    }
    public static void main(String[] args) throws IOException, URISyntaxException, FontFormatException {
        new Game().start();
    }
    private void start() throws IOException{
        private void start() throws IOException {
            int FPS = 10;
            int frameTime = 1000 / FPS;

            while (this.state != null) {
                long startTime = System.currentTimeMillis();

                state.step(this, gui, startTime);

                long elapsedTime = System.currentTimeMillis() - startTime;
                long sleepTime = frameTime - elapsedTime;

                try {
                    if (sleepTime > 0) Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                }
            }

            gui.close();
        }
    }
}
