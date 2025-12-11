package com.gladiator.model.movement;

import com.gladiator.model.Arena;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;

import java.util.Random;

public class WanderMovement implements MovementStrategy {
    private final Random random;

    public WanderMovement() {
        random = new Random();
    }

    @Override
    public void move(Enemy enemy, Arena arena) {
        int dx = (random.nextInt(3) - 1) * enemy.getSpeed();
        int dy = (random.nextInt(3) - 1) * enemy.getSpeed();

        if (arena.isEmpty(new Position(enemy.getPosition().getX() + dx, enemy.getPosition().getY() + dy))) {
            enemy.setPosition(new Position(enemy.getPosition().getX() + dx, enemy.getPosition().getY() + dy));
        }
    }
}
