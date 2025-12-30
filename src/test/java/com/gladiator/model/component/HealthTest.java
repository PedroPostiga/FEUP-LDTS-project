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

    @Test
    void testGetMaxHealth() {
        assertEquals(100, health.getMaxHealth());
    }

    @Test
    void testTakeDamageEqualToHealth() {
        health.takeDamage(100);
        assertEquals(0, health.getHealth());
        assertFalse(health.isAlive());
    }

    @Test
    void testTakeDamageLessThanHealth() {
        health.takeDamage(50);
        assertEquals(50, health.getHealth());
        assertTrue(health.isAlive());
    }

    @Test
    void testTakeZeroDamage() {
        health.takeDamage(0);
        assertEquals(100, health.getHealth());
        assertTrue(health.isAlive());
    }

    @Test
    void testHealZero() {
        health.takeDamage(50);
        health.heal(0);
        assertEquals(50, health.getHealth());
    }

    @Test
    void testHealToMaxHealth() {
        health.takeDamage(30);
        health.heal(30);
        assertEquals(100, health.getHealth());
    }

    @Test
    void testHealPartial() {
        health.takeDamage(60);
        health.heal(20);
        assertEquals(60, health.getHealth());
    }

    @Test
    void testIsAliveWithOneHealth() {
        health = new Health(1);
        assertTrue(health.isAlive());
        health.takeDamage(1);
        assertFalse(health.isAlive());
    }

    @Test
    void testMultipleDamageAndHeal() {
        health.takeDamage(30);
        health.takeDamage(20);
        health.heal(10);
        assertEquals(60, health.getHealth());
    }

    @Test
    void testHealthWithZeroInitial() {
        health = new Health(0);
        assertEquals(0, health.getHealth());
        assertFalse(health.isAlive());
        assertEquals(0, health.getMaxHealth());
    }

    @Test
    void testMaxHealthPreserved() {
        health.takeDamage(50);
        assertEquals(100, health.getMaxHealth());
        health.takeDamage(50);
        assertEquals(100, health.getMaxHealth());
    }
}
