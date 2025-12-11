package com.gladiator.model.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HitboxTest {
    private Hitbox hitbox;
    private Position position;

    @BeforeEach
    void setUp() {
        hitbox = new Hitbox(50, 30);
        position = new Position(100, 200);
    }

    @Test
    void testGetBounds() {
        Rectangle bounds = hitbox.getBounds(position);
        assertEquals(100, bounds.x);
        assertEquals(200, bounds.y);
        assertEquals(50, bounds.width);
        assertEquals(30, bounds.height);
    }

    @Test
    void testGetBoundsWithDifferentPosition() {
        Position newPosition = new Position(150, 250);
        Rectangle bounds = hitbox.getBounds(newPosition);
        assertEquals(150, bounds.x);
        assertEquals(250, bounds.y);
    }
}
