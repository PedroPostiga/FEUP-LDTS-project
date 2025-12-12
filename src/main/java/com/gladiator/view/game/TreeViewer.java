package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.entity.Tree;

import java.io.IOException;

public class TreeViewer implements EntityViewer<Tree>{
    @Override
    public void draw(Tree tree, GUI gui) throws IOException {
        gui.drawSprite("sprites/obstacle/tree.png",tree.getPosition());
    }
}


