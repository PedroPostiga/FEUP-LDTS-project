package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.enemy.enemy_types.LightZombie;

import java.io.IOException;

public class LightZombieViewer implements EntityViewer<LightZombie> {
    @Override
    public void draw(LightZombie lightZombie, GUI gui) throws IOException {
        gui.drawSprite("sprites/movingEntity/light_zombie_right.png",lightZombie.getPosition());
    }
}
