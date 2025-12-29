package com.gladiator.controller;

import com.gladiator.model.Arena;
import com.gladiator.model.attack.projectile.Arrow;
import com.gladiator.model.attack.projectile.SingleArrowPool;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.enemy.EnemyPool;
import com.gladiator.model.gladiator.Gladiator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class ProjectileUpdaterTest {
    private ProjectileUpdater updater;
    private Arena arena;
    private SingleArrowPool arrowPool;
    private EnemyPool enemyPool;
    private Gladiator gladiator;
    private List<Arrow> arrows;
    private List<Enemy> enemies;

    @BeforeEach
    void setUp() {
        updater = new ProjectileUpdater();
        arena = mock(Arena.class);
        arrowPool = mock(SingleArrowPool.class);
        enemyPool = mock(EnemyPool.class);
        gladiator = mock(Gladiator.class);
        arrows = new ArrayList<>();
        enemies = new ArrayList<>();

        when(arena.getArrowPool()).thenReturn(arrowPool);
        when(arena.getEnemiePool()).thenReturn(enemyPool);
        when(arena.getGladiator()).thenReturn(gladiator);
        when(arrowPool.getActiveArrows()).thenReturn(arrows);
        when(enemyPool.getAllActiveEnemies()).thenReturn(enemies);
    }

    @Test
    void testUpdateWithNoArrows() {
        updater.update(arena);
        
        verify(arrowPool).getActiveArrows();
    }

    @Test
    void testUpdateWithNullTarget() {
        Arrow arrow = mock(Arrow.class);
        
        when(arrow.getTarget()).thenReturn(null);
        
        arrows.add(arrow);
        
        updater.update(arena);
        
        verify(arrowPool).releaseArrow(arrow);
    }

    @Test
    void testUpdateWithDeadTarget() {
        Arrow arrow = mock(Arrow.class);
        Enemy target = mock(Enemy.class);
        
        when(arrow.getTarget()).thenReturn(target);
        when(target.isAlive()).thenReturn(false);
        
        arrows.add(arrow);
        
        updater.update(arena);
        
        verify(arrowPool).releaseArrow(arrow);
    }

    @Test
    void testUpdateWithTargetNotInActiveEnemies() {
        Arrow arrow = mock(Arrow.class);
        Enemy target = mock(Enemy.class);
        
        when(arrow.getTarget()).thenReturn(target);
        when(target.isAlive()).thenReturn(true);
        when(enemyPool.getAllActiveEnemies()).thenReturn(new ArrayList<>());
        
        arrows.add(arrow);
        
        updater.update(arena);
        
        verify(arrowPool).releaseArrow(arrow);
    }

    @Test
    void testUpdateArrowHitsEnemy() {
        Arrow arrow = mock(Arrow.class);
        Enemy target = mock(Enemy.class);
        Rectangle arrowHitbox = new Rectangle(100, 100, 6, 3);
        Rectangle enemyHitbox = new Rectangle(100, 100, 16, 16);
        
        when(arrow.getTarget()).thenReturn(target);
        when(target.isAlive()).thenReturn(true);
        when(arrow.getPosition()).thenReturn(new Position(100, 100));
        when(arrow.getHitbox()).thenReturn(arrowHitbox);
        when(target.getHitbox()).thenReturn(enemyHitbox);
        when(arrow.getSpeed()).thenReturn(10);
        when(arrow.getDamage()).thenReturn(5);
        when(arena.isEnemy(any(Rectangle.class))).thenReturn(true);
        enemies.add(target);
        
        arrows.add(arrow);
        
        updater.update(arena);
        
        verify(target).takeDamage(5);
        verify(arrowPool).releaseArrow(arrow);
    }

    @Test
    void testUpdateArrowMovesTowardsTarget() {
        Arrow arrow = mock(Arrow.class);
        Enemy target = mock(Enemy.class);
        Rectangle arrowHitbox = new Rectangle(100, 100, 6, 3);
        Rectangle enemyHitbox = new Rectangle(150, 150, 16, 16);
        
        when(arrow.getTarget()).thenReturn(target);
        when(target.isAlive()).thenReturn(true);
        when(arrow.getPosition()).thenReturn(new Position(100, 100));
        when(arrow.getHitbox()).thenReturn(arrowHitbox);
        when(target.getHitbox()).thenReturn(enemyHitbox);
        when(arrow.getSpeed()).thenReturn(10);
        when(arrow.getDistanceTraveled()).thenReturn(0.0);
        when(arrow.getMaxDistance()).thenReturn(1000.0);
        when(arena.isEnemy(any(Rectangle.class))).thenReturn(false);
        when(arena.isEmpty(any(Rectangle.class), isNull(), eq(gladiator))).thenReturn(true);
        enemies.add(target);
        
        arrows.add(arrow);
        
        updater.update(arena);
        
        verify(arrow).setPosition(any(Position.class));
    }

    @Test
    void testUpdateArrowExceedsMaxDistance() {
        Arrow arrow = mock(Arrow.class);
        Enemy target = mock(Enemy.class);
        Rectangle arrowHitbox = new Rectangle(100, 100, 6, 3);
        Rectangle enemyHitbox = new Rectangle(150, 150, 16, 16);
        
        when(arrow.getTarget()).thenReturn(target);
        when(target.isAlive()).thenReturn(true);
        when(arrow.getPosition()).thenReturn(new Position(100, 100));
        when(arrow.getHitbox()).thenReturn(arrowHitbox);
        when(target.getHitbox()).thenReturn(enemyHitbox);
        when(arrow.getDistanceTraveled()).thenReturn(1001.0);
        when(arrow.getMaxDistance()).thenReturn(1000.0);
        enemies.add(target);
        
        arrows.add(arrow);
        
        updater.update(arena);
        
        verify(arrowPool).releaseArrow(arrow);
    }

    @Test
    void testUpdateArrowHitsObstacle() {
        Arrow arrow = mock(Arrow.class);
        Enemy target = mock(Enemy.class);
        Rectangle arrowHitbox = new Rectangle(100, 100, 6, 3);
        Rectangle enemyHitbox = new Rectangle(150, 150, 16, 16);
        
        when(arrow.getTarget()).thenReturn(target);
        when(target.isAlive()).thenReturn(true);
        when(arrow.getPosition()).thenReturn(new Position(100, 100));
        when(arrow.getHitbox()).thenReturn(arrowHitbox);
        when(target.getHitbox()).thenReturn(enemyHitbox);
        when(arrow.getSpeed()).thenReturn(10);
        when(arrow.getDistanceTraveled()).thenReturn(0.0);
        when(arrow.getMaxDistance()).thenReturn(1000.0);
        when(arena.isEnemy(any(Rectangle.class))).thenReturn(false);
        when(arena.isEmpty(any(Rectangle.class), isNull(), eq(gladiator))).thenReturn(false);
        enemies.add(target);
        
        arrows.add(arrow);
        
        updater.update(arena);
        
        verify(arrowPool).releaseArrow(arrow);
    }
}

