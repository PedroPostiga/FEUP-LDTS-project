package com.gladiator.model.movement;

import com.gladiator.model.Arena;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.entity.MovingEntity;
import com.gladiator.model.gladiator.Gladiator;

public class ChaseMovement implements MovementStrategy {
    private final double speed;
    private final Gladiator gladiator;

    public ChaseMovement(double speed, Gladiator gladiator) {
        this.speed = speed;
        this.gladiator = gladiator;
    }

    @Override
    public void move(Enemy enemy, Arena arena) {
        int dx = Integer.compare(arena.getGladiator().getPosition().getX(),
                enemy.getPosition().getX()) * enemy.getSpeed();
        int dy = Integer.compare(arena.getGladiator().getPosition().getY(),
                enemy.getPosition().getY()) * enemy.getSpeed();
        if (arena.isEmpty(new Position(enemy.getPosition().getX() + dx, enemy.getPosition().getY() + dy))) {
            enemy.setPosition(new Position(enemy.getPosition().getX() + dx, enemy.getPosition().getY() + dy));
        }
    }
}
