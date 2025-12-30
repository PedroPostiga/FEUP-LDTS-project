package com.gladiator.model;

import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.enemy.EnemyPool;
import com.gladiator.model.attack.projectile.SingleArrowPool;
import com.gladiator.model.entity.Obstacle;
import com.gladiator.model.entity.SmallRock;
import com.gladiator.model.gladiator.Gladiator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ArenaTest {
    private Arena arena;
    private Gladiator gladiator;
    private EnemyPool enemyPool;
    private SingleArrowPool arrowPool;
    private List<Obstacle> obstacles;

    @BeforeEach
    void setUp() {
        arena = new Arena(400, 300);
        gladiator = mock(Gladiator.class);
        enemyPool = mock(EnemyPool.class);
        arrowPool = mock(SingleArrowPool.class);
        obstacles = new ArrayList<>();

        arena.setGladiator(gladiator);
        arena.setEnemiePool(enemyPool);
        arena.setArrowPool(arrowPool);
        arena.setObstacles(obstacles);
    }

    @Test
    void testArenaDimensions() {
        assertEquals(400, arena.getWidth());
        assertEquals(300, arena.getHeight());
    }

    @Test
    void testGetSetGladiator() {
        Gladiator newGladiator = mock(Gladiator.class);
        arena.setGladiator(newGladiator);

        assertEquals(newGladiator, arena.getGladiator());
    }

    @Test
    void testGetSetEnemyPool() {
        EnemyPool newPool = mock(EnemyPool.class);
        arena.setEnemiePool(newPool);

        assertEquals(newPool, arena.getEnemiePool());
    }

    @Test
    void testGetSetArrowPool() {
        SingleArrowPool newPool = mock(SingleArrowPool.class);
        arena.setArrowPool(newPool);

        assertEquals(newPool, arena.getArrowPool());
    }

    @Test
    void testGetSetObstacles() {
        List<Obstacle> newObstacles = new ArrayList<>();
        newObstacles.add(new SmallRock(100, 100));
        arena.setObstacles(newObstacles);

        assertEquals(newObstacles, arena.getObstacles());
    }

    @Test
    void testIsEnemyWhenEnemyExistsAtPosition() {
        Enemy enemy = mock(Enemy.class);
        java.awt.Rectangle enemyHitbox = new java.awt.Rectangle(5, 5, 16, 16);
        when(enemy.getHitbox()).thenReturn(enemyHitbox);
        when(enemyPool.getAllActiveEnemies()).thenReturn(List.of(enemy));

        assertTrue(arena.isEnemy(new java.awt.Rectangle(5, 5, 10, 10)));
    }

    @Test
    void testIsEnemyWhenNoEnemyAtPosition() {
        Enemy enemy = mock(Enemy.class);
        java.awt.Rectangle enemyHitbox = new java.awt.Rectangle(100, 100, 16, 16);
        when(enemy.getHitbox()).thenReturn(enemyHitbox);
        when(enemyPool.getAllActiveEnemies()).thenReturn(List.of(enemy));

        assertFalse(arena.isEnemy(new java.awt.Rectangle(5, 5, 10, 10)));
    }

    @Test
    void testIsEnemyWithEmptyEnemiesList() {
        when(enemyPool.getAllActiveEnemies()).thenReturn(new ArrayList<>());
        assertFalse(arena.isEnemy(new java.awt.Rectangle(5, 5, 10, 10)));
    }

    @Test
    void testIsEnemyExcludesEnemy() {
        Enemy enemy1 = mock(Enemy.class);
        Enemy enemy2 = mock(Enemy.class);
        java.awt.Rectangle hitbox1 = new java.awt.Rectangle(5, 5, 16, 16);
        java.awt.Rectangle hitbox2 = new java.awt.Rectangle(25, 25, 16, 16);
        when(enemy1.getHitbox()).thenReturn(hitbox1);
        when(enemy2.getHitbox()).thenReturn(hitbox2);
        when(enemyPool.getAllActiveEnemies()).thenReturn(List.of(enemy1, enemy2));

        assertFalse(arena.isEnemy(new java.awt.Rectangle(5, 5, 10, 10), enemy1));
        assertTrue(arena.isEnemy(new java.awt.Rectangle(5, 5, 10, 10), enemy2));
    }

    @Test
    void testIsObstacle() {
        Obstacle obstacle = new SmallRock(50, 50);
        obstacles.add(obstacle);

        assertTrue(arena.isObstacle(new java.awt.Rectangle(50, 50, 10, 10)));
        assertFalse(arena.isObstacle(new java.awt.Rectangle(200, 200, 10, 10)));
    }

    @Test
    void testIsObstacleWithNullObstacles() {
        arena.setObstacles(null);
        assertFalse(arena.isObstacle(new java.awt.Rectangle(50, 50, 10, 10)));
    }

    @Test
    void testIsArrow() {
        com.gladiator.model.attack.projectile.Arrow arrow = mock(com.gladiator.model.attack.projectile.Arrow.class);
        java.awt.Rectangle arrowHitbox = new java.awt.Rectangle(25, 25, 6, 3);
        when(arrow.getHitbox()).thenReturn(arrowHitbox);
        when(arrowPool.getActiveArrows()).thenReturn(List.of(arrow));

        assertTrue(arena.isArrow(new java.awt.Rectangle(25, 25, 10, 10)));
        assertFalse(arena.isArrow(new java.awt.Rectangle(200, 200, 10, 10)));
    }

    @Test
    void testIsGladiator() {
        java.awt.Rectangle gladiatorHitbox = new java.awt.Rectangle(100, 100, 20, 20);
        when(gladiator.getHitbox()).thenReturn(gladiatorHitbox);

        assertTrue(arena.isGladiator(new java.awt.Rectangle(100, 100, 10, 10)));
        assertFalse(arena.isGladiator(new java.awt.Rectangle(200, 200, 10, 10)));
    }

    @Test
    void testIsGladiatorExcludesGladiator() {
        java.awt.Rectangle gladiatorHitbox = new java.awt.Rectangle(100, 100, 20, 20);
        when(gladiator.getHitbox()).thenReturn(gladiatorHitbox);

        assertFalse(arena.isGladiator(new java.awt.Rectangle(100, 100, 10, 10), gladiator));
    }

    @Test
    void testIsEmpty() {
        when(enemyPool.getAllActiveEnemies()).thenReturn(new ArrayList<>());
        obstacles.clear();
        java.awt.Rectangle gladiatorHitbox = new java.awt.Rectangle(100, 100, 20, 20);
        when(gladiator.getHitbox()).thenReturn(gladiatorHitbox);

        assertTrue(arena.isEmpty(new java.awt.Rectangle(200, 200, 10, 10)));
    }

    @Test
    void testIsEmptyWithEnemy() {
        Enemy enemy = mock(Enemy.class);
        java.awt.Rectangle enemyHitbox = new java.awt.Rectangle(50, 50, 16, 16);
        when(enemy.getHitbox()).thenReturn(enemyHitbox);
        when(enemyPool.getAllActiveEnemies()).thenReturn(List.of(enemy));

        assertFalse(arena.isEmpty(new java.awt.Rectangle(50, 50, 10, 10)));
    }

    @Test
    void testIsEmptyWithObstacle() {
        obstacles.add(new SmallRock(50, 50));
        when(enemyPool.getAllActiveEnemies()).thenReturn(new ArrayList<>());

        assertFalse(arena.isEmpty(new java.awt.Rectangle(50, 50, 10, 10)));
    }

    @Test
    void testIsEmptyWithGladiator() {
        java.awt.Rectangle gladiatorHitbox = new java.awt.Rectangle(100, 100, 20, 20);
        when(gladiator.getHitbox()).thenReturn(gladiatorHitbox);
        when(enemyPool.getAllActiveEnemies()).thenReturn(new ArrayList<>());
        obstacles.clear();

        assertFalse(arena.isEmpty(new java.awt.Rectangle(100, 100, 10, 10)));
    }

    @Test
    void testIsEnemyWithNullEnemyPool() {
        arena.setEnemiePool(null);
        assertFalse(arena.isEnemy(new java.awt.Rectangle(5, 5, 10, 10)));
    }

    @Test
    void testIsArrowWithNullArrowPool() {
        arena.setArrowPool(null);
        assertFalse(arena.isArrow(new java.awt.Rectangle(25, 25, 10, 10)));
    }

    @Test
    void testIsGladiatorWithNullGladiator() {
        arena.setGladiator(null);
        assertFalse(arena.isGladiator(new java.awt.Rectangle(100, 100, 10, 10)));
    }

    @Test
    void testIsEmptyWithExcludedEnemy() {
        Enemy enemy = mock(Enemy.class);
        java.awt.Rectangle enemyHitbox = new java.awt.Rectangle(50, 50, 16, 16);
        when(enemy.getHitbox()).thenReturn(enemyHitbox);
        when(enemyPool.getAllActiveEnemies()).thenReturn(List.of(enemy));
        obstacles.clear();
        java.awt.Rectangle gladiatorHitbox = new java.awt.Rectangle(100, 100, 20, 20);
        when(gladiator.getHitbox()).thenReturn(gladiatorHitbox);

        assertTrue(arena.isEmpty(new java.awt.Rectangle(50, 50, 10, 10), enemy));
    }

    @Test
    void testIsEmptyWithExcludedGladiator() {
        when(enemyPool.getAllActiveEnemies()).thenReturn(new ArrayList<>());
        obstacles.clear();
        java.awt.Rectangle gladiatorHitbox = new java.awt.Rectangle(100, 100, 20, 20);
        when(gladiator.getHitbox()).thenReturn(gladiatorHitbox);

        assertTrue(arena.isEmpty(new java.awt.Rectangle(100, 100, 10, 10), null, gladiator));
    }

    @Test
    void testIsEmptyWithBothExclusions() {
        Enemy enemy = mock(Enemy.class);
        java.awt.Rectangle enemyHitbox = new java.awt.Rectangle(50, 50, 16, 16);
        when(enemy.getHitbox()).thenReturn(enemyHitbox);
        when(enemyPool.getAllActiveEnemies()).thenReturn(List.of(enemy));
        obstacles.clear();
        java.awt.Rectangle gladiatorHitbox = new java.awt.Rectangle(100, 100, 20, 20);
        when(gladiator.getHitbox()).thenReturn(gladiatorHitbox);

        assertTrue(arena.isEmpty(new java.awt.Rectangle(50, 50, 10, 10), enemy, gladiator));
    }
}
