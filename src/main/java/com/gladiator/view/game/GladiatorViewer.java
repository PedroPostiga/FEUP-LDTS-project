package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.gladiator.Gladiator;

import java.io.IOException;

public class GladiatorViewer implements EntityViewer<Gladiator> {

    @Override
    public void draw(Gladiator gladiator, GUI gui) throws IOException {
        gui.drawGladiator(gladiator.getPosition());
    }
}
