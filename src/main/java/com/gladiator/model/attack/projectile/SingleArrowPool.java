package com.gladiator.model.attack.projectile;

import com.gladiator.model.component.Hitbox;
import com.gladiator.model.component.Position;
import com.gladiator.model.entity.MovingEntity;

import java.util.List;

public class SingleArrowPool {
    private Projectile arrow;
    private final Hitbox hitbox;
    private boolean isArrowActive;

    public SingleArrowPool() {
        this.hitbox = new Hitbox(8, 8);
        this.isArrowActive = false;

        Position dummyPos = new Position(0, 0);
        this.arrow = new Projectile(dummyPos, dummyPos, 0, 0, 0, List.of(), hitbox);
    }

    public Projectile getArrow(Position start, Position target, int speed, int damage,
                               double maxDistance, List<? extends MovingEntity> targets) {
        if (isArrowActive) {
            return null;
        }

        arrow.configure(start, target, speed, damage, maxDistance, targets);
        isArrowActive = true;

        return arrow;
    }

    public void returnArrow() {
        isArrowActive = false;
        arrow.setAlive(false);
    }

    public boolean isArrowActive() {
        return isArrowActive;
    }

    public Projectile getActiveArrow() {
        return isArrowActive ? arrow : null;
    }
}