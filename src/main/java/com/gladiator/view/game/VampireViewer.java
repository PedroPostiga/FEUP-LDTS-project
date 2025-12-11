package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.enemy.enemy_types.Vampire;

import java.io.IOException;

public class VampireViewer implements EntityViewer<Vampire> {
    @Override
    public void draw(Vampire vampire, GUI gui) throws IOException {
        gui.drawSprite("sprite/vampire.png" ,vampire.getPosition());
    }
}
