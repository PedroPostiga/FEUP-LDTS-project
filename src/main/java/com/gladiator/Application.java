package com.gladiator;

import com.gladiator.controller.Controller;
import com.gladiator.controller.MenuController;
import com.gladiator.gui.GUI;
import com.gladiator.gui.LanternaGUI;
import com.gladiator.model.menu.MenuModel;
import com.gladiator.view.menu.MenuViewer;

import java.awt.*;


public class Application {
    public static void main(String[] args) {
        try {
            GUI gui = new LanternaGUI(400, 300);

            MenuModel menuModel = new MenuModel();
            Controller controller = new MenuController(new MenuViewer(menuModel));

            controller.run(gui);

            gui.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
