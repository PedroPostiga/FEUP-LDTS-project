package com.gladiator.model.enemy.enemy_types;

import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.movement.MovementStrategy;

public class Vampire extends Enemy {

    private final double lifestealPercentage;

    public Vampire(int x, int y, MovementStrategy movement, AttackStrategy attack) {
        super(x, y, 35, 35, 80, 3, movement, attack);
        this.lifestealPercentage = 0.3;
    }

    public double getLifestealPercentage() {
        return lifestealPercentage;
    }
}
