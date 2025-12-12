package com.gladiator.view.menu;

import com.gladiator.gui.GUI;
import com.gladiator.model.component.Position;
import com.gladiator.view.Viewer;

import java.io.IOException;

public class CreditsViewer extends Viewer<Void> {

    public CreditsViewer() {
        super(null);
    }

    @Override
    protected void drawElements(GUI gui) throws IOException {
        gui.drawSprite("sprites/menu/credits_screen.png", new Position(0, 0));
    }
}
