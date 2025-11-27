package com.gladiator.model;

import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.attack.VampireAttack;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.enemy.enemy_types.FatZombie;
import com.gladiator.model.enemy.enemy_types.LightZombie;
import com.gladiator.model.enemy.enemy_types.Vampire;
import com.gladiator.model.gladiator.Gladiator;
import com.gladiator.model.movement.ChaseMovement;
import com.gladiator.model.movement.WanderMovement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArenaBuilder {
    public Arena createArena() {
        Arena arena = new Arena(20, 20);

        arena.setGladiator(createGladiator());
        arena.setEnemies(createEnemies(arena));

        return arena;
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

    protected Gladiator createGladiator() {
        return new Gladiator(5,5,5,5,100,5);
    }
}
