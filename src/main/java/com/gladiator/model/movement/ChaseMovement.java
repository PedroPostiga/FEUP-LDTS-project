package com.gladiator.model.movement;

import com.gladiator.model.component.Position;
import com.gladiator.model.entity.MovingEntity;
import com.gladiator.model.gladiator.Gladiator;

public class ChaseMovement implements MovementStrategy {
    private final double speed;
    private final Gladiator gladiator;

    public ChaseMovement(double speed, Gladiator gladiator) {
        this.speed = speed;
        this.gladiator = gladiator;
    }

    @Override
    public void move(MovingEntity entity) {
        Position targetPos = gladiator.getPosition();
        Position pos = entity.getPosition();

        double dx = targetPos.getX() - pos.getX();
        double dy = targetPos.getY() - pos.getY();
        double dist = Math.sqrt(dx * dx + dy * dy);
        if (dist == 0) return;

        pos.setX((int) (pos.getX() + (dx / dist) * speed));
        pos.setY((int) (pos.getY() + (dy / dist) * speed));
    }
}
