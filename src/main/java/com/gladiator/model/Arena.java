package com.gladiator.model;

import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.entity.InvisibleWall;
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
        createInvisibleBorders();
    }

    private void createInvisibleBorders() {
        // Create 4 invisible walls around the entire arena

        // Top border: 1 unit thick, runs across the entire top
        obstacles.add(new InvisibleWall(0, -1, width, 1));

        // Bottom border: 1 unit thick, runs across the entire bottom
        obstacles.add(new InvisibleWall(0, height, width, 1));

        // Left border: 1 unit thick, runs along the entire left side
        obstacles.add(new InvisibleWall(-1, 0, 1, height));

        // Right border: 1 unit thick, runs along the entire right side
        obstacles.add(new InvisibleWall(width, 0, 1, height));
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
        for (Enemy enemy : enemies) {
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

    public boolean isGladiator(Position position) {
        return gladiator.getPosition().equals(position);
    }

    public boolean isEmpty(Position position) {
        return !(isEnemy(position) || isObstacle(position) || isGladiator(position));
    }
}
