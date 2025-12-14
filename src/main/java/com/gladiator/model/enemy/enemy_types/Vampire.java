package com.gladiator.model.enemy.enemy_types;

import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.movement.MovementStrategy;

public class Vampire extends Enemy {

    public Vampire(int x, int y, MovementStrategy movement, AttackStrategy attack) {
        super(x, y, 16, 16, 20, 3, movement, attack);
    }

    public MovementStrategy getMovementStrategy() {
        return movement;
    }

    public AttackStrategy getAttackStrategy() {
        return attack;
    }
}
