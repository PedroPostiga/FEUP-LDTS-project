package com.Gladiator.model.component;

public class Health {
    private int health;

    public Health(int health) {
        this.health = health;
    }

    public void takeDamage(int damage) {
        if (damage < health) {
            health -= damage;
        }
        else {
            health = 0;
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getHealth() {
        return health;
    }
}
