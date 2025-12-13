package com.gladiator.model.attack.projectile;

import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;

import java.awt.*;

public class Projectile {

    private Position position;
    private int damage;
    private int speed;
    private double maxDistance;

    private Rectangle hitbox;
    private Enemy target;


    public Projectile(Position start, int speed, int damage,
                      double maxDistance, Enemy target) {
        this.position = new Position(start.getX(), start.getY());
        this.damage = damage;
        this.maxDistance = maxDistance;
        this.target = target;
        this.speed = speed;
        this.hitbox = new Rectangle(start.getX(), start.getY(),8, 8);
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

    public Rectangle getHitbox() {
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

    public Enemy getTarget() {
        return target;
    }
}
