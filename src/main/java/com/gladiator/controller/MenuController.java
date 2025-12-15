package com.gladiator.controller;

import com.gladiator.gui.GUI;
import com.gladiator.model.menu.MenuModel;
import com.gladiator.view.menu.MenuViewer;

import java.io.IOException;

public class MenuController extends Controller {

    private final MenuViewer viewer;
    private final MenuModel model;
    private String selectionResult = null;

    public MenuController(MenuViewer viewer) {
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
            case QUIT -> stop();
            default -> {}
        }
    }

    private void handleSelection(MenuModel.Option selected) {
        switch (selected) {
            case PLAY -> selectionResult = "PLAY";
            case CREDITS -> selectionResult = "CREDITS";
            case EXIT -> selectionResult = "EXIT";
        }
        stop(); // Stop the controller when a selection is made
    }

    @Override
    protected void update() {
    }

    @Override
    protected void draw(GUI gui) throws IOException {
        viewer.draw(gui);
    }

    public String getSelectionResult() {
        return selectionResult;
    }
}
