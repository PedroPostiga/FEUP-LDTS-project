package com.gladiator.model.enemy.enemy_types;

import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.movement.MovementStrategy;

public class LightZombie extends Enemy {

    private final double attackSpeedMultiplier;

    public LightZombie(int x, int y, MovementStrategy movement, AttackStrategy attack) {
        super(x, y, 30, 30, 50, 5, movement, attack);
        attackSpeedMultiplier = 1.5;
    }

    public double getAttackSpeedMultiplier() {
        return attackSpeedMultiplier;
    }

    public MovementStrategy getMovementStrategy() {
        return movement;
    }

    public AttackStrategy getAttackStrategy() {
        return attack;
    }
}
