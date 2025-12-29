package com.gladiator.model.enemy.enemy_types;

import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.attack.VampireAttack;
import com.gladiator.model.gladiator.Gladiator;
import com.gladiator.model.movement.ChaseMovement;
import com.gladiator.model.movement.MovementStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class VampireTest {
    private Vampire vampire;
    private MovementStrategy movement;
    private AttackStrategy attack;

    @BeforeEach
    void setUp() {
        Gladiator gladiator = mock(Gladiator.class);
        movement = new ChaseMovement(2.0, gladiator);
        attack = new VampireAttack(15, 30, gladiator, 0.3);
        vampire = new Vampire(100, 100, movement, attack);
    }

    @Test
    void testVampireCreation() {
        assertNotNull(vampire);
        assertEquals(100, vampire.getPosition().getX());
        assertEquals(100, vampire.getPosition().getY());
    }

    @Test
    void testVampireStats() {
        assertEquals(30, vampire.getHealth().getHealth());
        assertEquals(2, vampire.getSpeed());
        assertEquals(16, vampire.getHitbox().width);
        assertEquals(16, vampire.getHitbox().height);
    }

    @Test
    void testGetMovementStrategy() {
        assertSame(movement, vampire.getMovementStrategy());
    }

    @Test
    void testGetAttackStrategy() {
        assertSame(attack, vampire.getAttackStrategy());
    }

    @Test
    void testVampireIsAlive() {
        assertTrue(vampire.isAlive());
    }

    @Test
    void testVampireTakesDamage() {
        vampire.takeDamage(15);
        
        assertEquals(15, vampire.getHealth().getHealth());
        assertTrue(vampire.isAlive());
    }

    @Test
    void testVampireDies() {
        vampire.takeDamage(30);
        
        assertFalse(vampire.isAlive());
    }

    @Test
    void testResetHealth() {
        vampire.takeDamage(20);
        vampire.resetHealth();
        
        assertEquals(30, vampire.getHealth().getHealth());
    }
}

