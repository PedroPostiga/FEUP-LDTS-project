package com.gladiator.model;

import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.gladiator.Gladiator;

import java.util.List;

public class Arena {
    private final int width;
    private final int height;
    private Gladiator gladiator;
    private List<Enemy> enemies;

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

    public boolean isEnemy(Position position) {
        for(Enemy enemy : enemies)
            if (enemy.getPosition().equals(position))
                return true;
        return false;
    }
}
