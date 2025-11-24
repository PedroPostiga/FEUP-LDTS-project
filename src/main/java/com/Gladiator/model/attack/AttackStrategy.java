package com.Gladiator.model.attack;

import com.Gladiator.model.entity.MovingEntity;

public interface AttackStrategy {
    void attack(MovingEntity attacker);
}
