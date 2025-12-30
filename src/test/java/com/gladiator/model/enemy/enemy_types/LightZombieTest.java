package com.gladiator.model.enemy.enemy_types;

import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.movement.MovementStrategy;
import com.gladiator.model.movement.WanderMovement;
import com.gladiator.model.gladiator.Gladiator;
import com.gladiator.model.movement.ChaseMovement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class LightZombieTest {
    private LightZombie lightZombie;
    private MovementStrategy movement;
    private AttackStrategy attack;

    @BeforeEach
    void setUp() {
        Gladiator gladiator = mock(Gladiator.class);
        movement = new WanderMovement();
        attack = new SwordAttack(8, 30, new java.util.ArrayList<>());
        lightZombie = new LightZombie(100, 100, movement, attack);
    }

    @Test
    void testLightZombieCreation() {
        assertNotNull(lightZombie);
        assertEquals(100, lightZombie.getPosition().getX());
        assertEquals(100, lightZombie.getPosition().getY());
    }

    @Test
    void testLightZombieStats() {
        assertEquals(20, lightZombie.getHealth().getHealth());
        assertEquals(3, lightZombie.getSpeed());
        assertEquals(16, lightZombie.getHitbox().width);
        assertEquals(16, lightZombie.getHitbox().height);
    }

    @Test
    void testGetMovementStrategy() {
        assertSame(movement, lightZombie.getMovementStrategy());
    }

    @Test
    void testGetAttackStrategy() {
        assertSame(attack, lightZombie.getAttackStrategy());
    }

    @Test
    void testLightZombieIsAlive() {
        assertTrue(lightZombie.isAlive());
    }

    @Test
    void testLightZombieTakesDamage() {
        lightZombie.takeDamage(10);
        
        assertEquals(10, lightZombie.getHealth().getHealth());
        assertTrue(lightZombie.isAlive());
    }

    @Test
    void testLightZombieDies() {
        lightZombie.takeDamage(20);
        
        assertFalse(lightZombie.isAlive());
    }

    @Test
    void testResetHealth() {
        lightZombie.takeDamage(15);
        lightZombie.resetHealth();
        
        assertEquals(20, lightZombie.getHealth().getHealth());
    }

    @Test
    void testReset() {
        lightZombie.takeDamage(15);
        lightZombie.reset();
        
        assertEquals(20, lightZombie.getHealth().getHealth());
    }

    @Test
    void testSetMaxHealth() {
        lightZombie.setMaxHealth(30);
        lightZombie.resetHealth();
        
        assertEquals(30, lightZombie.getHealth().getHealth());
    }

    @Test
    void testTakeExcessiveDamage() {
        lightZombie.takeDamage(100);
        
        assertEquals(0, lightZombie.getHealth().getHealth());
        assertFalse(lightZombie.isAlive());
    }

    @Test
    void testGetMaxHealth() {
        assertEquals(20, lightZombie.getHealth().getMaxHealth());
    }

    @Test
    void testSetMaxHealthAndReset() {
        lightZombie.setMaxHealth(30);
        lightZombie.takeDamage(10);
        lightZombie.resetHealth();
        
        assertEquals(30, lightZombie.getHealth().getHealth());
    }
}

