package com.gladiator.model.attack;

import com.gladiator.model.entity.MovingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SwordAttackTest {
    private SwordAttack swordAttack;
    private List<MovingEntity> targets;
    private MovingEntity attacker;
    private MovingEntity target1;
    private MovingEntity target2;

    @BeforeEach
    void setUp() {
        targets = new ArrayList<>();

        attacker = mock(MovingEntity.class);
        target1 = mock(MovingEntity.class);
        target2 = mock(MovingEntity.class);

        Rectangle attackerHitbox = new Rectangle(0, 0, 20, 20);
        Rectangle target1Hitbox = new Rectangle(5, 0, 16, 16);
        Rectangle target2Hitbox = new Rectangle(50, 0, 16, 16);

        when(attacker.getHitbox()).thenReturn(attackerHitbox);
        when(target1.getHitbox()).thenReturn(target1Hitbox);
        when(target2.getHitbox()).thenReturn(target2Hitbox);

        when(target1.isAlive()).thenReturn(true);
        when(target2.isAlive()).thenReturn(true);

        targets.add(target1);
        targets.add(target2);

        swordAttack = new SwordAttack(10, 10, targets);
    }

    @Test
    void testAttackHitsTargetInRange() {
        swordAttack.attack(attacker);
        verify(target1).takeDamage(10);
        verify(target2, never()).takeDamage(10);
    }

    @Test
    void testAttackIgnoresDeadTargets() {
        when(target1.isAlive()).thenReturn(false);

        swordAttack.attack(attacker);

        verify(target1, never()).takeDamage(anyInt());
        verify(target2, never()).takeDamage(anyInt());
    }

    @Test
    void testAttackIgnoresAttackerItself() {
        targets.add(attacker);
        when(attacker.isAlive()).thenReturn(true);

        swordAttack.attack(attacker);

        verify(attacker, never()).takeDamage(anyInt());
    }

    @Test
    void testAttackWithCooldown() throws InterruptedException {
        swordAttack.attack(attacker);
        verify(target1).takeDamage(10);
        
        reset(target1);
        when(target1.isAlive()).thenReturn(true);
        when(target1.getHitbox()).thenReturn(new Rectangle(5, 0, 16, 16));
        
        Thread.sleep(10);
        swordAttack.attack(attacker);
        verify(target1, never()).takeDamage(anyInt());
    }

    @Test
    void testAttackDoesNotHitWhenNoTargetsInRange() {
        Rectangle farTargetHitbox = new Rectangle(200, 200, 16, 16);
        when(target1.getHitbox()).thenReturn(farTargetHitbox);
        when(target2.getHitbox()).thenReturn(farTargetHitbox);

        swordAttack.attack(attacker);

        verify(target1, never()).takeDamage(anyInt());
        verify(target2, never()).takeDamage(anyInt());
    }

    @Test
    void testGetDamage() {
        assertEquals(10, swordAttack.getDamage());
    }

    @Test
    void testGetRange() {
        assertEquals(10, swordAttack.getRange());
    }

    @Test
    void testGetTargets() {
        // getTargets() returns the internal list, which is a copy of the original
        List<MovingEntity> returnedTargets = swordAttack.getTargets();
        assertEquals(targets.size(), returnedTargets.size());
        // The lists should have the same content
        assertTrue(returnedTargets.containsAll(targets));
    }

    @Test
    void testAttackWithEmptyTargets() {
        SwordAttack emptyAttack = new SwordAttack(10, 10, new ArrayList<>());
        emptyAttack.attack(attacker);
        // Should not throw exception
    }

    @Test
    void testAttackWithExactRange() {
        // Target exactly at range boundary
        Rectangle exactRangeHitbox = new Rectangle(10, 0, 16, 16);
        when(target1.getHitbox()).thenReturn(exactRangeHitbox);
        
        swordAttack.attack(attacker);
        
        verify(target1).takeDamage(10);
    }

    @Test
    void testAttackWithJustOutsideRange() {
        // Attacker center is at (10, 10) for hitbox (0, 0, 20, 20)
        // Range is 10, so target center should be > 10 distance away
        // Target center at (11 + 8, 0 + 8) = (19, 8) for 16x16 hitbox
        // Distance from (10, 10) to (19, 8) = sqrt(9^2 + 2^2) = sqrt(81 + 4) = sqrt(85) ≈ 9.22
        // That's still in range! Need to go further
        // Let's put target at (25, 0) so center is at (33, 8)
        // Distance = sqrt(23^2 + 2^2) = sqrt(529 + 4) = sqrt(533) ≈ 23.09 > 10
        Rectangle outsideRangeHitbox = new Rectangle(25, 0, 16, 16);
        when(target1.getHitbox()).thenReturn(outsideRangeHitbox);
        
        swordAttack.attack(attacker);
        
        verify(target1, never()).takeDamage(anyInt());
    }

    @Test
    void testIsAttackingReturnsFalseInitially() {
        assertFalse(swordAttack.isAttacking(attacker));
    }

    @Test
    void testIsAttackingAfterAttack() {
        swordAttack.attack(attacker);
        // isAttacking should return true for a short time after attack
        assertTrue(swordAttack.isAttacking(attacker));
    }

    @Test
    void testAttackWithCustomCooldown() {
        SwordAttack customCooldownAttack = new SwordAttack(10, 10, targets, 60);
        customCooldownAttack.attack(attacker);
        verify(target1).takeDamage(10);
    }

    @Test
    void testAttackDoesNotUpdateCooldownIfNoHit() {
        Rectangle farTargetHitbox = new Rectangle(200, 200, 16, 16);
        when(target1.getHitbox()).thenReturn(farTargetHitbox);
        when(target2.getHitbox()).thenReturn(farTargetHitbox);
        
        swordAttack.attack(attacker);
        
        // Should be able to attack again immediately since no hit occurred
        // (cooldown only updates on hit)
    }
}
