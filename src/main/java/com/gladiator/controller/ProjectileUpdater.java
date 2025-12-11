package com.gladiator.controller;

import com.gladiator.model.Arena;
import com.gladiator.model.attack.projectile.Projectile;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;

import java.util.List;

public class ProjectileUpdater {

    public void update(Arena arena) {
        List<Projectile> projectiles = arena.getActiveProjectiles();

        for (int i = projectiles.size - 1; i >= 0; i--) {
            Projectile p = projectiles.get(i);

            if (!p.isActive()) {
                arena.removeProjectile(i);
                continue;
            }

            Enemy enemy = p.getTarget();

            int dx = Integer.compare(p.getPosition().getX(),
                    enemy.getPosition().getX()) * p.getSpeed();
            int dy = Integer.compare(p.getPosition().getY(),
                    enemy.getPosition().getY()) * p.getSpeed();

            // Move projectile
            int newX = p.getPosition().getX() + dx;
            int newY = p.getPosition().getY() + dy;

            if (!arena.isEmpty(newX, newY)) {
                arena.removeProjectile(i);
                continue;
            }

            p.setPosition(new Position(newX, newY));
        }
    }
}
