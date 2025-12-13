package com.gladiator.model;

import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.attack.VampireAttack;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.enemy.enemy_types.FatZombie;
import com.gladiator.model.enemy.enemy_types.LightZombie;
import com.gladiator.model.enemy.enemy_types.Vampire;
import com.gladiator.model.entity.*;
import com.gladiator.model.gladiator.Gladiator;
import com.gladiator.model.movement.ChaseMovement;
import com.gladiator.model.movement.WanderMovement;

import java.util.ArrayList;
import java.util.List;

public class ArenaBuilder {
    private static final int ARENA_WIDTH = 400;
    private static final int ARENA_HEIGHT = 300;

    public Arena createArena() {
        Arena arena = new Arena(ARENA_WIDTH, ARENA_HEIGHT);

        arena.setGladiator(createGladiator());
        arena.setEnemies(createEnemies(arena));
        arena.setObstacles(createObstacles(arena));

        return arena;
    }

    public WaveManager createWaveManager(Arena arena) {
        return new WaveManager(arena);
    }

    protected List<Enemy> createEnemies(Arena arena) {
        List<Enemy> enemies = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Vampire vampire = new Vampire(i,1,
                    new ChaseMovement(5, arena.gladiator),
                    new VampireAttack(10,10,arena.gladiator, 10));
            enemies.add(vampire);
            FatZombie fatZombie = new FatZombie(i,2,
                    new ChaseMovement(5, arena.gladiator),
                    new SwordAttack(10,10, List.of(arena.getGladiator())));
            enemies.add(fatZombie);
            LightZombie lightZombie = new LightZombie(i, 3,
                    new WanderMovement(),
                    new SwordAttack(10,10, List.of(arena.getGladiator())));
            enemies.add(lightZombie);
        }
        return enemies;
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
