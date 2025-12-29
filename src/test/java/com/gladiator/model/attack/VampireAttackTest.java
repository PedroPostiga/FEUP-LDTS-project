package com.gladiator.model.attack;

import com.gladiator.model.component.Health;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.enemy_types.Vampire;
import com.gladiator.model.gladiator.Gladiator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Rectangle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class VampireAttackTest {
    private VampireAttack vampireAttack;
    private Vampire vampire;
    private Gladiator gladiator;
    private Health vampireHealth;

    @BeforeEach
    void setUp() {
        gladiator = mock(Gladiator.class);
        vampire = mock(Vampire.class);

        Rectangle gladiatorHitbox = new Rectangle(5, 0, 20, 20);
        Rectangle vampireHitbox = new Rectangle(0, 0, 16, 16);

        when(gladiator.getHitbox()).thenReturn(gladiatorHitbox);
        when(gladiator.isAlive()).thenReturn(true);
        when(vampire.getHitbox()).thenReturn(vampireHitbox);

        vampireHealth = new Health(80);
        vampireHealth.takeDamage(30);
        when(vampire.getHealth()).thenReturn(vampireHealth);

        vampireAttack = new VampireAttack(15, 10, gladiator, 0.3);
    }

    @Test
    void testVampireAttackHitsAndHeals() {
        vampireAttack.attack(vampire);

        verify(gladiator).takeDamage(15);
        assertEquals(54, vampireHealth.getHealth());
    }

    @Test
    void testVampireAttackOutOfRange() {
        Rectangle farGladiatorHitbox = new Rectangle(20, 0, 20, 20);
        when(gladiator.getHitbox()).thenReturn(farGladiatorHitbox);

        vampireAttack.attack(vampire);

        verify(gladiator, never()).takeDamage(anyInt());
        assertEquals(50, vampireHealth.getHealth());
    }

    @Test
    void testVampireAttackOnDeadGladiator() {
        when(gladiator.isAlive()).thenReturn(false);

        vampireAttack.attack(vampire);

        verify(gladiator, never()).takeDamage(anyInt());
        assertEquals(50, vampireHealth.getHealth());
    }

    @Test
    void testVampireAttackWithFullHealth() {
        Health fullHealth = new Health(80);
        when(vampire.getHealth()).thenReturn(fullHealth);

        vampireAttack.attack(vampire);

        verify(gladiator).takeDamage(15);
        assertEquals(80, fullHealth.getHealth());
    }

    @Test
    void testVampireAttackHealingDoesNotExceedMaxHealth() {
        Health nearMaxHealth = new Health(80);
        nearMaxHealth.takeDamage(2);
        when(vampire.getHealth()).thenReturn(nearMaxHealth);

        vampireAttack.attack(vampire);

        verify(gladiator).takeDamage(15);
        assertEquals(80, nearMaxHealth.getHealth());
    }

    @Test
    void testVampireAttackWithExactHealingCalculation() {
        Health preciseHealth = new Health(100);
        preciseHealth.takeDamage(40);
        when(vampire.getHealth()).thenReturn(preciseHealth);

        VampireAttack preciseAttack = new VampireAttack(20, 10, gladiator, 0.25);

        preciseAttack.attack(vampire);

        verify(gladiator).takeDamage(20);
        assertEquals(65, preciseHealth.getHealth());
    }

    @Test
    void testVampireAttackWithCooldown() {
        vampireAttack.attack(vampire);
        verify(gladiator).takeDamage(15);
        
        reset(gladiator);
        when(gladiator.getHitbox()).thenReturn(new Rectangle(5, 0, 20, 20));
        when(gladiator.isAlive()).thenReturn(true);
        
        vampireAttack.attack(vampire);
        verify(gladiator, never()).takeDamage(anyInt());
    }
}
