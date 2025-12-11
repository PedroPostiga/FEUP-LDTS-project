package com.gladiator.model.movement;

import com.gladiator.model.Arena;
import com.gladiator.model.enemy.Enemy;

public interface MovementStrategy {
    void move(Enemy enemy, Arena arena);
}
