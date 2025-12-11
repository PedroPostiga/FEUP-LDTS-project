package com.gladiator.model.attack.projectile;

import com.gladiator.model.component.Hitbox;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.entity.MovingEntity;

import java.util.List;

public class SingleArrowPool {
    private Arrow arrow;
    private boolean isArrowActive;

    public SingleArrowPool() {
        this.isArrowActive = false;

        Position dummyPos = new Position(0, 0);
        this.arrow = new Arrow(dummyPos, 0, 0, 0, null);
    }

    public Arrow getArrow(Position start, int speed, int damage,
                               double maxDistance, Enemy target) {
        if (isArrowActive) {
            return null;
        }

        arrow.setDamage(damage);
        arrow.setPosition(start);
        arrow.setMaxDistance(maxDistance);
        arrow.setTarget(target);
        arrow.setSpeed(speed);
        isArrowActive = true;

        return arrow;
    }

    public void returnArrow() {
        isArrowActive = false;
        arrow.setActive(false);
    }

    public boolean isArrowActive() {
        return isArrowActive;
    }

    public Arrow getActiveArrow() {
        return isArrowActive ? arrow : null;
    }
}