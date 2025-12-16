package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.enemy.enemy_types.Vampire;

import java.io.IOException;

public class VampireViewer implements EntityViewer<Vampire> {
    @Override
    public void draw(Vampire vampire, GUI gui) throws IOException {
        AttackStrategy attack = vampire.getAttackStrategy();
        boolean attacking = attack != null && attack.isAttacking(vampire);

        String sprite;
        if (attacking) {
            // Use attack sprite - defaulting to right, you can add direction tracking later
            sprite = "sprites/movingEntity/vampire_attack.png";
        } else {
            sprite = "sprites/movingEntity/vampire.png";
        }

        gui.drawSprite(sprite, vampire.getPosition());
    }
}
