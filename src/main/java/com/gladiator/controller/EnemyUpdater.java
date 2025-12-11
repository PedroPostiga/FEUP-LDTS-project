package com.gladiator.controller;

import com.gladiator.model.Arena;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.gladiator.Gladiator;
import com.gladiator.model.movement.MovementStrategy;

import java.util.List;

public class EnemyUpdater {
    public void update(Arena arena) {
        List<Enemy> enemies = arena.getActiveEnemies();
        Gladiator g = arena.getGladiator();

        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy e = enemies.get(i);

            if (!e.isAlive()) {
                arena.removeEnemy(i);
                continue;
            }

            MovementStrategy movementStrategy = e.getMovementStrategy();
            // Move according to strategy
            if (movementStrategy != null) {
                movementStrategy.move(e, arena);
            }
        }
    }
}