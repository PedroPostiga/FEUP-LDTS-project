package com.gladiator.controller.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.Arena;
import com.gladiator.model.WaveManager;
import com.gladiator.model.attack.projectile.SingleArrowPool;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.EnemyPool;
import com.gladiator.model.gladiator.Gladiator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameControllerTest {
    private Arena arena;
    private GameController gameController;
    private GUI gui;
    private Gladiator gladiator;
    private EnemyPool enemyPool;
    private SingleArrowPool arrowPool;
    private WaveManager waveManager;

    @BeforeEach
    void setUp() {
        arena = mock(Arena.class);
        gui = mock(GUI.class);
        gladiator = mock(Gladiator.class);
        enemyPool = mock(EnemyPool.class);
        arrowPool = mock(SingleArrowPool.class);
        waveManager = mock(WaveManager.class);

        when(arena.getGladiator()).thenReturn(gladiator);
        when(arena.getEnemiePool()).thenReturn(enemyPool);
        when(arena.getArrowPool()).thenReturn(arrowPool);
        when(gladiator.getSpeed()).thenReturn(5);
        when(gladiator.getPosition()).thenReturn(new Position(100, 100));
        when(gladiator.getHitbox()).thenReturn(new Rectangle(100, 100, 20, 20));
        when(gladiator.isAlive()).thenReturn(true);
        when(enemyPool.getAllActiveEnemies()).thenReturn(new ArrayList<>());
        when(arrowPool.getActiveArrows()).thenReturn(new ArrayList<>());

        gameController = new GameController(arena);
    }

    @Test
    void testInitialGameState() {
        assertEquals(GameController.GameState.PLAYING, gameController.getGameState());
        assertFalse(gameController.isGameOver());
    }

    @Test
    void testMoveGladiatorLeft() {
        when(arena.isEmpty(any(Rectangle.class), isNull(), eq(gladiator))).thenReturn(true);
        
        gameController.moveGladiatorLeft();
        
        verify(gladiator).setPosition(argThat(pos -> pos.getX() == 95 && pos.getY() == 100));
        verify(gladiator).setDirection(Gladiator.Direction.LEFT);
    }

    @Test
    void testMoveGladiatorRight() {
        when(arena.isEmpty(any(Rectangle.class), isNull(), eq(gladiator))).thenReturn(true);
        
        gameController.moveGladiatorRight();
        
        verify(gladiator).setPosition(argThat(pos -> pos.getX() == 105 && pos.getY() == 100));
        verify(gladiator).setDirection(Gladiator.Direction.RIGHT);
    }

    @Test
    void testMoveGladiatorUp() {
        when(arena.isEmpty(any(Rectangle.class), isNull(), eq(gladiator))).thenReturn(true);
        
        gameController.moveGladiatorUp();
        
        verify(gladiator).setPosition(argThat(pos -> pos.getX() == 100 && pos.getY() == 95));
        verify(gladiator).setDirection(Gladiator.Direction.UP);
    }

    @Test
    void testMoveGladiatorDown() {
        when(arena.isEmpty(any(Rectangle.class), isNull(), eq(gladiator))).thenReturn(true);
        
        gameController.moveGladiatorDown();
        
        verify(gladiator).setPosition(argThat(pos -> pos.getX() == 100 && pos.getY() == 105));
        verify(gladiator).setDirection(Gladiator.Direction.DOWN);
    }

    @Test
    void testMoveGladiatorBlockedByObstacle() {
        when(arena.isEmpty(any(Rectangle.class), isNull(), eq(gladiator))).thenReturn(false);
        
        gameController.moveGladiatorLeft();
        
        verify(gladiator, never()).setPosition(any());
        verify(gladiator, never()).setDirection(any());
    }

    @Test
    void testProcessInputQuit() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.QUIT);
        
        gameController.processInput(gui);
        
        assertFalse(gameController.running);
    }

    @Test
    void testProcessInputMovement() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.UP);
        when(arena.isEmpty(any(Rectangle.class), isNull(), eq(gladiator))).thenReturn(true);
        
        gameController.processInput(gui);
        
        verify(gladiator).setPosition(any());
        verify(gladiator).setDirection(Gladiator.Direction.UP);
    }

    @Test
    void testUpdateGameLostWhenGladiatorDies() {
        when(gladiator.isAlive()).thenReturn(false);
        when(enemyPool.getAllActiveEnemies()).thenReturn(new ArrayList<>());
        
        gameController.update();
        
        assertEquals(GameController.GameState.LOST, gameController.getGameState());
        assertTrue(gameController.isGameOver());
        assertFalse(gameController.running);
    }

    @Test
    void testUpdateDoesNotUpdateWhenGameOver() {
        when(gladiator.isAlive()).thenReturn(false);
        when(enemyPool.getAllActiveEnemies()).thenReturn(new ArrayList<>());
        
        gameController.update();
        assertEquals(GameController.GameState.LOST, gameController.getGameState());
        
        reset(gladiator, enemyPool);
        gameController.update();
        
        verify(gladiator, never()).isAlive();
    }
}

