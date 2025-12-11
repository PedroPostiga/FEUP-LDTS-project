package com.gladiator.view.menu;

import com.gladiator.gui.GUI;
import com.gladiator.model.component.Position;
import com.gladiator.model.menu.MenuModel;
import com.gladiator.view.Viewer;
import com.googlecode.lanterna.TextColor;

import java.io.IOException;

public class MenuViewer extends Viewer<MenuModel> {

    public MenuViewer(MenuModel model) {
        super(model);
    }

    @Override
    protected void drawElements(GUI gui) throws IOException {
        drawTitle(gui);
        drawOptions(gui);
        drawSelectionIndicator(gui);
    }

    private void drawTitle(GUI gui) {
        // Draw ASCII art title for "GLADIATOR"
        String[] titleLines = {
                "  ________.__              .___.__        __                ",
                " /  _____/|  | _____     __| _/|__|____ _/  |_  ___________ ",
                "/   \\  ___|  | \\__  \\   / __ | |  \\__  \\\\   __\\/  _ \\_  __ \\",
                "\\    \\_\\  \\  |__/ __ \\_/ /_/ | |  |/ __ \\|  | (  <_> )  | \\/",
                " \\______  /____(____  /\\____ | |__(____  /__|  \\____/|__|   ",
                "        \\/          \\/      \\/         \\/                   ",
        };

        int startY = 3;
        for (int i = 0; i < titleLines.length; i++) {
            gui.drawText(new Position(10, startY + i), titleLines[i], "#FFD700"); // Gold color
        }
    }

    private void drawOptions(GUI gui) {
        MenuModel.Option[] options = MenuModel.Option.values();
        int startY = 15;
        int optionSpacing = 2;

        for (int i = 0; i < options.length; i++) {
            MenuModel.Option option = options[i];
            String optionText = getOptionText(option);
            Position pos = new Position(35, startY + (i * optionSpacing));

            if (option == getModel().getSelected()) {
                // Selected option - highlighted
                gui.drawText(pos, "> " + optionText, "#00FF00"); // Green
            } else {
                // Unselected option
                gui.drawText(pos, "  " + optionText, "#CCCCCC"); // Light gray
            }
        }
    }

    private void drawSelectionIndicator(GUI gui) {
        // Draw a border around the selected option
        MenuModel.Option selected = getModel().getSelected();
        int startY = 15;
        int optionIndex = selected.ordinal();
        int optionSpacing = 2;

        int y = startY + (optionIndex * optionSpacing) - 1;

        // Draw a simple bracket indicator
        gui.drawText(new Position(33, y + 1), "[", "#00FF00");
        gui.drawText(new Position(33 + getOptionText(selected).length() + 3, y + 1), "]", "#00FF00");
    }

    private String getOptionText(MenuModel.Option option) {
        switch (option) {
            case PLAY: return "START GAME";
            case CREDITS: return "CREDITS";
            case EXIT: return "EXIT GAME";
            default: return "";
        }
    }

}