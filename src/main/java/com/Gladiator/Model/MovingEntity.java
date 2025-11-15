package com.gladiator.model;

public abstract class MovingEntity implements Entities {
    protected int x, y;
    protected int vx,vy;
    protected int health;

    public void move(int t){
        x += vx * t;
        y += vy * t;
    }

    public void takeDamage(int damage) {
        if(damage < health) {
            health -= damage;
        }
        else {
            health = 0;
        }
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