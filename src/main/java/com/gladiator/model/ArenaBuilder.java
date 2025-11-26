package com.gladiator.model;

import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.gladiator.Gladiator;

import java.util.ArrayList;
import java.util.List;

public class ArenaBuilder {
    public Arena createArena() {
        Arena arena = new Arena(20, 20);

        arena.setGladiator(createGladiator());
        arena.setEnemies(createEnemies());

        return arena;
    }

    protected List<Enemy> createEnemies() {
        List<Enemy>  enemies = new ArrayList<>();
        for (int i = 0; i < 5; i++) {}
    };

    protected Gladiator createGladiator();
}
