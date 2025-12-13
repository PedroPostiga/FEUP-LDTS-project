package com.gladiator.controller;

import com.gladiator.model.Arena;
import com.gladiator.model.attack.projectile.Arrow;
import com.gladiator.model.attack.projectile.Projectile;
import com.gladiator.model.attack.projectile.SingleArrowPool;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;

import java.awt.*;
import java.util.List;

public class ProjectileUpdater {

    public void update(Arena arena) {
        SingleArrowPool singleArrowPool = arena.getArrowPool();
        List<Arrow> arrows = singleArrowPool.getActiveArrows();

        for (int i = arrows.size() - 1; i >= 0; i--) {
            Arrow a = arrows.get(i);

            Enemy enemy = a.getTarget();

            int dx = Integer.compare(a.getPosition().getX(),
                    enemy.getPosition().getX()) * a.getSpeed();
            int dy = Integer.compare(a.getPosition().getY(),
                    enemy.getPosition().getY()) * a.getSpeed();

            // Move projectile
            int newX = a.getPosition().getX() + dx;
            int newY = a.getPosition().getY() + dy;

            if (!arena.isEmpty(new Rectangle(newX, newY, a.getHitbox().width, a.getHitbox().height))) {
                singleArrowPool.releaseArrow(a);
                continue;
            }

            a.setPosition(new Position(newX, newY));
        }
    }
}
