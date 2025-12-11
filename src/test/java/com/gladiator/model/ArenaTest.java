package com.gladiator.model;

import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
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
    private List<Enemy> enemies;

    @BeforeEach
    void setUp() {
        arena = new Arena(20, 20);
        gladiator = mock(Gladiator.class);
        enemies = new ArrayList<>();

        arena.setGladiator(gladiator);
        arena.setEnemies(enemies);
    }

    @Test
    void testArenaDimensions() {
        assertEquals(20, arena.getWidth());
        assertEquals(20, arena.getHeight());
    }

    @Test
    void testGetSetGladiator() {
        Gladiator newGladiator = mock(Gladiator.class);
        arena.setGladiator(newGladiator);

        assertEquals(newGladiator, arena.getGladiator());
    }

    @Test
    void testGetSetEnemies() {
        List<Enemy> newEnemies = new ArrayList<>();
        Enemy enemy1 = mock(Enemy.class);
        Enemy enemy2 = mock(Enemy.class);
        newEnemies.add(enemy1);
        newEnemies.add(enemy2);

        arena.setEnemies(newEnemies);

        assertEquals(newEnemies, arena.getEnemies());
        assertEquals(2, arena.getEnemies().size());
    }

    @Test
    void testIsEnemyWhenEnemyExistsAtPosition() {
        Enemy enemy = mock(Enemy.class);
        Position enemyPosition = new Position(5, 5);
        when(enemy.getPosition()).thenReturn(enemyPosition);

        enemies.add(enemy);

        assertTrue(arena.isEnemy(new Position(5, 5)));
    }

    @Test
    void testIsEnemyWhenNoEnemyAtPosition() {
        Enemy enemy = mock(Enemy.class);
        when(enemy.getPosition()).thenReturn(new Position(10, 10));

        enemies.add(enemy);

        assertFalse(arena.isEnemy(new Position(5, 5)));
    }

    @Test
    void testIsEnemyWithEmptyEnemiesList() {
        assertFalse(arena.isEnemy(new Position(5, 5)));
    }

    @Test
    void testIsEnemyWithMultipleEnemies() {
        Enemy enemy1 = mock(Enemy.class);
        Enemy enemy2 = mock(Enemy.class);
        Enemy enemy3 = mock(Enemy.class);

        when(enemy1.getPosition()).thenReturn(new Position(1, 1));
        when(enemy2.getPosition()).thenReturn(new Position(2, 2));
        when(enemy3.getPosition()).thenReturn(new Position(3, 3));

        enemies.add(enemy1);
        enemies.add(enemy2);
        enemies.add(enemy3);

        assertTrue(arena.isEnemy(new Position(2, 2)));
        assertFalse(arena.isEnemy(new Position(4, 4)));
    }

    @Test
    void testIsEnemyPositionComparison() {
        Enemy enemy = mock(Enemy.class);
        Position enemyPos = new Position(7, 8);
        when(enemy.getPosition()).thenReturn(enemyPos);

        enemies.add(enemy);

        assertTrue(arena.isEnemy(new Position(7, 8)));
        assertFalse(arena.isEnemy(new Position(7, 9)));
        assertFalse(arena.isEnemy(new Position(8, 8)));
    }

    @Test
    void testArenaWithDifferentDimensions() {
        Arena largeArena = new Arena(100, 50);
        assertEquals(100, largeArena.getWidth());
        assertEquals(50, largeArena.getHeight());

        Arena smallArena = new Arena(10, 10);
        assertEquals(10, smallArena.getWidth());
        assertEquals(10, smallArena.getHeight());
    }
}