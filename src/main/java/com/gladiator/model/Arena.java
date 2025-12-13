package com.gladiator.model;

import com.gladiator.model.attack.projectile.Arrow;
import com.gladiator.model.attack.projectile.SingleArrowPool;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.enemy.EnemyPool;
import com.gladiator.model.entity.InvisibleWall;
import com.gladiator.model.entity.Obstacle;
import com.gladiator.model.gladiator.Gladiator;

import java.awt.*;
import java.util.List;

public class Arena {
    private final int width;
    private final int height;
    protected Gladiator gladiator;
    private EnemyPool enemyPool;
    private SingleArrowPool singleArrowPool;
    protected List<Obstacle> obstacles;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
    }


    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public Gladiator getGladiator() {
        return gladiator;
    }
    public void setGladiator(Gladiator gladiator) {
        this.gladiator = gladiator;
    }
    public EnemyPool getEnemiePool() {
        return enemyPool;
    }
    public void setEnemiePool(EnemyPool pool) {
        this.enemyPool = pool;
    }
    public SingleArrowPool getArrowPool() {
        return singleArrowPool;
    }
    public void setArrowPool(SingleArrowPool pool) {
        this.singleArrowPool = pool;
    }
    public List<Obstacle> getObstacles() { return obstacles; }
    public void setObstacles(List<Obstacle> obstacles) { this.obstacles = obstacles; }

    public boolean isEnemy(Rectangle hitbox) {
        return isEnemy(hitbox, null);
    }

    public boolean isEnemy(Rectangle hitbox, Enemy excludeEnemy) {
        if (enemyPool == null) return false;
        for (Enemy enemy : enemyPool.getAllActiveEnemies()) {
            if (enemy == excludeEnemy) continue;
            if (enemy.getHitbox().intersects(hitbox)) {
                return true;
            }
        }
        return false;
    }

    public boolean isObstacle(Rectangle hitbox) {
        if (obstacles == null) return false;
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getHitbox().intersects(hitbox)) {
                return true;
            }
        }
        return false;
    }

    public boolean isArrow(Rectangle hitbox) {
        if (singleArrowPool == null) return false;
        for (Arrow arrow : singleArrowPool.getActiveArrows()){
            if (arrow.getHitbox().intersects(hitbox)) {
                return true;
            }
        }
        return false;
    }

    public boolean isGladiator(Rectangle hitbox) {
        return isGladiator(hitbox, null);
    }

    public boolean isGladiator(Rectangle hitbox, Gladiator excludeGladiator) {
        if (gladiator == null) return false;
        if (gladiator == excludeGladiator) return false;
        return gladiator.getHitbox().intersects(hitbox);
    }

    public boolean isEmpty(Rectangle hitbox) {
        return isEmpty(hitbox, null, null);
    }

    public boolean isEmpty(Rectangle hitbox, Enemy excludeEnemy) {
        return isEmpty(hitbox, excludeEnemy, null);
    }

    public boolean isEmpty(Rectangle hitbox, Enemy excludeEnemy, Gladiator excludeGladiator) {
        return !(isEnemy(hitbox, excludeEnemy) || isObstacle(hitbox) || isGladiator(hitbox, excludeGladiator));
    }
}
