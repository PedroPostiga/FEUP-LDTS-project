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
    private List<Enemy> currentWaveEnemies;
    private final Arena arena;
    private final EnemyPool enemyPool;

    // Wave configuration
    private static final int BASE_ENEMIES_PER_WAVE = 5;
    private static final double ENEMY_SCALING_FACTOR = 1.2;

    public WaveManager(Arena arena) {
        this.arena = arena;
        this.currentWave = 0;
        this.waveInProgress = false;
        this.currentWaveEnemies = new ArrayList<>();
        this.enemyPool = new EnemyPool(arena.getGladiator()); // INITIALIZE
        this.enemyPool.preWarmPools(); // PRE-WARM
    }

    public void startNextWave() {
        currentWave++;
        waveInProgress = true;
        currentWaveEnemies.clear();

        List<Enemy> waveEnemies = generateWaveEnemies();
        arena.setEnemies(waveEnemies);
        currentWaveEnemies.addAll(waveEnemies);
    }

    private List<Enemy> generateWaveEnemies() {
        List<Enemy> enemies = new ArrayList<>();
        int totalEnemies = calculateTotalEnemies();

        // Enemy type distribution based on wave number
        int vampires = calculateVampireCount(totalEnemies);
        int fatZombies = calculateFatZombieCount(totalEnemies);
        int lightZombies = totalEnemies - vampires - fatZombies;

        // Create enemies
        createVampires(enemies, vampires);
        createFatZombies(enemies, fatZombies);
        createLightZombies(enemies, lightZombies);

        return enemies;
    }

    private int calculateTotalEnemies() {
        return (int) (BASE_ENEMIES_PER_WAVE * Math.pow(ENEMY_SCALING_FACTOR, currentWave - 1));
    }

    private int calculateVampireCount(int totalEnemies) {
        // Vampires start appearing after wave 3
        if (currentWave < 3) return 0;
        return (int) (totalEnemies * 0.3); // 30% vampires
    }

    private int calculateFatZombieCount(int totalEnemies) {
        // Fat zombies from the beginning, increasing presence
        double percentage = 0.2 + (currentWave * 0.05); // 20% to 45%
        return Math.min((int) (totalEnemies * percentage), totalEnemies / 2);
    }

    private void createVampires(List<Enemy> enemies, int count) {
        for (int i = 0; i < count; i++) {
            int x = (i * 3) % arena.getWidth();
            int y = 1;

            Enemy vampire = enemyPool.acquireEnemy(
                    EnemyPool.EnemyType.VAMPIRE, x, y
            );

            if (vampire != null) {
                enemies.add(vampire);
            }
        }
    }

    private void createFatZombies(List<Enemy> enemies, int count) {
        for (int i = 0; i < count; i++) {
            int x = (i * 2) % arena.getWidth();
            int y = 2;

            Enemy fatZombie = enemyPool.acquireEnemy(
                    EnemyPool.EnemyType.FAT_ZOMBIE, x, y
            );

            if (fatZombie != null) {
                enemies.add(fatZombie);
            }
        }
    }

    private void createLightZombies(List<Enemy> enemies, int count) {
        for (int i = 0; i < count; i++) {
            int x = i % arena.getWidth();
            int y = 3;

            Enemy lightZombie = enemyPool.acquireEnemy(
                    EnemyPool.EnemyType.LIGHT_ZOMBIE, x, y
            );

            if (lightZombie != null) {
                enemies.add(lightZombie);
            }
        }
    }

    public void enemyDied(Enemy enemy) {
        enemyPool.releaseEnemy(enemy);
        currentWaveEnemies.remove(enemy);

        if (waveInProgress && currentWaveEnemies.isEmpty()) {
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

    public int getRemainingEnemies() {
        return currentWaveEnemies.size();
    }

    public int getTotalWaveEnemies() {
        return calculateTotalEnemies();
    }

    public double getWaveProgress() {
        if (currentWave == 0) return 0.0;
        int total = getTotalWaveEnemies();
        int remaining = getRemainingEnemies();
        return ((double) (total - remaining) / total) * 100.0;
    }

    public void reset() {
        currentWave = 0;
        waveInProgress = false;

        // Return all enemies to pool
        for (Enemy enemy : currentWaveEnemies) {
            enemyPool.releaseEnemy(enemy);
        }
        currentWaveEnemies.clear();
        enemyPool.clear(); // clear pool completely
    }

    public List<Enemy> getCurrentWaveEnemies() {
        return new ArrayList<>(currentWaveEnemies);
    }
}