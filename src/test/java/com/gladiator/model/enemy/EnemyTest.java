package com.gladiator.model.enemy;

import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.component.Health;
import com.gladiator.model.movement.ChaseMovement;
import com.gladiator.model.movement.MovementStrategy;
import com.gladiator.model.gladiator.Gladiator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnemyTest {
    private TestEnemy enemy;
    private MovementStrategy movement;
    private AttackStrategy attack;
    private Gladiator gladiator;

    @BeforeEach
    void setUp() {
        gladiator = mock(Gladiator.class);
        movement = new ChaseMovement(2.0, gladiator);
        attack = new SwordAttack(10, 30, new ArrayList<>());
        enemy = new TestEnemy(100, 100, 20, 20, 50, 3, movement, attack);
    }

    @Test
    void testGetMovementStrategy() {
        assertSame(movement, enemy.getMovementStrategy());
    }

    @Test
    void testGetAttackStrategy() {
        assertSame(attack, enemy.getAttackStrategy());
    }

    @Test
    void testReset() {
        enemy.takeDamage(30);
        assertEquals(20, enemy.getHealth().getHealth());
        
        enemy.reset();
        
        assertEquals(50, enemy.getHealth().getHealth());
        assertTrue(enemy.isAlive());
    }

    @Test
    void testResetHealth() {
        enemy.takeDamage(30);
        assertEquals(20, enemy.getHealth().getHealth());
        
        enemy.resetHealth();
        
        assertEquals(50, enemy.getHealth().getHealth());
        assertTrue(enemy.isAlive());
    }

    @Test
    void testResetAndResetHealthAreEquivalent() {
        enemy.takeDamage(20);
        int healthAfterDamage = enemy.getHealth().getHealth();
        
        enemy.reset();
        int healthAfterReset = enemy.getHealth().getHealth();
        
        enemy.takeDamage(20);
        enemy.resetHealth();
        int healthAfterResetHealth = enemy.getHealth().getHealth();
        
        assertEquals(healthAfterReset, healthAfterResetHealth);
    }

    @Test
    void testSetMaxHealth() {
        enemy.setMaxHealth(100);
        enemy.resetHealth();
        
        assertEquals(100, enemy.getHealth().getHealth());
        assertEquals(100, enemy.getHealth().getMaxHealth());
    }

    @Test
    void testSetMaxHealthAndReset() {
        enemy.takeDamage(20);
        enemy.setMaxHealth(80);
        enemy.resetHealth();
        
        assertEquals(80, enemy.getHealth().getHealth());
    }

    @Test
    void testMaxHealthPreservedAfterDamage() {
        int originalMaxHealth = enemy.getHealth().getMaxHealth();
        enemy.takeDamage(30);
        
        assertEquals(originalMaxHealth, enemy.getHealth().getMaxHealth());
    }

    @Test
    void testResetWithZeroMaxHealth() {
        enemy.setMaxHealth(0);
        enemy.resetHealth();
        
        assertEquals(0, enemy.getHealth().getHealth());
        assertFalse(enemy.isAlive());
    }

    @Test
    void testResetMultipleTimes() {
        enemy.takeDamage(20);
        enemy.reset();
        assertEquals(50, enemy.getHealth().getHealth());
        
        enemy.takeDamage(30);
        enemy.reset();
        assertEquals(50, enemy.getHealth().getHealth());
        
        enemy.takeDamage(50);
        enemy.reset();
        assertEquals(50, enemy.getHealth().getHealth());
    }

    // Test implementation of abstract Enemy
    private static class TestEnemy extends Enemy {
        public TestEnemy(int x, int y, int w, int h, int hp, int speed, 
                        MovementStrategy movement, AttackStrategy attack) {
            super(x, y, w, h, hp, speed, movement, attack);
        }
    }
}

