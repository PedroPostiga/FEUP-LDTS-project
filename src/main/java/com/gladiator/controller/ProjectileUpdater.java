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

            // Release arrow if target enemy is null, dead, or no longer in active enemies list
            if (enemy == null || !enemy.isAlive() || !arena.getEnemiePool().getAllActiveEnemies().contains(enemy)) {
                singleArrowPool.releaseArrow(a);
                continue;
            }

            // Calculate centers for accurate targeting
            Rectangle arrowHitbox = a.getHitbox();
            double arrowCenterX = arrowHitbox.getCenterX();
            double arrowCenterY = arrowHitbox.getCenterY();
            
            Rectangle enemyHitbox = enemy.getHitbox();
            double enemyCenterX = enemyHitbox.getCenterX();
            double enemyCenterY = enemyHitbox.getCenterY();

            // Calculate direction vector from arrow center to enemy center
            double dx = enemyCenterX - arrowCenterX;
            double dy = enemyCenterY - arrowCenterY;
            double distance = Math.sqrt(dx * dx + dy * dy);
            
            // Normalize direction and apply speed
            if (distance > 0) {
                dx = (dx / distance) * a.getSpeed();
                dy = (dy / distance) * a.getSpeed();
            } else {
                dx = 0;
                dy = 0;
            }

            // Move projectile (cast to int for position)
            int newX = a.getPosition().getX() + (int) Math.round(dx);
            int newY = a.getPosition().getY() + (int) Math.round(dy);

            Rectangle newHitbox = new Rectangle(newX, newY, a.getHitbox().width, a.getHitbox().height);

            if(arena.isEnemy(newHitbox)){
                enemy.takeDamage(a.getDamage());
                singleArrowPool.releaseArrow(a);
                continue;
            }

            if (!arena.isEmpty(newHitbox, null, arena.getGladiator())) {
                singleArrowPool.releaseArrow(a);
                continue;
            }

            a.setPosition(new Position(newX, newY));
            
            // Check if projectile has exceeded max distance
            if (a.getDistanceTraveled() > a.getMaxDistance()) {
                singleArrowPool.releaseArrow(a);
                continue;
            }
        }
    }
}
