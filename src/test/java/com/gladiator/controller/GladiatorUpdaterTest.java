package com.gladiator.controller;

import com.gladiator.model.Arena;
import com.gladiator.model.attack.BowAttack;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.enemy.EnemyPool;
import com.gladiator.model.gladiator.Gladiator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class GladiatorUpdaterTest {
    private GladiatorUpdater updater;
    private Arena arena;
    private Gladiator gladiator;
    private EnemyPool enemyPool;
    private List<Enemy> enemies;
    private SwordAttack swordAttack;
    private BowAttack bowAttack;

    @BeforeEach
    void setUp() {
        updater = new GladiatorUpdater();
        arena = mock(Arena.class);
        gladiator = mock(Gladiator.class);
        enemyPool = mock(EnemyPool.class);
        enemies = new ArrayList<>();
        swordAttack = mock(SwordAttack.class);
        bowAttack = mock(BowAttack.class);

        when(arena.getGladiator()).thenReturn(gladiator);
        when(arena.getEnemiePool()).thenReturn(enemyPool);
        when(enemyPool.getAllActiveEnemies()).thenReturn(enemies);
        when(gladiator.getSwordAttack()).thenReturn(swordAttack);
        when(gladiator.getBowAttack()).thenReturn(bowAttack);
        
        List<com.gladiator.model.entity.MovingEntity> swordTargets = new ArrayList<>();
        List<Enemy> bowTargets = new ArrayList<>();
        when(swordAttack.getTargets()).thenReturn(swordTargets);
        when(bowAttack.getTargets()).thenReturn(bowTargets);
    }

    @Test
    void testUpdateWithNullGladiator() {
        when(arena.getGladiator()).thenReturn(null);
        
        updater.update(arena);
        
        verify(arena).getGladiator();
        verify(arena, never()).getEnemiePool();
    }

    @Test
    void testUpdateWithNoEnemies() {
        updater.update(arena);
        
        verify(swordAttack, never()).attack(any());
        verify(bowAttack, never()).attack(any());
    }

    @Test
    void testUpdateWithEnemyInSwordRange() {
        Enemy enemy = mock(Enemy.class);
        Rectangle gladiatorHitbox = new Rectangle(100, 100, 20, 20);
        Rectangle enemyHitbox = new Rectangle(110, 110, 16, 16);
        
        when(enemy.isAlive()).thenReturn(true);
        when(enemy.getHitbox()).thenReturn(enemyHitbox);
        when(gladiator.getHitbox()).thenReturn(gladiatorHitbox);
        when(swordAttack.getRange()).thenReturn(50);
        
        enemies.add(enemy);
        
        updater.update(arena);
        
        verify(swordAttack, atLeastOnce()).getTargets();
        verify(swordAttack).attack(gladiator);
    }

    @Test
    void testUpdateWithEnemyOutOfSwordRange() {
        Enemy enemy = mock(Enemy.class);
        Rectangle gladiatorHitbox = new Rectangle(100, 100, 20, 20);
        Rectangle enemyHitbox = new Rectangle(200, 200, 16, 16);
        
        when(enemy.isAlive()).thenReturn(true);
        when(enemy.getHitbox()).thenReturn(enemyHitbox);
        when(gladiator.getHitbox()).thenReturn(gladiatorHitbox);
        when(swordAttack.getRange()).thenReturn(50);
        
        enemies.add(enemy);
        
        updater.update(arena);
        
        verify(swordAttack, never()).attack(any());
    }

    @Test
    void testUpdateWithDeadEnemy() {
        Enemy enemy = mock(Enemy.class);
        Rectangle gladiatorHitbox = new Rectangle(100, 100, 20, 20);
        
        when(enemy.isAlive()).thenReturn(false);
        when(gladiator.getHitbox()).thenReturn(gladiatorHitbox);
        
        enemies.add(enemy);
        
        updater.update(arena);
        
        verify(swordAttack, never()).attack(any());
    }

    @Test
    void testUpdateBowAttackAlwaysFires() {
        Enemy enemy = mock(Enemy.class);
        Rectangle gladiatorHitbox = new Rectangle(100, 100, 20, 20);
        Rectangle enemyHitbox = new Rectangle(200, 200, 16, 16);
        
        when(enemy.isAlive()).thenReturn(true);
        when(enemy.getHitbox()).thenReturn(enemyHitbox);
        when(gladiator.getHitbox()).thenReturn(gladiatorHitbox);
        when(swordAttack.getRange()).thenReturn(50);
        
        enemies.add(enemy);
        
        updater.update(arena);
        
        verify(bowAttack, atLeastOnce()).getTargets();
        verify(bowAttack).attack(gladiator);
    }

    @Test
    void testUpdateWithNullSwordAttack() {
        when(gladiator.getSwordAttack()).thenReturn(null);
        Enemy enemy = mock(Enemy.class);
        enemies.add(enemy);
        
        updater.update(arena);
        
        verify(bowAttack).attack(gladiator);
    }

    @Test
    void testUpdateWithNullBowAttack() {
        when(gladiator.getBowAttack()).thenReturn(null);
        Enemy enemy = mock(Enemy.class);
        Rectangle gladiatorHitbox = new Rectangle(100, 100, 20, 20);
        Rectangle enemyHitbox = new Rectangle(110, 110, 16, 16);
        
        when(enemy.isAlive()).thenReturn(true);
        when(enemy.getHitbox()).thenReturn(enemyHitbox);
        when(gladiator.getHitbox()).thenReturn(gladiatorHitbox);
        when(swordAttack.getRange()).thenReturn(50);
        
        enemies.add(enemy);
        
        updater.update(arena);
        
        verify(swordAttack).attack(gladiator);
    }
}

