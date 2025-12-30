package com.gladiator.model.attack;

import com.gladiator.model.entity.MovingEntity;

public interface AttackStrategy {
    void attack(MovingEntity attacker);

    default boolean isAttacking(MovingEntity attacker) {
        return false;
    }
}
