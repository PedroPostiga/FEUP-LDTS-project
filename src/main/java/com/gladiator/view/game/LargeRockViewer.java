package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.entity.LargeRock;

import java.io.IOException;

public class LargeRockViewer implements EntityViewer<LargeRock> {
    @Override
    public void draw(LargeRock largeRock, GUI gui) throws IOException {
        gui.drawLargeRock(largeRock.getPosition());
    }
}

