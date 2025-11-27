package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.Arena;
import com.gladiator.model.enemy.Enemy;
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
        List<Enemy> enemies = arena.getEnemies();
        new GladiatorViewer().draw(gladiator, gui);
        for (Enemy enemy : enemies) {
            EntityViewer<Enemy> viewer = (EntityViewer<Enemy>) ViewerRegistry.getViewer(enemy);
            viewer.draw(enemy, gui);
        }
    }
}
