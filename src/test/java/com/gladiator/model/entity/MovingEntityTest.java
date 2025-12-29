package com.gladiator.model.entity;

import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.component.Health;
import com.gladiator.model.component.Position;
import com.gladiator.model.movement.MovementStrategy;
import com.gladiator.model.enemy.enemy_types.FatZombie;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.movement.ChaseMovement;
import com.gladiator.model.gladiator.Gladiator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MovingEntityTest {
    private FatZombie entity;
    private Gladiator gladiator;

    @BeforeEach
    void setUp() {
        gladiator = mock(Gladiator.class);
        entity = new FatZombie(100, 100, 
            new ChaseMovement(1.0, gladiator),
            new SwordAttack(12, 30, new ArrayList<>()));
    }

    @Test
    void testGetSetPosition() {
        Position newPos = new Position(150, 200);
        entity.setPosition(newPos);
        
        assertEquals(150, entity.getPosition().getX());
        assertEquals(200, entity.getPosition().getY());
    }

    @Test
    void testGetSetSpeed() {
        assertEquals(1, entity.getSpeed());
        
        entity.setSpeed(5);
        assertEquals(5, entity.getSpeed());
    }

    @Test
    void testGetSetHealth() {
        Health newHealth = new Health(50);
        entity.setHealth(newHealth);
        
        assertSame(newHealth, entity.getHealth());
    }

    @Test
    void testIsAlive() {
        assertTrue(entity.isAlive());
        
        entity.takeDamage(40);
        assertFalse(entity.isAlive());
    }

    @Test
    void testTakeDamage() {
        int initialHealth = entity.getHealth().getHealth();
        entity.takeDamage(10);
        
        assertEquals(initialHealth - 10, entity.getHealth().getHealth());
    }

    @Test
    void testGetHitbox() {
        assertNotNull(entity.getHitbox());
        assertEquals(23, entity.getHitbox().width);
        assertEquals(23, entity.getHitbox().height);
    }

    @Test
    void testHitboxUpdatesWithPosition() {
        Position newPos = new Position(200, 300);
        entity.setPosition(newPos);
        
        assertEquals(200, entity.getHitbox().x);
        assertEquals(300, entity.getHitbox().y);
    }

    @Test
    void testSetMovement() {
        MovementStrategy newMovement = new ChaseMovement(2.0, gladiator);
        entity.setMovement(newMovement);
        
        assertSame(newMovement, entity.getMovementStrategy());
    }

    @Test
    void testSetAttack() {
        AttackStrategy newAttack = new SwordAttack(15, 40, new ArrayList<>());
        entity.setAttack(newAttack);
        
        assertSame(newAttack, entity.getAttackStrategy());
    }
}

