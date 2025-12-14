package com.gladiator;

import com.gladiator.controller.CreditsController;
import com.gladiator.controller.GameController;
import com.gladiator.controller.MenuController;
import com.gladiator.gui.GUI;
import com.gladiator.gui.LanternaGUI;
import com.gladiator.model.Arena;
import com.gladiator.model.ArenaBuilder;
import com.gladiator.model.menu.MenuModel;
import com.gladiator.view.menu.CreditsViewer;
import com.gladiator.view.menu.MenuViewer;

public class GameRunner {

    public void run() throws Exception {
        GUI gui = null;
        boolean running = true;

        try {
            gui = new LanternaGUI(400, 300);

            while (running) {
                String menuResult = showMainMenu(gui);

                if ("PLAY".equals(menuResult)) {
                    gui.close();
                    gui = new LanternaGUI(400, 300);
                    startGame(gui);
                    gui.close();
                    gui = new LanternaGUI(400, 300);
                } else if ("CREDITS".equals(menuResult)) {
                    showCredits(gui);
                } else if ("EXIT".equals(menuResult)) {
                    running = false;
                }
            }
        } finally {
            if (gui != null) gui.close();
        }
    }

    private String showMainMenu(GUI gui) throws Exception {
        MenuModel menuModel = new MenuModel();
        MenuViewer menuViewer = new MenuViewer(menuModel);
        MenuController menuController = new MenuController(menuViewer);
        menuController.run(gui);
        return menuController.getSelectionResult();
    }

    private void showCredits(GUI gui) throws Exception {
        CreditsViewer creditsViewer = new CreditsViewer();
        CreditsController creditsController = new CreditsController(creditsViewer);
        creditsController.run(gui);
    }

    private void startGame(GUI gui) throws Exception {
        Arena arena = new ArenaBuilder().createArena();
        GameController gameController = new GameController(arena);
        gameController.run(gui);
    }
}