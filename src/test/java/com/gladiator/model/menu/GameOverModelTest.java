package com.gladiator.model.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameOverModelTest {
    private GameOverModel gameOverModel;

    @BeforeEach
    void setUp() {
        gameOverModel = new GameOverModel(false);
    }

    @Test
    void testInitialStateWon() {
        GameOverModel wonModel = new GameOverModel(true);
        assertTrue(wonModel.isWon());
        assertEquals(GameOverModel.Option.MENU, wonModel.getSelected());
    }

    @Test
    void testInitialStateLost() {
        assertFalse(gameOverModel.isWon());
        assertEquals(GameOverModel.Option.MENU, gameOverModel.getSelected());
    }

    @Test
    void testSetSelected() {
        gameOverModel.setSelected(GameOverModel.Option.QUIT);
        assertEquals(GameOverModel.Option.QUIT, gameOverModel.getSelected());
        
        gameOverModel.setSelected(GameOverModel.Option.MENU);
        assertEquals(GameOverModel.Option.MENU, gameOverModel.getSelected());
    }

    @Test
    void testNextSelected() {
        assertEquals(GameOverModel.Option.MENU, gameOverModel.getSelected());
        
        gameOverModel.nextSelected();
        assertEquals(GameOverModel.Option.QUIT, gameOverModel.getSelected());
        
        gameOverModel.nextSelected();
        assertEquals(GameOverModel.Option.MENU, gameOverModel.getSelected());
    }

    @Test
    void testPreviousSelected() {
        assertEquals(GameOverModel.Option.MENU, gameOverModel.getSelected());
        
        gameOverModel.previousSelected();
        assertEquals(GameOverModel.Option.QUIT, gameOverModel.getSelected());
        
        gameOverModel.previousSelected();
        assertEquals(GameOverModel.Option.MENU, gameOverModel.getSelected());
    }

    @Test
    void testCyclingThroughOptions() {
        gameOverModel.setSelected(GameOverModel.Option.MENU);
        gameOverModel.nextSelected();
        assertEquals(GameOverModel.Option.QUIT, gameOverModel.getSelected());
        
        gameOverModel.nextSelected();
        assertEquals(GameOverModel.Option.MENU, gameOverModel.getSelected());
    }
}

