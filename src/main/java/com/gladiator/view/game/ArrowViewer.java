package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.attack.projectile.Arrow;

import java.io.IOException;

public class ArrowViewer implements EntityViewer<Arrow>{
    @Override
    public void draw(Arrow arrow, GUI gui) throws IOException {
        gui.drawSprite("sprites/projectile/arrow.png", arrow.getPosition());
    }
}
