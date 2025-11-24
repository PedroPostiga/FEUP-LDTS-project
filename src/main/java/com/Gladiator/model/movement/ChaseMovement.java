package com.Gladiator.model.movement;

import com.Gladiator.model.component.Position;
import com.Gladiator.model.entity.MovingEntity;
import com.gladiator.model.GladiatorModel;

public class ChaseMovement implements MovementStrategy {
    private final double speed;
    private final GladiatorModel gladiator;

    public ChaseMovement(double speed, GladiatorModel gladiator) {
        this.speed = speed;
        this.gladiator = gladiator;
    }

    @Override
    public void move(MovingEntity entity) {
        Position targetPos = gladiator.getPos();
        Position pos = entity.getPosition();

        double dx = targetPos.getX() - pos.getX();
        double dy = targetPos.getY() - pos.getY();
        double dist = Math.sqrt(dx * dx + dy * dy);
        if (dist == 0) return;

        pos.setX(pos.getX() + (dx / dist) * speed);
        pos.setY(pos.getY() + (dy / dist) * speed);
    }
}
