package com.gladiator.view.menu;

import com.gladiator.gui.GUI;
import com.gladiator.model.component.Position;
import com.gladiator.model.menu.MenuModel;
import com.gladiator.view.Viewer;

import java.io.IOException;

public class MenuViewer extends Viewer<MenuModel> {

    public MenuViewer(MenuModel model) {
        super(model);
    }

    @Override
    protected void drawElements(GUI gui) throws IOException {
        // Draw the appropriate PNG based on the selected option
        MenuModel.Option selected = getModel().getSelected();

        switch (selected) {
            case PLAY:
                gui.drawSprite("sprites/menu/menu_startgame.png", new Position(0, 0));
                break;
            case CREDITS:
                gui.drawSprite("sprites/menu/menu_credits.png", new Position(0, 0));
                break;
            case EXIT:
                gui.drawSprite("sprites/menu/menu_exit.png", new Position(0, 0));
                break;
        }
    }


}