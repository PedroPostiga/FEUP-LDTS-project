package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.enemy.enemy_types.LightZombie;

import java.io.IOException;

public class LightZombieViewer implements EntityViewer<LightZombie> {
    @Override
    public void draw(LightZombie lightZombie, GUI gui) throws IOException {
        AttackStrategy attack = lightZombie.getAttackStrategy();
        boolean attacking = attack != null && attack.isAttacking(lightZombie);
        
        String sprite;
        if (attacking) {
            // Use attack sprite - defaulting to right, you can add direction tracking later
            sprite = "sprites/movingEntity/light_zombie_attack.png";
        } else {
            sprite = "sprites/movingEntity/light_zombie.png";
        }
        
        gui.drawSprite(sprite, lightZombie.getPosition());
    }
}
