package com.gladiator.model;

import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.enemy.enemy_types.FatZombie;
import com.gladiator.model.enemy.enemy_types.LightZombie;
import com.gladiator.model.enemy.enemy_types.Vampire;
import com.gladiator.model.movement.ChaseMovement;
import com.gladiator.model.movement.WanderMovement;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.attack.VampireAttack;
import com.gladiator.model.enemy.EnemyPool;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WaveManager {
    private int currentWave;
    private boolean waveInProgress;
    private final Arena arena;
    private final EnemyPool enemyPool;
    private final Random random;

    // Wave configuration
    private static final int BASE_ENEMIES_PER_WAVE = 5;
    private static final double ENEMY_SCALING_FACTOR = 1.2;
    private static final int MIN_SPAWN_DISTANCE_FROM_GLADIATOR = 100; // Minimum distance from gladiator to spawn
    private static final int WINNING_WAVE = 1; // Win the game after completing this many waves

    public WaveManager(Arena arena) {
        this.arena = arena;
        this.currentWave = 0;
        this.waveInProgress = false;
        this.enemyPool = arena.getEnemiePool();
        this.random = new Random();
    }

    public void startNextWave() {
        currentWave++;
        waveInProgress = true;

        int totalEnemies = calculateTotalEnemies();

        // Spawn enemies through the pool
        for (int i = 0; i < totalEnemies; i++) {
            EnemyPool.EnemyType type = chooseEnemyType(i, totalEnemies);

            int w, h;
            if (type == EnemyPool.EnemyType.LIGHT_ZOMBIE){
                w = 16; h = 16;
            }
            else if (type == EnemyPool.EnemyType.FAT_ZOMBIE){
                w = 25; h = 25;
            }
            else if (type == EnemyPool.EnemyType.VAMPIRE){
                w = 16; h = 16;
            }
            else continue;

            // Find a random spawn position outside the minimum range from gladiator
            Position spawnPos = findRandomSpawnPosition(w, h);
            if (spawnPos != null) {
                enemyPool.acquireEnemy(type, spawnPos.getX(), spawnPos.getY());
            }
        }
    }

    /**
     * Finds a random spawn position outside the minimum distance from the gladiator.
     * Tries up to 50 times to find a valid position.
     * @param enemyWidth Width of the enemy to spawn
     * @param enemyHeight Height of the enemy to spawn
     * @return A valid spawn position, or null if no valid position found
     */
    private Position findRandomSpawnPosition(int enemyWidth, int enemyHeight) {
        if (arena.getGladiator() == null) {
            // Fallback if gladiator is not available
            return new Position(20, 20);
        }

        int gladiatorX = arena.getGladiator().getPosition().getX();
        int gladiatorY = arena.getGladiator().getPosition().getY();
        int arenaWidth = arena.getWidth();
        int arenaHeight = arena.getHeight();

        // Try to find a valid spawn position
        for (int attempts = 0; attempts < 50; attempts++) {
            int x = random.nextInt(arenaWidth - enemyWidth);
            int y = random.nextInt(arenaHeight - enemyHeight);

            // Calculate distance from gladiator
            double distance = Math.sqrt(Math.pow(x - gladiatorX, 2) + Math.pow(y - gladiatorY, 2));

            // Check if position is far enough from gladiator and is empty
            if (distance >= MIN_SPAWN_DISTANCE_FROM_GLADIATOR) {
                Rectangle spawnRect = new Rectangle(x, y, enemyWidth, enemyHeight);
                if (arena.isEmpty(spawnRect)) {
                    return new Position(x, y);
                }
            }
        }

        // If we couldn't find a position outside the range, try anywhere in the arena
        for (int attempts = 0; attempts < 50; attempts++) {
            int x = random.nextInt(arenaWidth - enemyWidth);
            int y = random.nextInt(arenaHeight - enemyHeight);
            Rectangle spawnRect = new Rectangle(x, y, enemyWidth, enemyHeight);
            if (arena.isEmpty(spawnRect)) {
                return new Position(x, y);
            }
        }

        // Last resort: return a default position
        return new Position(20, 20);
    }

    private int calculateTotalEnemies() {
        return (int) (BASE_ENEMIES_PER_WAVE * Math.pow(ENEMY_SCALING_FACTOR, currentWave - 1));
    }

    private EnemyPool.EnemyType chooseEnemyType(int index, int total) {
        // Simple distribution: 30% Vampire, 30% FatZombie, 40% LightZombie
        double ratio = (double) index / total;
        if (ratio < 0.3 && currentWave >= 3) return EnemyPool.EnemyType.VAMPIRE;
        if (ratio < 0.6) return EnemyPool.EnemyType.FAT_ZOMBIE;
        return EnemyPool.EnemyType.LIGHT_ZOMBIE;
    }

    public void enemyDied(Enemy enemy) {
        enemyPool.releaseEnemy(enemy);

        if (enemyPool.getAllActiveEnemies().isEmpty()) {
            waveInProgress = false;
        }
    }

    /**
     * Checks if all enemies are dead and updates wave status accordingly.
     * Should be called after enemies are updated to detect when a wave is complete.
     * @return true if the game should be won (completed all waves)
     */
    public boolean checkWaveCompletion() {
        if (waveInProgress && enemyPool.getAllActiveEnemies().isEmpty()) {
            waveInProgress = false;
            
            // Check if player won (completed all required waves)
            if (currentWave >= WINNING_WAVE) {
                return true; // Game won
            }
        }
        return false; // Game continues
    }

    public boolean isWaveInProgress() {
        return waveInProgress;
    }

    public boolean isWaveComplete() {
        return !waveInProgress && currentWave > 0;
    }

    public int getCurrentWave() {
        return currentWave;
    }


    public int getTotalWaveEnemies() {
        return calculateTotalEnemies();
    }

    public static int getWinningWave() {
        return WINNING_WAVE;
    }
}