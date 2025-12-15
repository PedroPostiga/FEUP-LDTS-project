package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.gladiator.Gladiator;

import java.io.IOException;

public class GladiatorViewer implements EntityViewer<Gladiator> {

    @Override
    public void draw(Gladiator gladiator, GUI gui) throws IOException {
        String sprite;
        
        // Check if gladiator is currently performing a sword attack (attack sprites are only for sword attacks)
        boolean attacking = gladiator.getSwordAttack() != null &&
                            gladiator.getSwordAttack().isAttacking(gladiator);
        
        // Select sprite based on direction and attack state
        if (attacking) {
            // Attack sprites based on direction
            switch (gladiator.getDirection()) {
                case LEFT:
                    sprite = "sprites/movingEntity/gladiator_left_attack.png";
                    break;
                case RIGHT:
                    sprite = "sprites/movingEntity/gladiator_right_attack.png";
                    break;
                case UP:
                    sprite = "sprites/movingEntity/gladiator_up_attack.png";
                    break;
                case DOWN:
                default:
                    sprite = "sprites/movingEntity/gladiator_attack.png";
                    break;
            }
        } else {
            // Normal movement sprites based on direction
            switch (gladiator.getDirection()) {
                case LEFT:
                    sprite = "sprites/movingEntity/gladiator_left.png";
                    break;
                case RIGHT:
                    sprite = "sprites/movingEntity/gladiator_right.png";
                    break;
                case UP:
                    sprite = "sprites/movingEntity/gladiator_up.png";
                    break;
                case DOWN:
                default:
                    sprite = "sprites/movingEntity/gladiator.png";
                    break;
            }
        }
        
        gui.drawSprite(sprite, gladiator.getPosition());
    }
}
