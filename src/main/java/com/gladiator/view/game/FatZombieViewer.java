package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.enemy.enemy_types.FatZombie;

import java.io.IOException;

public class FatZombieViewer implements EntityViewer<FatZombie> {
    @Override
    public void draw(FatZombie fatZombie, GUI gui) throws IOException {
        gui.drawSprite("sprites/movingEntity/fat_zombie", fatZombie.getPosition());
    }
}
