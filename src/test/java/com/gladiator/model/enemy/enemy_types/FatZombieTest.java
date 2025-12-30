package com.gladiator.model.enemy.enemy_types;

import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.gladiator.Gladiator;
import com.gladiator.model.movement.ChaseMovement;
import com.gladiator.model.movement.MovementStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FatZombieTest {
    private FatZombie fatZombie;
    private MovementStrategy movement;
    private AttackStrategy attack;

    @BeforeEach
    void setUp() {
        Gladiator gladiator = mock(Gladiator.class);
        movement = new ChaseMovement(1.0, gladiator);
        attack = new SwordAttack(12, 30, new java.util.ArrayList<>());
        fatZombie = new FatZombie(100, 100, movement, attack);
    }

    @Test
    void testFatZombieCreation() {
        assertNotNull(fatZombie);
        assertEquals(100, fatZombie.getPosition().getX());
        assertEquals(100, fatZombie.getPosition().getY());
    }

    @Test
    void testFatZombieStats() {
        assertEquals(40, fatZombie.getHealth().getHealth());
        assertEquals(1, fatZombie.getSpeed());
        assertEquals(23, fatZombie.getHitbox().width);
        assertEquals(23, fatZombie.getHitbox().height);
    }

    @Test
    void testGetMovementStrategy() {
        assertSame(movement, fatZombie.getMovementStrategy());
    }

    @Test
    void testGetAttackStrategy() {
        assertSame(attack, fatZombie.getAttackStrategy());
    }

    @Test
    void testFatZombieIsAlive() {
        assertTrue(fatZombie.isAlive());
    }

    @Test
    void testFatZombieTakesDamage() {
        fatZombie.takeDamage(20);
        
        assertEquals(20, fatZombie.getHealth().getHealth());
        assertTrue(fatZombie.isAlive());
    }

    @Test
    void testFatZombieDies() {
        fatZombie.takeDamage(40);
        
        assertFalse(fatZombie.isAlive());
    }

    @Test
    void testResetHealth() {
        fatZombie.takeDamage(30);
        fatZombie.resetHealth();
        
        assertEquals(40, fatZombie.getHealth().getHealth());
    }

    @Test
    void testReset() {
        fatZombie.takeDamage(30);
        fatZombie.reset();
        
        assertEquals(40, fatZombie.getHealth().getHealth());
    }

    @Test
    void testResetAndResetHealthAreSame() {
        fatZombie.takeDamage(20);
        int healthAfterDamage = fatZombie.getHealth().getHealth();
        
        fatZombie.reset();
        int healthAfterReset = fatZombie.getHealth().getHealth();
        
        fatZombie.takeDamage(20);
        fatZombie.resetHealth();
        int healthAfterResetHealth = fatZombie.getHealth().getHealth();
        
        assertEquals(healthAfterReset, healthAfterResetHealth);
    }

    @Test
    void testSetMaxHealth() {
        fatZombie.setMaxHealth(50);
        fatZombie.resetHealth();
        
        assertEquals(50, fatZombie.getHealth().getHealth());
    }

    @Test
    void testTakeExcessiveDamage() {
        fatZombie.takeDamage(100);
        
        assertEquals(0, fatZombie.getHealth().getHealth());
        assertFalse(fatZombie.isAlive());
    }

    @Test
    void testGetMaxHealth() {
        assertEquals(40, fatZombie.getHealth().getMaxHealth());
    }

    @Test
    void testSetMaxHealthAndReset() {
        fatZombie.setMaxHealth(50);
        fatZombie.takeDamage(20);
        fatZombie.resetHealth();
        
        assertEquals(50, fatZombie.getHealth().getHealth());
    }
}

