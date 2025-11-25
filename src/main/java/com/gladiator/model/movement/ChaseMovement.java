package com.gladiator.model.movement;

import com.gladiator.model.component.Position;
import com.gladiator.model.entity.MovingEntity;
import com.gladiator.model.gladiator.GladiatorModel;

public class ChaseMovement implements MovementStrategy {
    private final double speed;
    private final GladiatorModel gladiator;

    public ChaseMovement(double speed, GladiatorModel gladiator) {
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

        pos.setX(pos.getX() + (dx / dist) * speed);
        pos.setY(pos.getY() + (dy / dist) * speed);
    }
}
