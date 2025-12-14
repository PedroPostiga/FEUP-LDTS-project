package com.gladiator.model.enemy.enemy_types;

import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.movement.MovementStrategy;

public class LightZombie extends Enemy {

    public LightZombie(int x, int y, MovementStrategy movement, AttackStrategy attack) {
        super(x, y, 16, 16, 20, 2, movement, attack);
    }

    public MovementStrategy getMovementStrategy() {
        return movement;
    }

    public AttackStrategy getAttackStrategy() {
        return attack;
    }
}
