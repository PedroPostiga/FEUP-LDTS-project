package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.Arena;
import com.gladiator.model.attack.projectile.Arrow;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.gladiator.Gladiator;
import com.gladiator.view.Viewer;
import com.gladiator.view.ViewerRegistry;

import java.io.IOException;
import java.util.List;

public class GameViewer extends Viewer<Arena> {
    private final ArenaViewer arenaViewer;

    public GameViewer(Arena model) {
        super(model);
        this.arenaViewer = new ArenaViewer(model);
    }

    @Override
    protected void drawElements(GUI gui) throws IOException {
        Arena arena = this.getModel();

        // Draw immovable entities (obstacles) every frame after clearing
        // Note: They must be redrawn every frame because gui.clear() clears the screen,
        // but the logic is separated in ArenaViewer for clarity
        arenaViewer.drawWithoutClearing(gui);

        // Draw moving entities (gladiator, enemies, arrows) every tick
        Gladiator gladiator = arena.getGladiator();
        List<Enemy> enemies = arena.getEnemiePool().getAllActiveEnemies();
        List<Arrow> arrows = arena.getArrowPool().getActiveArrows();

        new GladiatorViewer().draw(gladiator, gui);

        for (Enemy enemy : enemies) {
            EntityViewer<Enemy> viewer = (EntityViewer<Enemy>) ViewerRegistry.getViewer(enemy);
            viewer.draw(enemy, gui);
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

        for (Arrow arrow : arrows) {
            java.awt.Rectangle hitbox = arrow.getHitbox();
            gui.drawHitbox(hitbox.x, hitbox.y, hitbox.width, hitbox.height, "#FF0000");
        }
    }
}
