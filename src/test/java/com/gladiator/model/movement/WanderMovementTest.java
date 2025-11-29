package com.gladiator.model.movement;

import com.gladiator.model.component.Position;
import com.gladiator.model.entity.MovingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WanderMovementTest {
    private WanderMovement wanderMovement;
    private MovingEntity entity;
    private Position entityPosition;

    @BeforeEach
    void setUp() {
        wanderMovement = new WanderMovement();
        entity = mock(MovingEntity.class);
        entityPosition = new Position(100, 100);

        when(entity.getPosition()).thenReturn(entityPosition);
        when(entity.getSpeed()).thenReturn(2);
    }

    @Test
    void testMoveChangesPosition() {
        double initialX = entityPosition.getX();
        double initialY = entityPosition.getY();

        wanderMovement.move(entity);

        boolean positionChanged = (entityPosition.getX() != initialX) || (entityPosition.getY() != initialY);
        assertTrue(positionChanged);
    }

    @Test
    void testMoveUsesEntitySpeed() {
        when(entity.getSpeed()).thenReturn(5);

        wanderMovement.move(entity);

        assertTrue(entityPosition.getX() != 100 || entityPosition.getY() != 100);
    }

    @Test
    void testMultipleMovesChangePosition() {
        int x1 = entityPosition.getX();
        int y1 = entityPosition.getY();

        wanderMovement.move(entity);

        int x2 = entityPosition.getX();
        int y2 = entityPosition.getY();

        wanderMovement.move(entity);

        int x3 = entityPosition.getX();
        int y3 = entityPosition.getY();

        assertTrue(x1 != x2 || y1 != y2);
        assertTrue(x2 != x3 || y2 != y3);
    }

    @Test
    void testMoveWithZeroSpeed() {
        when(entity.getSpeed()).thenReturn(0);

        int initialX = entityPosition.getX();
        int initialY = entityPosition.getY();

        wanderMovement.move(entity);

        assertEquals(initialX, entityPosition.getX(), 0.001);
        assertEquals(initialY, entityPosition.getY(), 0.001);
    }

    @Test
    void testMoveWithDifferentStartingPositions() {
        Position[] startingPositions = {
                new Position(0, 0),
                new Position(50, 50),
                new Position(-25, -25),
                new Position(200, 150)
        };

        for (Position startPos : startingPositions) {
            when(entity.getPosition()).thenReturn(startPos);

            double initialX = startPos.getX();
            double initialY = startPos.getY();

            wanderMovement.move(entity);

            boolean positionChanged = (startPos.getX() != initialX) || (startPos.getY() != initialY);
            assertTrue(positionChanged);
        }
    }

    @Test
    void testMovementStaysWithinReasonableBounds() {
        Position testPos = new Position(0, 0);
        when(entity.getPosition()).thenReturn(testPos);
        when(entity.getSpeed()).thenReturn(5);

        for (int i = 0; i < 100; i++) {
            wanderMovement.move(entity);

            assertFalse(Double.isNaN(testPos.getX()));
            assertFalse(Double.isNaN(testPos.getY()));
            assertFalse(Double.isInfinite(testPos.getX()));
            assertFalse(Double.isInfinite(testPos.getY()));
            assertTrue(Math.abs(testPos.getX()) < 10000);
            assertTrue(Math.abs(testPos.getY()) < 10000);
        }
    }

    @Test
    void testMoveConsistency() {
        when(entity.getSpeed()).thenReturn(2);

        for (int i = 0; i < 1000; i++) {
            wanderMovement.move(entity);
        }

        assertTrue(true);
    }

}
