package com.gladiator.model.entity;

import com.gladiator.model.component.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmallRockTest {
    private SmallRock smallRock;

    @BeforeEach
    void setUp() {
        smallRock = new SmallRock(100, 100);
    }

    @Test
    void testSmallRockCreation() {
        assertNotNull(smallRock);
        assertEquals(100, smallRock.getPosition().getX());
        assertEquals(100, smallRock.getPosition().getY());
    }

    @Test
    void testSmallRockHitbox() {
        assertNotNull(smallRock.getHitbox());
        assertEquals(11, smallRock.getHitbox().width);
        assertEquals(11, smallRock.getHitbox().height);
    }

    @Test
    void testSmallRockWithDifferentPositions() {
        SmallRock rock1 = new SmallRock(0, 0);
        SmallRock rock2 = new SmallRock(200, 300);
        
        assertEquals(0, rock1.getPosition().getX());
        assertEquals(0, rock1.getPosition().getY());
        assertEquals(200, rock2.getPosition().getX());
        assertEquals(300, rock2.getPosition().getY());
    }
}

