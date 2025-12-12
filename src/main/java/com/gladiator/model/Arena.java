package com.gladiator.model;

import com.gladiator.model.attack.projectile.Arrow;
import com.gladiator.model.attack.projectile.SingleArrowPool;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.enemy.EnemyPool;
import com.gladiator.model.entity.InvisibleWall;
import com.gladiator.model.entity.Obstacle;
import com.gladiator.model.gladiator.Gladiator;

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

    public boolean isEnemy(Position position) {
        for (Enemy enemy : enemyPool.getAllActiveEnemies()) {
            if (enemy.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    public boolean isObstacle(Position position) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getBounds().contains(position.getX(), position.getY())) {
                return true;
            }
        }
        return false;
    }

    public boolean isArrow(Position position) {
        for (Arrow arrow : singleArrowPool.getActiveArrows()){
            if (arrow.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    public boolean isGladiator(Position position) {
        return gladiator.getPosition().equals(position);
    }

    public boolean isEmpty(Position position) {
        return !(isEnemy(position) || isObstacle(position) || isGladiator(position));
    }
}
