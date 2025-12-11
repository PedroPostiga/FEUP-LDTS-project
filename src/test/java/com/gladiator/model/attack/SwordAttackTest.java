package com.gladiator.model.attack;

import com.gladiator.model.component.Position;
import com.gladiator.model.entity.MovingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        when(attacker.getPosition()).thenReturn(new Position(0, 0));

        when(target1.getPosition()).thenReturn(new Position(5, 0));
        when(target2.getPosition()).thenReturn(new Position(50, 0));

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

        verify(target1, never()).takeDamage(10);
        verify(target2, never()).takeDamage(10);
    }

    @Test
    void testAttackIgnoresAttackerItself() {
        targets.add(attacker);
        when(attacker.isAlive()).thenReturn(true);
        when(attacker.getPosition()).thenReturn(new Position(0, 0));

        swordAttack.attack(attacker);

        verify(attacker, never()).takeDamage(10);
    }
}
