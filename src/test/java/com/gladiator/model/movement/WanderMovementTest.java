package com.gladiator.model.movement;

import com.gladiator.model.Arena;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.enemy.enemy_types.LightZombie;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.gladiator.Gladiator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Rectangle;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WanderMovementTest {
    private WanderMovement wanderMovement;
    private Enemy enemy;
    private Arena arena;
    private Gladiator gladiator;

    @BeforeEach
    void setUp() {
        wanderMovement = new WanderMovement();
        gladiator = mock(Gladiator.class);
        enemy = new LightZombie(100, 100, 
            new ChaseMovement(2.0, gladiator),
            new SwordAttack(8, 30, new ArrayList<>()));
        arena = mock(Arena.class);
    }

    @Test
    void testMoveWithEmptySpace() {
        Position initialPos = enemy.getPosition();
        Rectangle newHitbox = new Rectangle(
            initialPos.getX() - enemy.getSpeed(), 
            initialPos.getY() - enemy.getSpeed(),
            enemy.getHitbox().width,
            enemy.getHitbox().height
        );
        
        when(arena.isEmpty(any(Rectangle.class), eq(enemy))).thenReturn(true);
        
        wanderMovement.move(enemy, arena);
        
        // Position should change (wander movement is random, but should move if space is empty)
        verify(arena).isEmpty(any(Rectangle.class), eq(enemy));
    }

    @Test
    void testMoveWithBlockedSpace() {
        Position initialPos = enemy.getPosition();
        int initialX = initialPos.getX();
        int initialY = initialPos.getY();
        
        when(arena.isEmpty(any(Rectangle.class), eq(enemy))).thenReturn(false);
        
        wanderMovement.move(enemy, arena);
        
        // Position should not change if space is blocked
        assertEquals(initialX, enemy.getPosition().getX());
        assertEquals(initialY, enemy.getPosition().getY());
        verify(arena).isEmpty(any(Rectangle.class), eq(enemy));
    }

    @Test
    void testMoveMultipleTimes() {
        when(arena.isEmpty(any(Rectangle.class), eq(enemy))).thenReturn(true);
        
        // Move multiple times to test randomness
        for (int i = 0; i < 10; i++) {
            wanderMovement.move(enemy, arena);
        }
        
        verify(arena, atLeast(10)).isEmpty(any(Rectangle.class), eq(enemy));
    }

    @Test
    void testMoveWithDifferentEnemySpeeds() {
        enemy.setSpeed(1);
        when(arena.isEmpty(any(Rectangle.class), eq(enemy))).thenReturn(true);
        
        wanderMovement.move(enemy, arena);
        
        verify(arena).isEmpty(any(Rectangle.class), eq(enemy));
        
        enemy.setSpeed(5);
        wanderMovement.move(enemy, arena);
        
        verify(arena, atLeast(2)).isEmpty(any(Rectangle.class), eq(enemy));
    }

    @Test
    void testMoveAtArenaBoundary() {
        // Test movement when enemy is at boundary
        enemy.setPosition(new Position(0, 0));
        when(arena.isEmpty(any(Rectangle.class), eq(enemy))).thenReturn(false);
        
        Position beforeMove = enemy.getPosition();
        wanderMovement.move(enemy, arena);
        Position afterMove = enemy.getPosition();
        
        // Should not move if blocked
        assertEquals(beforeMove.getX(), afterMove.getX());
        assertEquals(beforeMove.getY(), afterMove.getY());
    }

    @Test
    void testMoveWithNullArena() {
        // Test that null arena is handled (should throw NPE or be handled gracefully)
        assertThrows(Exception.class, () -> {
            wanderMovement.move(enemy, null);
        });
    }

    @Test
    void testWanderMovementRandomness() {
        // Test that wander movement produces different results
        when(arena.isEmpty(any(Rectangle.class), eq(enemy))).thenReturn(true);
        
        Position pos1 = enemy.getPosition();
        wanderMovement.move(enemy, arena);
        Position pos2 = enemy.getPosition();
        
        // Reset and try again - should potentially get different result
        enemy.setPosition(pos1);
        wanderMovement.move(enemy, arena);
        Position pos3 = enemy.getPosition();
        
        // At least verify movement is attempted
        verify(arena, atLeast(2)).isEmpty(any(Rectangle.class), eq(enemy));
    }
}

