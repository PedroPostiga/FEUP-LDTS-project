package com.gladiator.model.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testGetLeft() {
        Position left = position.getLeft();
        assertEquals(9, left.getX());
        assertEquals(20, left.getY());
    }

    @Test
    void testGetRight() {
        Position right = position.getRight();
        assertEquals(11, right.getX());
        assertEquals(20, right.getY());
    }

    @Test
    void testGetUp() {
        Position up = position.getUp();
        assertEquals(10, up.getX());
        assertEquals(19, up.getY());
    }

    @Test
    void testGetDown() {
        Position down = position.getDown();
        assertEquals(10, down.getX());
        assertEquals(21, down.getY());
    }

    @Test
    void testEqualsSameObject() {
        assertTrue(position.equals(position));
    }

    @Test
    void testEqualsDifferentObjectSameValues() {
        Position other = new Position(10, 20);
        assertTrue(position.equals(other));
    }

    @Test
    void testEqualsDifferentValues() {
        Position other = new Position(15, 25);
        assertFalse(position.equals(other));
    }

    @Test
    void testEqualsNull() {
        assertFalse(position.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(position.equals("not a position"));
    }

    @Test
    void testEqualsDifferentX() {
        Position other = new Position(15, 20);
        assertFalse(position.equals(other));
    }

    @Test
    void testEqualsDifferentY() {
        Position other = new Position(10, 25);
        assertFalse(position.equals(other));
    }

    @Test
    void testNegativeCoordinates() {
        Position negPos = new Position(-10, -20);
        assertEquals(-10, negPos.getX());
        assertEquals(-20, negPos.getY());
    }

    @Test
    void testZeroCoordinates() {
        Position zeroPos = new Position(0, 0);
        assertEquals(0, zeroPos.getX());
        assertEquals(0, zeroPos.getY());
    }

    @Test
    void testLargeCoordinates() {
        Position largePos = new Position(1000, 2000);
        assertEquals(1000, largePos.getX());
        assertEquals(2000, largePos.getY());
    }
}
