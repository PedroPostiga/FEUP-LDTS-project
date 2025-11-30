package com.gladiator.view;

import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.enemy.enemy_types.FatZombie;
import com.gladiator.model.enemy.enemy_types.LightZombie;
import com.gladiator.model.enemy.enemy_types.Vampire;
import com.gladiator.model.entity.LargeRock;
import com.gladiator.model.entity.Obstacle;
import com.gladiator.model.entity.SmallRock;
import com.gladiator.model.entity.Tree;
import com.gladiator.view.game.*;

import java.util.HashMap;
import java.util.Map;

public class ViewerRegistry {
    private static final Map<Class<? extends Enemy>, EntityViewer<? extends Enemy>> enemyviewers = new HashMap<>();
    private static final Map<Class<? extends Obstacle>, EntityViewer<? extends Obstacle>> obstacleviewers = new HashMap<>();
    static {
        enemyviewers.put(Vampire.class, new VampireViewer());
        enemyviewers.put(FatZombie.class, new FatZombieViewer());
        enemyviewers.put(LightZombie.class, new LightZombieViewer());

        obstacleviewers.put(SmallRock.class, new SmallRockViewer());
        obstacleviewers.put(LargeRock.class, new LargeRockViewer());
        obstacleviewers.put(Tree.class, new TreeViewer());

    }

    @SuppressWarnings("unchecked")
    public static <T extends Enemy> EntityViewer<T> getViewer(T enemy) {
        return (EntityViewer<T>) enemyviewers.get(enemy.getClass());
    }
    @SuppressWarnings("uncheked")
    public static <T extends Obstacle> EntityViewer<T> getViewer(T obstacle) {
        return (EntityViewer<T>) obstacleviewers.get(obstacle.getClass());
    }
}
