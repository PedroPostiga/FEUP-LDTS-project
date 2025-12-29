package com.gladiator.model.attack;

import com.gladiator.model.entity.MovingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

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
}
