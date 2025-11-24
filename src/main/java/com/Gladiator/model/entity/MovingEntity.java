package com.Gladiator.model.entity;

import com.Gladiator.model.component.Health;
import com.Gladiator.model.component.Hitbox;
import com.Gladiator.model.component.Position;

import java.awt.*;

public abstract class MovingEntity implements Entity {
    protected Position position;
    protected Hitbox hitbox;
    protected Health health;

    protected MovingEntity(int x, int y, int w, int h, int health) {
        this.position = new Position(x, y);
        this.hitbox = new Hitbox(w, h);
        this.health = new Health(health);
    }

    public Position getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return hitbox.getBounds(position);
    }

    @Override
    public boolean isAlive() {
        return health.isAlive();
    }

    public void takeDamage(int damage) {
        health.takeDamage(damage);
    }

    @Override
    public void render(Graphics g) {

    }

}