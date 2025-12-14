package com.gladiator.model;

import com.gladiator.model.attack.projectile.SingleArrowPool;
import com.gladiator.model.enemy.EnemyPool;
import com.gladiator.model.entity.*;
import com.gladiator.model.gladiator.Gladiator;

import java.util.ArrayList;
import java.util.List;

public class ArenaBuilder {
    private static final int ARENA_WIDTH = 400;
    private static final int ARENA_HEIGHT = 300;

    public Arena createArena() {
        Arena arena = new Arena(ARENA_WIDTH, ARENA_HEIGHT);

        Gladiator gladiator = createGladiator();
        arena.setGladiator(gladiator);
        EnemyPool enemyPool = new EnemyPool(gladiator);
        enemyPool.preWarmPools();
        arena.setEnemiePool(enemyPool);
        SingleArrowPool singleArrowPool = new SingleArrowPool();
        arena.setArrowPool(singleArrowPool);
        arena.setObstacles(createObstacles(arena));

        return arena;
    }

    public WaveManager createWaveManager(Arena arena) {
        return new WaveManager(arena);
    }


    protected List<Obstacle> createObstacles(Arena arena) {
        List<Obstacle> obstacles = new ArrayList<>();

        obstacles.add(new Tree(114,200));
        obstacles.add(new LargeRock(347, 45));
        obstacles.add(new Tree(289, 123));

        obstacles.add(new LargeRock(227, 247));
        obstacles.add(new Tree(156, 6));
        obstacles.add(new LargeRock(198, 114));

        obstacles.add(new Tree(67, 67));
        obstacles.add(new SmallRock(340, 256));
        obstacles.add(new SmallRock(37, 238));

        // Create 4 invisible walls around the entire arena

        // Top border: 1 unit thick, runs across the entire top
        obstacles.add(new InvisibleWall(0, -1, ARENA_WIDTH, 1));

        // Bottom border: 1 unit thick, runs across the entire bottom
        obstacles.add(new InvisibleWall(0, ARENA_HEIGHT, ARENA_WIDTH, 1));

        // Left border: 1 unit thick, runs along the entire left side
        obstacles.add(new InvisibleWall(-1, 0, 1, ARENA_HEIGHT));

        // Right border: 1 unit thick, runs along the entire right side
        obstacles.add(new InvisibleWall(ARENA_WIDTH, 0, 1, ARENA_HEIGHT));

        return obstacles;
    }

    protected Gladiator createGladiator() {
        return new Gladiator(ARENA_WIDTH / 2, ARENA_HEIGHT / 2,5,5,100,5);
    }
}
