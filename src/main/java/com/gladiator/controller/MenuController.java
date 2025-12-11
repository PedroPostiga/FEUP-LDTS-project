package com.gladiator.controller;

import com.gladiator.gui.GUI;
import com.gladiator.view.menu.MenuViewer;

import java.io.IOException;

public class MenuController extends Controller {

    private final MenuViewer viewer;

    public MenuController(MenuViewer viewer) {
        super(10); // menu updates faster
        this.viewer = viewer;
    }

    @Override
    protected void processInput(GUI gui) throws IOException {
        GUI.ACTION action = gui.getNextAction();

        switch (action) {
            case UP -> viewer.moveUp();
            case DOWN -> viewer.moveDown();
            case SELECT -> viewer.select();
            case QUIT -> stop();
            default -> {}
        }
    }

    @Override
    protected void update() {
        // menus don’t need ticking logic
    }

    @Override
    protected void draw(GUI gui) throws IOException {
        viewer.draw(gui);
    }
}
