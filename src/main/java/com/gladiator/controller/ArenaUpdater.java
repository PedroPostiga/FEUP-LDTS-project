package com.gladiator.controller;

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
