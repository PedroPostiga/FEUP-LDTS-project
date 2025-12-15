package com.gladiator.controller;

import com.gladiator.gui.GUI;
import com.gladiator.model.menu.GameOverModel;
import com.gladiator.view.menu.GameOverViewer;

import java.io.IOException;

public class GameOverController extends Controller {

    private final GameOverViewer viewer;
    private final GameOverModel model;
    private String selectionResult = null;

    public GameOverController(GameOverViewer viewer) {
        super(10); // menu updates faster
        this.viewer = viewer;
        this.model = viewer.getModel();
    }

    @Override
    protected void processInput(GUI gui) throws IOException {
        GUI.ACTION action = gui.getNextAction();

        switch (action) {
            case UP -> model.previousSelected();
            case DOWN -> model.nextSelected();
            case SELECT -> handleSelection(model.getSelected());
            case QUIT -> {
                selectionResult = "QUIT";
                stop();
            }
            default -> {}
        }
    }

    private void handleSelection(GameOverModel.Option selected) {
        switch (selected) {
            case MENU -> selectionResult = "MENU";
            case QUIT -> selectionResult = "QUIT";
        }
        stop(); // Stop the controller when a selection is made
    }

    @Override
    protected void update() {
        // No updates needed for game over screen
    }

    @Override
    protected void draw(GUI gui) throws IOException {
        viewer.draw(gui);
    }

    public String getSelectionResult() {
        return selectionResult;
    }
}

