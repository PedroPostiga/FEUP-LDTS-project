package com.gladiator.model.movement;

import com.gladiator.model.entity.MovingEntity;

public interface MovementStrategy {
    void move(MovingEntity entity);
}
