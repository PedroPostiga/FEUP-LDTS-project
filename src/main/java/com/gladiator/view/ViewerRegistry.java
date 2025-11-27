package com.gladiator.view;

import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.enemy.enemy_types.FatZombie;
import com.gladiator.model.enemy.enemy_types.LightZombie;
import com.gladiator.model.enemy.enemy_types.Vampire;
import com.gladiator.view.game.EntityViewer;
import com.gladiator.view.game.FatZombieViewer;
import com.gladiator.view.game.LightZombieViewer;
import com.gladiator.view.game.VampireViewer;

import java.util.HashMap;
import java.util.Map;

public class ViewerRegistry {
    private static final Map<Class<? extends Enemy>, EntityViewer<? extends Enemy>> viewers = new HashMap<>();

    static {
        viewers.put(Vampire.class, new VampireViewer());
        viewers.put(FatZombie.class, new FatZombieViewer());
        viewers.put(LightZombie.class, new LightZombieViewer());
    }

    @SuppressWarnings("unchecked")
    public static <T extends Enemy> EntityViewer<T> getViewer(T enemy) {
        return (EntityViewer<T>) viewers.get(enemy.getClass());
    }
}
