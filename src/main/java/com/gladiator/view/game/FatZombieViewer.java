package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.enemy.enemy_types.FatZombie;

import java.io.IOException;

public class FatZombieViewer implements EntityViewer<FatZombie> {
    @Override
    public void draw(FatZombie fatZombie, GUI gui) throws IOException {
        /*AttackStrategy attack = fatZombie.getAttackStrategy();
        boolean attacking = attack != null && attack.isAttacking(fatZombie);

        String sprite;
        if (attacking) {
            // Use attack sprite - defaulting to right, you can add direction tracking later
            sprite = "sprites/movingEntity/fat_zombie_attack.png";
        } else {
            sprite = "sprites/movingEntity/fat_zombie.png";
        }*/

        gui.drawSprite("sprites/movingEntity/fat_zombie.png", fatZombie.getPosition());
    }
}
