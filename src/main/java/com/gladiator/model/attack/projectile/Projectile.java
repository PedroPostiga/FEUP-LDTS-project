package com.gladiator.model.attack.projectile;

import com.gladiator.model.component.Hitbox;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;

public class Projectile {

    private Position position;
    private int damage;
    private int speed;
    private double maxDistance;

    private final Hitbox hitbox;
    private Enemy target;

    private boolean active = true;

    public Projectile(Position start, int speed, int damage,
                      double maxDistance, Enemy target) {
        this.position = new Position(start.getX(), start.getY());
        this.damage = damage;
        this.maxDistance = maxDistance;
        this.target = target;
        this.speed = speed;
        this.hitbox = new Hitbox(8, 8);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public void setTarget(Enemy target) {
        this.target = target;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public Position getPosition() {
        return position;
    }

    public int getDamage() {
        return damage;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public int getSpeed() {
        return speed;
    }
}
