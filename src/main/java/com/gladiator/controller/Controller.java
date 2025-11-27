package com.gladiator.controller;

import com.gladiator.Application;
import com.gladiator.gui.GUI;
import com.gladiator.view.game.ArenaViewer;

import java.io.IOException;

public class Controller {
    private ArenaViewer arenaViewer;

    public Controller(ArenaViewer arenaViewer){
        this.arenaViewer = arenaViewer;
    }
    public void step(GUI gui) throws IOException {
        arenaViewer.draw(gui);
    }
}
