package com.gladiator.model.movement;

import com.gladiator.model.component.Position;
import com.gladiator.model.entity.MovingEntity;

public class WanderMovement implements MovementStrategy {
    private double angle;

    public WanderMovement() {
        this.angle = Math.random() * 2 * Math.PI;
    }

    @Override
    public void move(MovingEntity entity) {
        Position pos = entity.getPosition();

        pos.setX(pos.getX() + entity.getSpeed() * Math.cos(angle));
        pos.setY(pos.getY() + entity.getSpeed() * Math.sin(angle));

        angle += (Math.random() - 0.5) * 0.1;
    }
}
