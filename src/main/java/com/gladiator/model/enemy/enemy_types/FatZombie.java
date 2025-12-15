package com.gladiator.model.enemy.enemy_types;

import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.movement.MovementStrategy;

public class FatZombie extends Enemy {

    public FatZombie(int x, int y, MovementStrategy movement, AttackStrategy attack) {
        super(x, y, 23, 23, 40, 1, movement, attack);
    }

    public MovementStrategy getMovementStrategy() {
        return movement;
    }

    public AttackStrategy getAttackStrategy() {
        return attack;
    }
}
