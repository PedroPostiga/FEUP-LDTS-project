package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.entity.Entity;

import java.io.IOException;

public interface EntityViewer<T extends Entity> {
    void draw(T entity, GUI gui) throws IOException;
}
