package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.entity.SmallRock;

import java.io.IOException;

public class SmallRockViewer implements EntityViewer<SmallRock> {
    @Override
    public void draw(SmallRock smallRock, GUI gui) throws IOException {
        gui.drawSmallRock(smallRock.getPosition());
    }
}