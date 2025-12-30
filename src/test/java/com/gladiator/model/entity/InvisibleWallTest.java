package com.gladiator.model.entity;

import com.gladiator.model.component.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvisibleWallTest {
    private InvisibleWall wall;

    @BeforeEach
    void setUp() {
        wall = new InvisibleWall(0, 0, 10, 20);
    }

    @Test
    void testInvisibleWallCreation() {
        assertNotNull(wall);
        assertEquals(0, wall.getPosition().getX());
        assertEquals(0, wall.getPosition().getY());
    }

    @Test
    void testInvisibleWallHitbox() {
        assertNotNull(wall.getHitbox());
        assertEquals(10, wall.getHitbox().width);
        assertEquals(20, wall.getHitbox().height);
    }

    @Test
    void testInvisibleWallWithDifferentDimensions() {
        InvisibleWall largeWall = new InvisibleWall(100, 200, 50, 30);
        assertEquals(100, largeWall.getPosition().getX());
        assertEquals(200, largeWall.getPosition().getY());
        assertEquals(50, largeWall.getHitbox().width);
        assertEquals(30, largeWall.getHitbox().height);
    }

    @Test
    void testInvisibleWallWithNegativeCoordinates() {
        InvisibleWall negWall = new InvisibleWall(-10, -20, 5, 5);
        assertEquals(-10, negWall.getPosition().getX());
        assertEquals(-20, negWall.getPosition().getY());
    }
}

