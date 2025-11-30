package com.gladiator.model;

import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.entity.Obstacle;
import com.gladiator.model.gladiator.Gladiator;

import java.util.List;

public class Arena {
    private final int width;
    private final int height;
    protected Gladiator gladiator;
    protected List<Enemy> enemies;
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
    public List<Enemy> getEnemies() {
        return enemies;
    }
    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }
    public List<Obstacle> getObstacles() { return obstacles; }
    public void setObstacles(List<Obstacle> obstacles) { this.obstacles = obstacles; }

    public boolean isEnemy(Position position) {
        for(Enemy enemy : enemies)
            if (enemy.getPosition().equals(position))
                return true;
        return false;
    }
}
