package com.gladiator.model.attack.projectile;

import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ArrowTest {
    private Arrow arrow;
    private Position startPos;
    private Enemy target;

    @BeforeEach
    void setUp() {
        startPos = new Position(100, 100);
        target = mock(Enemy.class);
        arrow = new Arrow(startPos, 10, 5, 100.0, target);
    }

    @Test
    void testArrowCreation() {
        assertNotNull(arrow);
        assertEquals(startPos.getX(), arrow.getPosition().getX());
        assertEquals(startPos.getY(), arrow.getPosition().getY());
        assertEquals(5, arrow.getDamage());
        assertEquals(10, arrow.getSpeed());
        assertEquals(100.0, arrow.getMaxDistance());
        assertSame(target, arrow.getTarget());
    }

    @Test
    void testSetPosition() {
        Position newPos = new Position(150, 200);
        arrow.setPosition(newPos);
        
        assertEquals(150, arrow.getPosition().getX());
        assertEquals(200, arrow.getPosition().getY());
    }

    @Test
    void testSetStartPosition() {
        Position newStart = new Position(50, 50);
        arrow.setStartPosition(newStart);
        
        assertEquals(50, arrow.getStartPosition().getX());
        assertEquals(50, arrow.getStartPosition().getY());
    }

    @Test
    void testGetDistanceTraveled() {
        arrow.setPosition(new Position(110, 100));
        
        double distance = arrow.getDistanceTraveled();
        
        assertEquals(10.0, distance, 0.1);
    }

    @Test
    void testGetDistanceTraveledDiagonal() {
        arrow.setPosition(new Position(110, 110));
        
        double distance = arrow.getDistanceTraveled();
        
        assertEquals(Math.sqrt(200), distance, 0.1);
    }

    @Test
    void testSetDamage() {
        arrow.setDamage(10);
        assertEquals(10, arrow.getDamage());
    }

    @Test
    void testSetSpeed() {
        arrow.setSpeed(20);
        assertEquals(20, arrow.getSpeed());
    }

    @Test
    void testSetMaxDistance() {
        arrow.setMaxDistance(200.0);
        assertEquals(200.0, arrow.getMaxDistance());
    }

    @Test
    void testSetTarget() {
        Enemy newTarget = mock(Enemy.class);
        arrow.setTarget(newTarget);
        assertSame(newTarget, arrow.getTarget());
    }

    @Test
    void testGetHitbox() {
        assertNotNull(arrow.getHitbox());
        assertEquals(6, arrow.getHitbox().width);
        assertEquals(3, arrow.getHitbox().height);
    }

    @Test
    void testGetDistanceTraveledZero() {
        // Arrow hasn't moved
        double distance = arrow.getDistanceTraveled();
        assertEquals(0.0, distance, 0.001);
    }

    @Test
    void testGetDistanceTraveledNegativeMovement() {
        arrow.setPosition(new Position(90, 100));
        double distance = arrow.getDistanceTraveled();
        assertEquals(10.0, distance, 0.1);
    }

    @Test
    void testGetDistanceTraveledAfterStartPositionChange() {
        arrow.setPosition(new Position(110, 100));
        arrow.setStartPosition(new Position(105, 100));
        double distance = arrow.getDistanceTraveled();
        assertEquals(5.0, distance, 0.1);
    }

    @Test
    void testHitboxUpdatesWithPosition() {
        Position newPos = new Position(150, 200);
        arrow.setPosition(newPos);
        
        assertEquals(150, arrow.getHitbox().x);
        assertEquals(200, arrow.getHitbox().y);
    }

    @Test
    void testHitboxUpdatesWithStartPosition() {
        Position newStart = new Position(50, 50);
        arrow.setStartPosition(newStart);
        
        assertEquals(50, arrow.getHitbox().x);
        assertEquals(50, arrow.getHitbox().y);
    }

    @Test
    void testSetTargetToNull() {
        arrow.setTarget(null);
        assertNull(arrow.getTarget());
    }

    @Test
    void testArrowWithZeroSpeed() {
        Arrow zeroSpeedArrow = new Arrow(startPos, 0, 5, 100.0, target);
        assertEquals(0, zeroSpeedArrow.getSpeed());
    }

    @Test
    void testArrowWithZeroDamage() {
        Arrow zeroDamageArrow = new Arrow(startPos, 10, 0, 100.0, target);
        assertEquals(0, zeroDamageArrow.getDamage());
    }

    @Test
    void testArrowWithZeroMaxDistance() {
        Arrow zeroDistanceArrow = new Arrow(startPos, 10, 5, 0.0, target);
        assertEquals(0.0, zeroDistanceArrow.getMaxDistance());
    }
}

