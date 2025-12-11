package com.gladiator.model.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class HealthTest {
    private Health health;

    @BeforeEach
    void setUp() {
        health = new Health(100);
    }

    @Test
    void testInitialHealth() {
        assertEquals(100, health.getHealth());
        assertTrue(health.isAlive());
    }

    @Test
    void testTakeDamage() {
        health.takeDamage(30);
        assertEquals(70, health.getHealth());
        assertTrue(health.isAlive());
    }

    @Test
    void testTakeLethalDamage() {
        health.takeDamage(100);
        assertEquals(0, health.getHealth());
        assertFalse(health.isAlive());
    }

    @Test
    void testTakeExcessiveDamage() {
        health.takeDamage(150);
        assertEquals(0, health.getHealth());
        assertFalse(health.isAlive());
    }

    @Test
    void testHeal() {
        health.takeDamage(40);
        health.heal(20);
        assertEquals(80, health.getHealth());
        assertTrue(health.isAlive());
    }

    @Test
    void testHealBeyondMaxHealth() {
        health.heal(20);
        assertEquals(100, health.getHealth());
    }


}
