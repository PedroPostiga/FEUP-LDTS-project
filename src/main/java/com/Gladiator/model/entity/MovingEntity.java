package com.Gladiator.model.entity;

import java.awt.*;

public abstract class MovingEntity implements Entity {
    protected int x, y;
    protected int width, height;
    protected int vx,vy;
    protected int health;

    protected MovingEntity(int x, int y, int w, int h, int health) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.health = health;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    public void move(int t){
        x += vx * t;
        y += vy * t;
    }

    public void takeDamage(int damage) {
        if (damage < health) {
            health -= damage;
        }
        else {
            health = 0;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getVx() {
        return vx;
    }
    public int getVy() {
        return vy;
    }
    public int getHealth() {
        return health;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setVx(int vx) {
        this.vx = vx;
    }
    public void setVy(int vy) {
        this.vy = vy;
    }
    public void setHealth(int health) {
        this.health = health;
    }
}