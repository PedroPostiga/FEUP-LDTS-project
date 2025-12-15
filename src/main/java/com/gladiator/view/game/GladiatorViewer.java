package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.gladiator.Gladiator;

import java.io.IOException;

public class GladiatorViewer implements EntityViewer<Gladiator> {

    @Override
    public void draw(Gladiator gladiator, GUI gui) throws IOException {
        /*SwordAttack attack = gladiator.getSwordAttack();
        boolean attacking = attack != null && attack.isAttacking(gladiator);

        String sprite;
        if (attacking) {
            // Use attack sprite - defaulting to right, you can add direction tracking later
            sprite = "sprites/movingEntity/gladiator_attack.png";
        } else {
            sprite = "sprites/movingEntity/gladiator.png";
        }*/

        gui.drawSprite("sprites/movingEntity/gladiator.png", gladiator.getPosition());
    }
}
