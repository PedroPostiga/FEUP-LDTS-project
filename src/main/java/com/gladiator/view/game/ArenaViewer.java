package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.Arena;
import com.gladiator.model.attack.projectile.Arrow;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.entity.InvisibleWall;
import com.gladiator.model.entity.Obstacle;
import com.gladiator.model.gladiator.Gladiator;
import com.gladiator.view.Viewer;
import com.gladiator.view.ViewerRegistry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArenaViewer extends Viewer<Arena> {

    public ArenaViewer(Arena model) {
        super(model);
    }

    @Override
    protected void drawElements(GUI gui) throws IOException {
        Arena arena = this.getModel();

        gui.drawSprite("sprites/arena.png", new Position(0,0));

        Gladiator gladiator = arena.getGladiator();
        List<Enemy> enemies = arena.getEnemiePool().getAllActiveEnemies();
        List<Obstacle> obstacles = arena.getObstacles();
        List<Arrow> arrows = arena.getArrowPool().getActiveArrows();

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

        for (Arrow arrow : arrows) {
            EntityViewer<Arrow> viewer = new ArrowViewer();
            viewer.draw(arrow, gui);
        }

        // Draw hitboxes
        if (gladiator != null) {
            java.awt.Rectangle hitbox = gladiator.getHitbox();
            gui.drawHitbox(hitbox.x, hitbox.y, hitbox.width, hitbox.height, "#00FFFF"); // Cyan for gladiator
        }

        for (Enemy enemy : enemies) {
            java.awt.Rectangle hitbox = enemy.getHitbox();
            gui.drawHitbox(hitbox.x, hitbox.y, hitbox.width, hitbox.height, "#FF00FF"); // Magenta for enemies
        }

        for (Obstacle obstacle : obstacles) {
            if (obstacle instanceof InvisibleWall) {continue;}
            java.awt.Rectangle hitbox = obstacle.getHitbox();
            gui.drawHitbox(hitbox.x, hitbox.y, hitbox.width, hitbox.height, "#FFFF00"); // Yellow for obstacles
        }

        for (Arrow arrow : arrows) {
            java.awt.Rectangle hitbox = arrow.getHitbox();
            gui.drawHitbox(hitbox.x, hitbox.y, hitbox.width, hitbox.height, "#FF0000");
        }
    }
}
