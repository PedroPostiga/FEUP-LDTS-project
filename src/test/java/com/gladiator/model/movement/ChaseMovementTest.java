package com.gladiator.model.movement;

import com.gladiator.model.Arena;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.enemy.enemy_types.FatZombie;
import com.gladiator.model.gladiator.Gladiator;
import com.gladiator.model.attack.SwordAttack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChaseMovementTest {
    private ChaseMovement chaseMovement;
    private Enemy enemy;
    private Arena arena;
    private Gladiator gladiator;

    @BeforeEach
    void setUp() {
        gladiator = mock(Gladiator.class);
        arena = mock(Arena.class);
        enemy = new FatZombie(0, 0, null, new SwordAttack(10, 10, new ArrayList<>()));

        chaseMovement = new ChaseMovement(5.0, gladiator);
        
        when(arena.getGladiator()).thenReturn(gladiator);
        when(gladiator.getPosition()).thenReturn(new Position(100, 0));
        when(arena.isEmpty(any(Rectangle.class), any())).thenReturn(true);
    }

    @Test
    void testMoveTowardsGladiator() {
        enemy.setPosition(new Position(0, 0));
        when(gladiator.getPosition()).thenReturn(new Position(100, 0));

        chaseMovement.move(enemy, arena);

        assertTrue(enemy.getPosition().getX() > 0);
        assertEquals(0, enemy.getPosition().getY());
    }

    @Test
    void testMoveVertically() {
        enemy.setPosition(new Position(50, 0));
        when(gladiator.getPosition()).thenReturn(new Position(50, 100));

        chaseMovement.move(enemy, arena);

        assertEquals(50, enemy.getPosition().getX());
        assertTrue(enemy.getPosition().getY() > 0);
    }

    @Test
    void testMoveDiagonally() {
        enemy.setPosition(new Position(0, 0));
        when(gladiator.getPosition()).thenReturn(new Position(30, 40));

        chaseMovement.move(enemy, arena);

        assertTrue(enemy.getPosition().getX() > 0 || enemy.getPosition().getY() > 0);
    }

    @Test
    void testMoveAtSamePosition() {
        enemy.setPosition(new Position(100, 100));
        when(gladiator.getPosition()).thenReturn(new Position(100, 100));

        chaseMovement.move(enemy, arena);

        assertEquals(100, enemy.getPosition().getX());
        assertEquals(100, enemy.getPosition().getY());
    }

    @Test
    void testMoveBlockedByObstacle() {
        enemy.setPosition(new Position(0, 0));
        when(gladiator.getPosition()).thenReturn(new Position(100, 0));
        when(arena.isEmpty(any(Rectangle.class), any())).thenReturn(false);

        Position initialPos = enemy.getPosition();
        chaseMovement.move(enemy, arena);

        assertEquals(initialPos.getX(), enemy.getPosition().getX());
        assertEquals(initialPos.getY(), enemy.getPosition().getY());
    }

    @Test
    void testMoveBackwards() {
        enemy.setPosition(new Position(100, 50));
        when(gladiator.getPosition()).thenReturn(new Position(0, 50));

        chaseMovement.move(enemy, arena);

        assertTrue(enemy.getPosition().getX() < 100);
        assertEquals(50, enemy.getPosition().getY());
    }

    @Test
    void testMoveUsesEnemySpeed() {
        enemy.setSpeed(3);
        enemy.setPosition(new Position(0, 0));
        when(gladiator.getPosition()).thenReturn(new Position(100, 0));

        chaseMovement.move(enemy, arena);

        assertTrue(enemy.getPosition().getX() >= 3);
    }

    @Test
    void testMoveWithNullGladiator() {
        when(arena.getGladiator()).thenReturn(null);
        
        Position initialPos = enemy.getPosition();
        assertThrows(NullPointerException.class, () -> chaseMovement.move(enemy, arena));
    }

    @Test
    void testMoveWithZeroSpeed() {
        enemy.setSpeed(0);
        enemy.setPosition(new Position(0, 0));
        when(gladiator.getPosition()).thenReturn(new Position(100, 0));

        chaseMovement.move(enemy, arena);

        assertEquals(0, enemy.getPosition().getX());
        assertEquals(0, enemy.getPosition().getY());
    }

    @Test
    void testMoveWithNegativeCoordinates() {
        enemy.setPosition(new Position(-10, -10));
        when(gladiator.getPosition()).thenReturn(new Position(0, 0));

        chaseMovement.move(enemy, arena);

        assertTrue(enemy.getPosition().getX() >= -10);
        assertTrue(enemy.getPosition().getY() >= -10);
    }

    @Test
    void testMoveWithLargeCoordinates() {
        enemy.setPosition(new Position(1000, 1000));
        when(gladiator.getPosition()).thenReturn(new Position(2000, 2000));

        chaseMovement.move(enemy, arena);

        assertTrue(enemy.getPosition().getX() >= 1000);
        assertTrue(enemy.getPosition().getY() >= 1000);
    }

    @Test
    void testMoveWithDifferentSpeedMultipliers() {
        ChaseMovement slowChase = new ChaseMovement(0.5, gladiator);
        ChaseMovement fastChase = new ChaseMovement(2.0, gladiator);
        
        enemy.setPosition(new Position(0, 0));
        when(gladiator.getPosition()).thenReturn(new Position(100, 0));
        
        slowChase.move(enemy, arena);
        int slowX = enemy.getPosition().getX();
        
        enemy.setPosition(new Position(0, 0));
        fastChase.move(enemy, arena);
        int fastX = enemy.getPosition().getX();
        
        // Both should move, but speed multiplier affects movement
        assertTrue(slowX > 0);
        assertTrue(fastX > 0);
    }
}
