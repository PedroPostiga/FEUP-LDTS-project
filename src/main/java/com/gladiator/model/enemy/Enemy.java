package com.gladiator.model.enemy;

import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.component.Health;
import com.gladiator.model.entity.MovingEntity;
import com.gladiator.model.movement.MovementStrategy;

public abstract class Enemy extends MovingEntity {

    private boolean active;
    private int maxHealth;

    public Enemy(int x, int y, int w, int h, int hp, int speed, MovementStrategy movement, AttackStrategy attack){
        super(x, y, w, h, hp, speed);
        setMovement(movement);
        setAttack(attack);
        this.maxHealth = hp;
        this.active = true;
    }

    public MovementStrategy getMovementStrategy() {
        return movement;
    }

    public AttackStrategy getAttackStrategy() {
        return attack;
    }

    public void reset() {
        setHealth(new Health(maxHealth));
        active = false;
    }

    public void resetHealth() {
        setHealth(new Health(maxHealth));
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
}
