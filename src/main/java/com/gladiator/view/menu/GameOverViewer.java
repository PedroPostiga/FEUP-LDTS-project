package com.gladiator.view.menu;

import com.gladiator.gui.GUI;
import com.gladiator.model.component.Position;
import com.gladiator.model.menu.GameOverModel;
import com.gladiator.view.Viewer;

import java.io.IOException;

public class GameOverViewer extends Viewer<GameOverModel> {

    public GameOverViewer(GameOverModel model) {
        super(model);
    }

    @Override
    protected void drawElements(GUI gui) throws IOException {
        // Draw the appropriate PNG based on win/loss and selected option
        GameOverModel model = getModel();
        GameOverModel.Option selected = model.getSelected();
        
        String sprite;
        if (model.isWon()) {
            // Winning screen
            switch (selected) {
                case MENU:
                    sprite = "sprites/gameOver/win_menu.png";
                    break;
                case QUIT:
                    sprite = "sprites/gameOver/win_quit.png";
                    break;
                default:
                    sprite = "sprites/gameOver/win_menu.png";
            }
        } else {
            // Losing screen
            switch (selected) {
                case MENU:
                    sprite = "sprites/gameOver/lose_menu.png";
                    break;
                case QUIT:
                    sprite = "sprites/gameOver/lose_quit.png";
                    break;
                default:
                    sprite = "sprites/gameOver/lose_menu.png";
            }
        }
        
        gui.drawSprite(sprite, new Position(0, 0));
    }

}

