package com.gladiator.model.component;

public class Health {
    private int health;
    private final int maxHealth;

    public Health(int health) {
        this.health = health;
        this.maxHealth = health;
    }

    public void takeDamage(int damage) {
        if (damage < health) {
            health -= damage;
        }
        else {
            health = 0;
        }
    }

    public void heal(int amount) {
        health = Math.min(health + amount, maxHealth);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
