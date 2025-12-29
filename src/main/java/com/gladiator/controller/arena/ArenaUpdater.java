package com.gladiator.controller.arena;

import com.gladiator.controller.Updater;
import com.gladiator.controller.entity.EnemyUpdater;
import com.gladiator.controller.projectile.ProjectileUpdater;
import com.gladiator.model.Arena;

public class ArenaUpdater implements Updater {

    EnemyUpdater enemyUpdater = new EnemyUpdater();
    ProjectileUpdater projectileUpdater = new ProjectileUpdater();

    @Override
    public void update(Arena arena) {
        enemyUpdater.update(arena);
        projectileUpdater.update(arena);
    }
}
