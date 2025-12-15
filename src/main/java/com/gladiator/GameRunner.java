package com.gladiator;

import com.gladiator.controller.CreditsController;
import com.gladiator.controller.GameController;
import com.gladiator.controller.GameOverController;
import com.gladiator.controller.MenuController;
import com.gladiator.gui.GUI;
import com.gladiator.gui.LanternaGUI;
import com.gladiator.model.Arena;
import com.gladiator.model.ArenaBuilder;
import com.gladiator.model.menu.GameOverModel;
import com.gladiator.model.menu.MenuModel;
import com.gladiator.view.menu.CreditsViewer;
import com.gladiator.view.menu.GameOverViewer;
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
        GAME_OVER,
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
                    case GAME_OVER -> currentState = handleGameOverState(gui);
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
     * Stores the win/loss state for the game over screen
     */
    private boolean lastGameWon = false;
    
    /**
     * Handles the game state and transitions to game over screen when game ends.
     */
    private AppState handleGameState(GUI gui) throws Exception {
        Arena arena = new ArenaBuilder().createArena();
        GameController gameController = new GameController(arena);
        gameController.run(gui);
        
        // After game ends, check if game actually ended (won or lost) or if user quit
        GameController.GameState gameState = gameController.getGameState();
        
        // Only show game over screen if the game actually ended (won or lost)
        // If user quit, gameState will still be PLAYING, so return to menu
        if (gameState == GameController.GameState.WON || gameState == GameController.GameState.LOST) {
            lastGameWon = (gameState == GameController.GameState.WON);
            return AppState.GAME_OVER;
        }
        
        // User quit the game, return to menu
        return AppState.MENU;
    }
    
    /**
     * Handles the game over state and returns the next state based on user selection.
     */
    private AppState handleGameOverState(GUI gui) throws Exception {
        GameOverModel gameOverModel = new GameOverModel(lastGameWon);
        GameOverViewer gameOverViewer = new GameOverViewer(gameOverModel);
        GameOverController gameOverController = new GameOverController(gameOverViewer);
        gameOverController.run(gui);
        
        String selection = gameOverController.getSelectionResult();
        
        return switch (selection) {
            case "MENU" -> AppState.MENU;
            case "QUIT" -> AppState.EXIT;
            default -> AppState.MENU; // Default to menu if no valid selection
        };
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