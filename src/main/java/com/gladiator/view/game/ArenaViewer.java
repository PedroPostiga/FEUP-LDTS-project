package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.Arena;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.entity.InvisibleWall;
import com.gladiator.model.entity.Obstacle;
import com.gladiator.model.gladiator.Gladiator;
import com.gladiator.view.Viewer;
import com.gladiator.view.ViewerRegistry;

import java.io.IOException;
import java.util.List;

public class ArenaViewer extends Viewer<Arena> {

    public ArenaViewer(Arena model) {
        super(model);
    }

    @Override
    protected void drawElements(GUI gui) throws IOException {
        Arena arena = this.getModel();
        Gladiator gladiator = arena.getGladiator();
        List<Enemy> enemies = arena.getEnemiePool().getAllActiveEnemies();
        List<Obstacle> obstacles = arena.getObstacles();

        new GladiatorViewer().draw(gladiator, gui);
        for (Enemy enemy : enemies) {
            EntityViewer<Enemy> viewer = (EntityViewer<Enemy>) ViewerRegistry.getViewer(enemy);
            viewer.draw(enemy, gui);
        }
        for (Obstacle obstacle : obstacles) {
            if (obstacle instanceof InvisibleWall) {continue;}
            EntityViewer<Obstacle> viewer = (EntityViewer<Obstacle>) ViewerRegistry.getViewer(obstacle);
            viewer.draw(obstacle, gui);
        }
    }
}
