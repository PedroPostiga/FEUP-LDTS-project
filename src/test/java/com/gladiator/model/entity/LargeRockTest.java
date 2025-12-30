package com.gladiator.model.entity;

import com.gladiator.model.component.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LargeRockTest {
    private LargeRock largeRock;

    @BeforeEach
    void setUp() {
        largeRock = new LargeRock(100, 100);
    }

    @Test
    void testLargeRockCreation() {
        assertNotNull(largeRock);
        assertEquals(100, largeRock.getPosition().getX());
        assertEquals(100, largeRock.getPosition().getY());
    }

    @Test
    void testLargeRockHitbox() {
        assertNotNull(largeRock.getHitbox());
        assertEquals(21, largeRock.getHitbox().width);
        assertEquals(11, largeRock.getHitbox().height);
    }

    @Test
    void testLargeRockWithDifferentPositions() {
        LargeRock rock1 = new LargeRock(0, 0);
        LargeRock rock2 = new LargeRock(200, 300);
        
        assertEquals(0, rock1.getPosition().getX());
        assertEquals(0, rock1.getPosition().getY());
        assertEquals(200, rock2.getPosition().getX());
        assertEquals(300, rock2.getPosition().getY());
    }
}

