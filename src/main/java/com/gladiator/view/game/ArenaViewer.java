package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.Arena;
import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.attack.VampireAttack;
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

        if (gladiator != null && gladiator.isAlive()) {
            new GladiatorViewer().draw(gladiator, gui);
            drawHealth(gladiator.getHealth().getHealth(), arena.getWidth(), arena.getHeight(), gui);
            // Draw gladiator attack range (sword attack)
            if (gladiator.getSwordAttack() != null) {
                java.awt.Rectangle gladiatorHitbox = gladiator.getHitbox();
                int centerX = gladiatorHitbox.x + gladiatorHitbox.width / 2;
                int centerY = gladiatorHitbox.y + gladiatorHitbox.height / 2;
                drawAttackRange(centerX, centerY, gladiator.getSwordAttack(), gui);
            }
        }

        for (Enemy enemy : enemies) {
            EntityViewer<Enemy> viewer = (EntityViewer<Enemy>) ViewerRegistry.getViewer(enemy);
            viewer.draw(enemy, gui);
            // Draw enemy attack range
            /*if (enemy.getAttackStrategy() != null) {
                java.awt.Rectangle enemyHitbox = enemy.getHitbox();
                int centerX = enemyHitbox.x + enemyHitbox.width / 2;
                int centerY = enemyHitbox.y + enemyHitbox.height / 2;
                drawAttackRange(centerX, centerY, enemy.getAttackStrategy(), gui);
            }*/
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
        /*if (gladiator != null) {
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
        }*/
    }

    private void drawHealth(int health, int arenaWidth, int arenaHeight, GUI gui) throws IOException {
        // Convert health to string to get individual digits
        String healthStr = String.valueOf(health);
        
        // Estimate digit width (assuming numbers are about 16 pixels wide)
        int digitWidth = 11;
        int digitHeight = 14; // Approximate height for positioning
        int padding = 5; // Padding from the edge
        
        // Start position: bottom right, drawing from right to left
        int startX = arenaWidth - padding;
        int startY = arenaHeight - digitHeight - padding;
        
        // Draw each digit from right to left
        for (int i = healthStr.length() - 1; i >= 0; i--) {
            char digit = healthStr.charAt(i);
            String spritePath = "sprites/numbers/" + digit + ".png";
            // Calculate position: rightmost digit at startX - digitWidth, then move left for each digit
            int x = startX - digitWidth * (healthStr.length() - i);
            gui.drawSprite(spritePath, new Position(x, startY));
        }
    }


    private void drawAttackRange(int centerX, int centerY, AttackStrategy attackStrategy, GUI gui) {
        int range = 0;
        String color = "#888888"; // Default gray color
        
        // Get range based on attack type
        if (attackStrategy instanceof SwordAttack) {
            range = ((SwordAttack) attackStrategy).getRange();
            color = "#00FF00"; // Green for sword attacks
        } else if (attackStrategy instanceof VampireAttack) {
            range = ((VampireAttack) attackStrategy).getRange();
            color = "#FF0000"; // Red for vampire attacks
        } else {
            // Skip if attack doesn't have a range
            return;
        }
        
        // Draw range circle centered on entity
        gui.drawCircle(centerX, centerY, range, color);
    }
}
