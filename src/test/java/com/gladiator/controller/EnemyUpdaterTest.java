package com.gladiator.controller;

import com.gladiator.model.Arena;
import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.enemy.EnemyPool;
import com.gladiator.model.gladiator.Gladiator;
import com.gladiator.model.movement.MovementStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class EnemyUpdaterTest {
    private EnemyUpdater updater;
    private Arena arena;
    private EnemyPool enemyPool;
    private Gladiator gladiator;
    private List<Enemy> enemies;

    @BeforeEach
    void setUp() {
        updater = new EnemyUpdater();
        arena = mock(Arena.class);
        enemyPool = mock(EnemyPool.class);
        gladiator = mock(Gladiator.class);
        enemies = new ArrayList<>();

        when(arena.getEnemiePool()).thenReturn(enemyPool);
        when(arena.getGladiator()).thenReturn(gladiator);
        when(enemyPool.getAllActiveEnemies()).thenReturn(enemies);
    }

    @Test
    void testUpdateWithNoEnemies() {
        updater.update(arena);
        
        verify(enemyPool).getAllActiveEnemies();
    }

    @Test
    void testUpdateWithAliveEnemy() {
        Enemy enemy = mock(Enemy.class);
        MovementStrategy movement = mock(MovementStrategy.class);
        AttackStrategy attack = mock(AttackStrategy.class);
        
        when(enemy.isAlive()).thenReturn(true);
        when(enemy.getMovementStrategy()).thenReturn(movement);
        when(enemy.getAttackStrategy()).thenReturn(attack);
        
        enemies.add(enemy);
        
        updater.update(arena);
        
        verify(movement).move(enemy, arena);
        verify(attack).attack(enemy);
        verify(enemyPool, never()).releaseEnemy(enemy);
    }

    @Test
    void testUpdateWithDeadEnemy() {
        Enemy enemy = mock(Enemy.class);
        
        when(enemy.isAlive()).thenReturn(false);
        
        enemies.add(enemy);
        
        updater.update(arena);
        
        verify(enemyPool).releaseEnemy(enemy);
        verify(enemy, never()).getMovementStrategy();
        verify(enemy, never()).getAttackStrategy();
    }

    @Test
    void testUpdateWithMultipleEnemies() {
        Enemy enemy1 = mock(Enemy.class);
        Enemy enemy2 = mock(Enemy.class);
        MovementStrategy movement1 = mock(MovementStrategy.class);
        MovementStrategy movement2 = mock(MovementStrategy.class);
        AttackStrategy attack1 = mock(AttackStrategy.class);
        AttackStrategy attack2 = mock(AttackStrategy.class);
        
        when(enemy1.isAlive()).thenReturn(true);
        when(enemy2.isAlive()).thenReturn(true);
        when(enemy1.getMovementStrategy()).thenReturn(movement1);
        when(enemy2.getMovementStrategy()).thenReturn(movement2);
        when(enemy1.getAttackStrategy()).thenReturn(attack1);
        when(enemy2.getAttackStrategy()).thenReturn(attack2);
        
        enemies.add(enemy1);
        enemies.add(enemy2);
        
        updater.update(arena);
        
        verify(movement1).move(enemy1, arena);
        verify(movement2).move(enemy2, arena);
        verify(attack1).attack(enemy1);
        verify(attack2).attack(enemy2);
    }

    @Test
    void testUpdateWithNullMovementStrategy() {
        Enemy enemy = mock(Enemy.class);
        AttackStrategy attack = mock(AttackStrategy.class);
        
        when(enemy.isAlive()).thenReturn(true);
        when(enemy.getMovementStrategy()).thenReturn(null);
        when(enemy.getAttackStrategy()).thenReturn(attack);
        
        enemies.add(enemy);
        
        updater.update(arena);
        
        verify(enemy).getMovementStrategy();
        verify(attack).attack(enemy);
    }

    @Test
    void testUpdateWithNullAttackStrategy() {
        Enemy enemy = mock(Enemy.class);
        MovementStrategy movement = mock(MovementStrategy.class);
        
        when(enemy.isAlive()).thenReturn(true);
        when(enemy.getMovementStrategy()).thenReturn(movement);
        when(enemy.getAttackStrategy()).thenReturn(null);
        
        enemies.add(enemy);
        
        updater.update(arena);
        
        verify(movement).move(enemy, arena);
        verify(enemy).getAttackStrategy();
    }
}

