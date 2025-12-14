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

/**
 * Main application orchestrator that manages the game lifecycle and state transitions.
 * Handles navigation between different screens (menu, game, credits).
 */
public class GameRunner {

    /**
     * Application states for state machine pattern
     */
    private enum AppState {
        MENU,
        GAME,
        CREDITS,
        EXIT
    }

    private static final int GUI_WIDTH = 400;
    private static final int GUI_HEIGHT = 300;

    /**
     * Main application entry point that runs the game loop.
     * Manages GUI lifecycle and state transitions.
     */
    public void run() throws Exception {
        GUI gui = null;

        try {
            // Create and reuse a single GUI instance throughout the application
            gui = new LanternaGUI(GUI_WIDTH, GUI_HEIGHT);
            
            AppState currentState = AppState.MENU;

            // Main application loop - state machine
            while (currentState != AppState.EXIT) {
                switch (currentState) {
                    case MENU -> currentState = handleMenuState(gui);
                    case GAME -> currentState = handleGameState(gui);
                    case CREDITS -> currentState = handleCreditsState(gui);
                    case EXIT -> {
                        // Exit state - loop will terminate
                    }
                }
            }
        } finally {
            // Ensure GUI is properly closed on exit
            if (gui != null) {
                gui.close();
            }
        }
    }

    /**
     * Handles the menu state and returns the next state based on user selection.
     */
    private AppState handleMenuState(GUI gui) throws Exception {
        MenuModel menuModel = new MenuModel();
        MenuViewer menuViewer = new MenuViewer(menuModel);
        MenuController menuController = new MenuController(menuViewer);
        menuController.run(gui);
        
        String selection = menuController.getSelectionResult();
        
        return switch (selection) {
            case "PLAY" -> AppState.GAME;
            case "CREDITS" -> AppState.CREDITS;
            case "EXIT" -> AppState.EXIT;
            default -> AppState.MENU; // Stay in menu if no valid selection
        };
    }

    /**
     * Handles the game state and returns to menu when game ends.
     */
    private AppState handleGameState(GUI gui) throws Exception {
        Arena arena = new ArenaBuilder().createArena();
        GameController gameController = new GameController(arena);
        gameController.run(gui);
        
        // After game ends, return to menu
        return AppState.MENU;
    }

    /**
     * Handles the credits state and returns to menu after viewing.
     */
    private AppState handleCreditsState(GUI gui) throws Exception {
        CreditsViewer creditsViewer = new CreditsViewer();
        CreditsController creditsController = new CreditsController(creditsViewer);
        creditsController.run(gui);
        
        // After viewing credits, return to menu
        return AppState.MENU;
    }
}