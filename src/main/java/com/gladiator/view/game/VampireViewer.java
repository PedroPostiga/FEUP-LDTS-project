package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.enemy.enemy_types.Vampire;

public class VampireViewer implements EntityViewer<Vampire> {
    @Override
    public void draw(Vampire vampire, GUI gui){
        gui.drawVampire(vampire.getPosition());
    }
}
