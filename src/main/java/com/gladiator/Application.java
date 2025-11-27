package com.gladiator;

import com.gladiator.controller.Controller;
import com.gladiator.gui.LanternaGUI;
import com.gladiator.model.Arena;
import com.gladiator.model.ArenaBuilder;
import com.gladiator.view.game.ArenaViewer;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Application {
    private final LanternaGUI gui;
    private final Arena arena;

    public Application() throws IOException, URISyntaxException, FontFormatException {
        this.gui = new LanternaGUI(20, 20);
        this.arena = new ArenaBuilder().createArena();
    }
    public static void main(String[] args) throws IOException, URISyntaxException, FontFormatException {
        new Application().start();
    }

    private void start() throws IOException {
        Controller controller = new Controller(new ArenaViewer(arena));
        controller.step(gui);
    }
}
