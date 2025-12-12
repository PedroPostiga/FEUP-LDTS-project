package com.gladiator.model;

import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.enemy.enemy_types.FatZombie;
import com.gladiator.model.enemy.enemy_types.LightZombie;
import com.gladiator.model.enemy.enemy_types.Vampire;
import com.gladiator.model.movement.ChaseMovement;
import com.gladiator.model.movement.WanderMovement;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.attack.VampireAttack;
import com.gladiator.model.enemy.EnemyPool;

import java.util.ArrayList;
import java.util.List;

public class WaveManager {
    private int currentWave;
    private boolean waveInProgress;
    private final Arena arena;
    private final EnemyPool enemyPool;

    // Wave configuration
    private static final int BASE_ENEMIES_PER_WAVE = 5;
    private static final double ENEMY_SCALING_FACTOR = 1.2;

    public WaveManager(Arena arena) {
        this.arena = arena;
        this.currentWave = 0;
        this.waveInProgress = false;
        this.enemyPool = arena.getEnemiePool();
    }

    public void startNextWave() {
        currentWave++;
        waveInProgress = true;

        int totalEnemies = calculateTotalEnemies();

        // Spawn enemies through the pool
        for (int i = 0; i < totalEnemies; i++) {
            EnemyPool.EnemyType type = chooseEnemyType(i, totalEnemies);

            // Random or pattern-based positions
            int x = i % arena.getWidth();
            int y = i / arena.getWidth();

            enemyPool.acquireEnemy(type, x, y);
        }
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
}