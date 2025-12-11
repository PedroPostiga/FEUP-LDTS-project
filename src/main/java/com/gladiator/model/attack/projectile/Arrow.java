package com.gladiator.model.attack.projectile;

import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;

public class Arrow extends Projectile {

    public Arrow(Position start, int speed, int damage,
                 double maxDistance, Enemy target) {
        super(start, speed, damage, maxDistance, target);
    }
}
