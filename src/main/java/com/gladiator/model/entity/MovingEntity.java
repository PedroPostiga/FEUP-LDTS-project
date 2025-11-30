package com.gladiator.model.entity;

import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.component.Health;
import com.gladiator.model.component.Hitbox;
import com.gladiator.model.component.Position;
import com.gladiator.model.movement.MovementStrategy;

import java.awt.*;

public abstract class MovingEntity implements Entity {
    protected Position position;
    protected final Hitbox hitbox;
    protected Health health;
    protected int speed;

    protected MovementStrategy movement;
    protected AttackStrategy attack;

    protected MovingEntity(int x, int y, int w, int h, int health, int speed) {
        this.position = new Position(x, y);
        this.hitbox = new Hitbox(w, h);
        this.health = new Health(health);
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public Health getHealth() {
        return health;
    }

    public void setHealth(Health health) {
        this.health = health;
    }

    public void setMovement(MovementStrategy movement) {
        this.movement = movement;
    }

    public void setAttack(AttackStrategy attack) {
        this.attack = attack;
    }

    @Override
    public Rectangle getBounds() {
        return hitbox.getBounds(position);
    }

    public boolean isAlive() {
        return health.isAlive();
    }

    public void takeDamage(int damage) {
        health.takeDamage(damage);
    }

}