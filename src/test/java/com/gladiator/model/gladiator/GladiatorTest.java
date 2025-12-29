package com.gladiator.model.gladiator;

import com.gladiator.model.attack.BowAttack;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.component.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class GladiatorTest {
    private Gladiator gladiator;

    @BeforeEach
    void setUp() throws Exception {
        resetGladiatorInstance();
        gladiator = Gladiator.getInstance(100, 100, 20, 20, 100, 5);
    }

    private void resetGladiatorInstance() throws Exception {
        Field instanceField = Gladiator.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
    }

    @Test
    void testGetInstance() {
        assertNotNull(gladiator);
        assertEquals(100, gladiator.getPosition().getX());
        assertEquals(100, gladiator.getPosition().getY());
    }

    @Test
    void testGetInstanceWithoutInitialization() throws Exception {
        resetGladiatorInstance();
        
        assertThrows(IllegalStateException.class, () -> Gladiator.getInstance());
    }

    @Test
    void testGetInstanceSingleton() throws Exception {
        resetGladiatorInstance();
        
        Gladiator g1 = Gladiator.getInstance(50, 50, 20, 20, 100, 5);
        Gladiator g2 = Gladiator.getInstance(200, 200, 20, 20, 100, 5);
        
        assertSame(g1, g2);
    }

    @Test
    void testGetSetSwordAttack() {
        SwordAttack swordAttack = new SwordAttack(10, 30, new java.util.ArrayList<>());
        
        gladiator.setSwordAttack(swordAttack);
        
        assertSame(swordAttack, gladiator.getSwordAttack());
    }

    @Test
    void testGetSetBowAttack() {
        BowAttack bowAttack = new BowAttack(5, 10, 100.0, new java.util.ArrayList<>());
        
        gladiator.setBowAttack(bowAttack);
        
        assertSame(bowAttack, gladiator.getBowAttack());
    }

    @Test
    void testGetSetDirection() {
        assertEquals(Gladiator.Direction.DOWN, gladiator.getDirection());
        
        gladiator.setDirection(Gladiator.Direction.LEFT);
        assertEquals(Gladiator.Direction.LEFT, gladiator.getDirection());
        
        gladiator.setDirection(Gladiator.Direction.UP);
        assertEquals(Gladiator.Direction.UP, gladiator.getDirection());
        
        gladiator.setDirection(Gladiator.Direction.RIGHT);
        assertEquals(Gladiator.Direction.RIGHT, gladiator.getDirection());
    }

    @Test
    void testSetPosition() {
        gladiator.setPosition(new Position(150, 200));
        
        assertEquals(150, gladiator.getPosition().getX());
        assertEquals(200, gladiator.getPosition().getY());
    }

    @Test
    void testInheritedProperties() {
        assertEquals(5, gladiator.getSpeed());
        assertTrue(gladiator.isAlive());
    }
}

