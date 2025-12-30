package com.gladiator.model;

import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.enemy.EnemyPool;
import com.gladiator.model.gladiator.Gladiator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WaveManagerTest {
    private Arena arena;
    private WaveManager waveManager;
    private EnemyPool enemyPool;
    private Gladiator gladiator;

    @BeforeEach
    void setUp() {
        arena = mock(Arena.class);
        enemyPool = mock(EnemyPool.class);
        gladiator = mock(Gladiator.class);

        when(arena.getEnemiePool()).thenReturn(enemyPool);
        when(arena.getGladiator()).thenReturn(gladiator);
        when(arena.getWidth()).thenReturn(400);
        when(arena.getHeight()).thenReturn(300);
        when(gladiator.getPosition()).thenReturn(new Position(200, 200));
        when(arena.isEmpty(any(Rectangle.class))).thenReturn(true);

        waveManager = new WaveManager(arena);
    }

    @Test
    void testInitialState() {
        assertEquals(0, waveManager.getCurrentWave());
        assertFalse(waveManager.isWaveInProgress());
        assertFalse(waveManager.isWaveComplete());
    }

    @Test
    void testStartNextWave() {
        when(enemyPool.acquireEnemy(any(), anyInt(), anyInt())).thenReturn(mock(Enemy.class));
        
        waveManager.startNextWave();
        
        assertEquals(1, waveManager.getCurrentWave());
        assertTrue(waveManager.isWaveInProgress());
        verify(enemyPool, atLeastOnce()).acquireEnemy(any(), anyInt(), anyInt());
    }

    @Test
    void testStartMultipleWaves() {
        when(enemyPool.acquireEnemy(any(), anyInt(), anyInt())).thenReturn(mock(Enemy.class));
        
        waveManager.startNextWave();
        assertEquals(1, waveManager.getCurrentWave());
        
        when(enemyPool.getAllActiveEnemies()).thenReturn(new ArrayList<>());
        waveManager.checkWaveCompletion();
        
        waveManager.startNextWave();
        assertEquals(2, waveManager.getCurrentWave());
    }

    @Test
    void testCheckWaveCompletion() {
        waveManager.startNextWave();
        assertTrue(waveManager.isWaveInProgress());
        
        when(enemyPool.getAllActiveEnemies()).thenReturn(new ArrayList<>());
        
        boolean gameWon = waveManager.checkWaveCompletion();
        
        assertFalse(waveManager.isWaveInProgress());
        assertTrue(waveManager.isWaveComplete());
        assertFalse(gameWon);
    }

    @Test
    void testCheckWaveCompletionGameWon() {
        // Complete WINNING_WAVE - 1 waves (waves 1 through WINNING_WAVE - 1)
        for (int i = 0; i < WaveManager.getWinningWave() - 1; i++) {
            when(enemyPool.acquireEnemy(any(), anyInt(), anyInt())).thenReturn(mock(Enemy.class));
            waveManager.startNextWave();
            when(enemyPool.getAllActiveEnemies()).thenReturn(new ArrayList<>());
            waveManager.checkWaveCompletion();
        }
        
        // Start the winning wave (wave WINNING_WAVE)
        when(enemyPool.acquireEnemy(any(), anyInt(), anyInt())).thenReturn(mock(Enemy.class));
        waveManager.startNextWave();
        assertEquals(WaveManager.getWinningWave(), waveManager.getCurrentWave());
        
        // Complete the winning wave - this should trigger game win
        when(enemyPool.getAllActiveEnemies()).thenReturn(new ArrayList<>());
        boolean gameWon = waveManager.checkWaveCompletion();
        
        assertTrue(gameWon);
    }

    @Test
    void testEnemyDied() {
        Enemy enemy = mock(Enemy.class);
        List<Enemy> activeEnemies = new ArrayList<>();
        activeEnemies.add(enemy);
        
        when(enemyPool.getAllActiveEnemies()).thenReturn(activeEnemies);
        waveManager.startNextWave();
        
        activeEnemies.remove(enemy);
        when(enemyPool.getAllActiveEnemies()).thenReturn(new ArrayList<>());
        
        waveManager.enemyDied(enemy);
        
        verify(enemyPool).releaseEnemy(enemy);
        assertFalse(waveManager.isWaveInProgress());
    }

    @Test
    void testGetTotalWaveEnemies() {
        waveManager.startNextWave();
        
        int totalEnemies = waveManager.getTotalWaveEnemies();
        
        assertTrue(totalEnemies > 0);
    }

    @Test
    void testWaveScaling() {
        when(enemyPool.acquireEnemy(any(), anyInt(), anyInt())).thenReturn(mock(Enemy.class));
        when(enemyPool.getAllActiveEnemies()).thenReturn(new ArrayList<>());
        
        waveManager.startNextWave();
        int wave1Enemies = waveManager.getTotalWaveEnemies();
        
        waveManager.checkWaveCompletion();
        waveManager.startNextWave();
        int wave2Enemies = waveManager.getTotalWaveEnemies();
        
        assertTrue(wave2Enemies >= wave1Enemies);
    }

    @Test
    void testCheckWaveCompletionWhenNotInProgress() {
        when(enemyPool.getAllActiveEnemies()).thenReturn(new ArrayList<>());
        
        boolean gameWon = waveManager.checkWaveCompletion();
        
        assertFalse(gameWon);
        assertFalse(waveManager.isWaveInProgress());
    }

    @Test
    void testCheckWaveCompletionWithEnemiesStillAlive() {
        when(enemyPool.acquireEnemy(any(), anyInt(), anyInt())).thenReturn(mock(Enemy.class));
        waveManager.startNextWave();
        
        List<Enemy> activeEnemies = new ArrayList<>();
        activeEnemies.add(mock(Enemy.class));
        when(enemyPool.getAllActiveEnemies()).thenReturn(activeEnemies);
        
        boolean gameWon = waveManager.checkWaveCompletion();
        
        assertFalse(gameWon);
        assertTrue(waveManager.isWaveInProgress());
    }

    @Test
    void testGetWinningWave() {
        int winningWave = WaveManager.getWinningWave();
        assertTrue(winningWave > 0);
    }

    @Test
    void testEnemyDiedWithEnemiesStillAlive() {
        Enemy enemy1 = mock(Enemy.class);
        Enemy enemy2 = mock(Enemy.class);
        List<Enemy> activeEnemies = new ArrayList<>();
        activeEnemies.add(enemy1);
        activeEnemies.add(enemy2);
        
        when(enemyPool.getAllActiveEnemies()).thenReturn(activeEnemies);
        waveManager.startNextWave();
        
        activeEnemies.remove(enemy1);
        when(enemyPool.getAllActiveEnemies()).thenReturn(activeEnemies);
        
        waveManager.enemyDied(enemy1);
        
        verify(enemyPool).releaseEnemy(enemy1);
        assertTrue(waveManager.isWaveInProgress()); // Still enemies alive
    }

    @Test
    void testIsWaveCompleteWhenNoWavesStarted() {
        assertFalse(waveManager.isWaveComplete());
    }

    @Test
    void testIsWaveCompleteAfterWaveCompletion() {
        when(enemyPool.acquireEnemy(any(), anyInt(), anyInt())).thenReturn(mock(Enemy.class));
        waveManager.startNextWave();
        
        when(enemyPool.getAllActiveEnemies()).thenReturn(new ArrayList<>());
        waveManager.checkWaveCompletion();
        
        assertTrue(waveManager.isWaveComplete());
    }
}

