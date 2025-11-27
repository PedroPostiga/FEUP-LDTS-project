package com.gladiator.model.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionTest {
    private Position position;

    @BeforeEach
    void setUp() {
        position = new Position(10, 20);
    }

    @Test
    void testInitialPosition() {
        assertEquals(10, position.getX());
        assertEquals(20, position.getY());
    }

    @Test
    void testSetX() {
        position.setX(15);
        assertEquals(15, position.getX());
        assertEquals(20, position.getY());
    }

    @Test
    void testSetY() {
        position.setY(25);
        assertEquals(10, position.getX());
        assertEquals(25, position.getY());
    }

    @Test
    void testSetBothCoordinates() {
        position.setX(30);
        position.setY(40);
        assertEquals(30, position.getX());
        assertEquals(40, position.getY());
    }
}
