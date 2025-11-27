package com.gladiator.model.enemy;

import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.entity.MovingEntity;
import com.gladiator.model.movement.MovementStrategy;

public abstract class Enemy extends MovingEntity {

    public Enemy(int x, int y, int w, int h, int hp, int speed, MovementStrategy movement, AttackStrategy attack){
        super(x, y, w, h, hp, speed);
        setMovement(movement);
        setAttack(attack);
    }

    public MovementStrategy getMovementStrategy() {
        return movement;
    }

    public AttackStrategy getAttackStrategy() {
        return attack;
    }
}
