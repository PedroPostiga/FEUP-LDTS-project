package com.gladiator.model.movement;

import com.gladiator.model.component.Position;
import com.gladiator.model.entity.MovingEntity;
import com.gladiator.model.gladiator.Gladiator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ChaseMovementTest {
    private ChaseMovement chaseMovement;
    private MovingEntity entity;
    private Gladiator gladiator;

    @BeforeEach
    void setUp() {
        gladiator = mock(Gladiator.class);
        entity = mock(MovingEntity.class);

        chaseMovement = new ChaseMovement(5.0, gladiator);
    }

    @Test
    void testMoveTowardsGladiator() {
        Position entityPos = new Position(0, 0);
        Position gladiatorPos = new Position(100, 0);

        when(entity.getPosition()).thenReturn(entityPos);
        when(gladiator.getPosition()).thenReturn(gladiatorPos);

        chaseMovement.move(entity);

        assertEquals(5, entityPos.getX(), 0.001);
        assertEquals(0, entityPos.getY(), 0.001);
    }

    @Test
    void testMoveVertically() {
        Position entityPos = new Position(50, 0);
        Position gladiatorPos = new Position(50, 100);

        when(entity.getPosition()).thenReturn(entityPos);
        when(gladiator.getPosition()).thenReturn(gladiatorPos);

        chaseMovement.move(entity);

        assertEquals(50, entityPos.getX(), 0.001);
        assertEquals(5, entityPos.getY(), 0.001);
    }

    @Test
    void testMoveDiagonally() {
        Position entityPos = new Position(0, 0);
        Position gladiatorPos = new Position(30, 40);

        when(entity.getPosition()).thenReturn(entityPos);
        when(gladiator.getPosition()).thenReturn(gladiatorPos);

        chaseMovement.move(entity);

        assertEquals(3, entityPos.getX(), 0.001);
        assertEquals(4, entityPos.getY(), 0.001);
    }

    @Test
    void testMoveAtSamePosition() {
        Position entityPos = new Position(100, 100);
        Position gladiatorPos = new Position(100, 100);

        when(entity.getPosition()).thenReturn(entityPos);
        when(gladiator.getPosition()).thenReturn(gladiatorPos);

        chaseMovement.move(entity);

        assertEquals(100, entityPos.getX(), 0.001);
        assertEquals(100, entityPos.getY(), 0.001);
    }

    @Test
    void testMoveWithDifferentSpeed() {
        ChaseMovement fastMovement = new ChaseMovement(10.0, gladiator);

        Position entityPos = new Position(0, 0);
        Position gladiatorPos = new Position(100, 0);

        when(entity.getPosition()).thenReturn(entityPos);
        when(gladiator.getPosition()).thenReturn(gladiatorPos);

        fastMovement.move(entity);

        assertEquals(10, entityPos.getX(), 0.001);
        assertEquals(0, entityPos.getY(), 0.001);
    }

    @Test
    void testMultipleMovements() {
        Position entityPos = new Position(0, 0);
        Position gladiatorPos = new Position(100, 0);

        when(entity.getPosition()).thenReturn(entityPos);
        when(gladiator.getPosition()).thenReturn(gladiatorPos);

        chaseMovement.move(entity);
        assertEquals(5, entityPos.getX(), 0.001);
        assertEquals(0, entityPos.getY(), 0.001);

        chaseMovement.move(entity);
        assertEquals(10, entityPos.getX(), 0.001);
        assertEquals(0, entityPos.getY(), 0.001);
    }

    @Test
    void testMoveBackwards() {
        Position entityPos = new Position(100, 50);
        Position gladiatorPos = new Position(0, 50);

        when(entity.getPosition()).thenReturn(entityPos);
        when(gladiator.getPosition()).thenReturn(gladiatorPos);

        chaseMovement.move(entity);

        assertEquals(95, entityPos.getX(), 0.001);
        assertEquals(50, entityPos.getY(), 0.001);
    }
}
