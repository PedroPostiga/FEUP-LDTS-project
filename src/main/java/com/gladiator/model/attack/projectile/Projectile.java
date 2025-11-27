package com.gladiator.model.attack.projectile;

import com.gladiator.model.component.Hitbox;
import com.gladiator.model.component.Position;
import com.gladiator.model.entity.MovingEntity;

import java.awt.*;
import java.util.List;

public class Projectile {

    private Position position;
    private final Position startPosition;
    private final double vx;
    private final double vy;
    private final int damage;
    private final double maxDistance;

    private final Hitbox hitbox;
    private final List<? extends MovingEntity> targets;

    private boolean alive = true;

    public Projectile(Position start, Position target, int speed, int damage, double maxDistance, List<? extends MovingEntity> targets, Hitbox hitbox) {
        this.position = new Position(start.getX(), start.getY());
        this.startPosition = new Position(start.getX(), start.getY());
        this.damage = damage;
        this.maxDistance = maxDistance;
        this.targets = targets;
        this.hitbox = hitbox;

        double dx = target.getX() - start.getX();
        double dy = target.getY() - start.getY();
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist == 0) dist = 1;

        this.vx = (dx / dist) * speed;
        this.vy = (dy / dist) * speed;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public Position getPosition() {
        return position;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public int getDamage() {
        return damage;
    }

    public double getMaxDistance() {
        return maxDistance;
    }
}
