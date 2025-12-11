package com.gladiator.model.enemy.enemy_types;

import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.movement.MovementStrategy;

public class FatZombie extends Enemy {

    private final double attackDamageMultiplier;

    public FatZombie(int x, int y, MovementStrategy movement, AttackStrategy attack) {
        super(x, y, 50, 50, 120, 2, movement, attack);
        attackDamageMultiplier = 2.0;
    }

    public double getAttackDamageMultiplier() {
        return attackDamageMultiplier;
    }

    public MovementStrategy getMovementStrategy() {
        return movement;
    }

    public AttackStrategy getAttackStrategy() {
        return attack;
    }
}
