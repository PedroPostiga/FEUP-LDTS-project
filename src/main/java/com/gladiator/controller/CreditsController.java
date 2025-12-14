package com.gladiator.controller;

import com.gladiator.gui.GUI;
import com.gladiator.view.menu.CreditsViewer;

import java.io.IOException;

public class CreditsController extends Controller {

    private final CreditsViewer viewer;

    public CreditsController(CreditsViewer viewer) {
        super(10);
        this.viewer = viewer;
    }

    @Override
    protected void processInput(GUI gui) throws IOException {
        GUI.ACTION action = gui.getNextAction();

        // Any key press exits credits
        if (action != GUI.ACTION.NONE) {
            stop();
        }
    }

    @Override
    protected void update() {
        // Credits doesn't need updates
    }

    @Override
    protected void draw(GUI gui) throws IOException {
        viewer.draw(gui);
    }

}