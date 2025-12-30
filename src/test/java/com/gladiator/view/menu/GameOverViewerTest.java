package com.gladiator.view.menu;

import com.gladiator.gui.GUI;
import com.gladiator.model.component.Position;
import com.gladiator.model.menu.GameOverModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameOverViewerTest {
    private GameOverViewer viewer;
    private GameOverModel model;
    private GUI gui;

    @BeforeEach
    void setUp() {
        gui = mock(GUI.class);
    }

    @Test
    void testDrawWithWonAndMenuSelected() throws IOException {
        model = new GameOverModel(true);
        model.setSelected(GameOverModel.Option.MENU);
        viewer = new GameOverViewer(model);
        
        viewer.draw(gui);
        
        verify(gui).clear();
        verify(gui).drawSprite("sprites/gameOver/win_menu.png", new Position(0, 0));
        verify(gui).refresh();
    }

    @Test
    void testDrawWithWonAndQuitSelected() throws IOException {
        model = new GameOverModel(true);
        model.setSelected(GameOverModel.Option.QUIT);
        viewer = new GameOverViewer(model);
        
        viewer.draw(gui);
        
        verify(gui).clear();
        verify(gui).drawSprite("sprites/gameOver/win_quit.png", new Position(0, 0));
        verify(gui).refresh();
    }

    @Test
    void testDrawWithLostAndMenuSelected() throws IOException {
        model = new GameOverModel(false);
        model.setSelected(GameOverModel.Option.MENU);
        viewer = new GameOverViewer(model);
        
        viewer.draw(gui);
        
        verify(gui).clear();
        verify(gui).drawSprite("sprites/gameOver/lose_menu.png", new Position(0, 0));
        verify(gui).refresh();
    }

    @Test
    void testDrawWithLostAndQuitSelected() throws IOException {
        model = new GameOverModel(false);
        model.setSelected(GameOverModel.Option.QUIT);
        viewer = new GameOverViewer(model);
        
        viewer.draw(gui);
        
        verify(gui).clear();
        verify(gui).drawSprite("sprites/gameOver/lose_quit.png", new Position(0, 0));
        verify(gui).refresh();
    }

    @Test
    void testDrawWithWonDefaultSelection() throws IOException {
        model = new GameOverModel(true);
        viewer = new GameOverViewer(model);
        
        viewer.draw(gui);
        
        verify(gui).drawSprite("sprites/gameOver/win_menu.png", new Position(0, 0));
    }

    @Test
    void testDrawWithLostDefaultSelection() throws IOException {
        model = new GameOverModel(false);
        viewer = new GameOverViewer(model);
        
        viewer.draw(gui);
        
        verify(gui).drawSprite("sprites/gameOver/lose_menu.png", new Position(0, 0));
    }

    @Test
    void testGetModel() {
        model = new GameOverModel(true);
        viewer = new GameOverViewer(model);
        
        assertSame(model, viewer.getModel());
    }

    @Test
    void testDrawCallsClearAndRefresh() throws IOException {
        model = new GameOverModel(true);
        viewer = new GameOverViewer(model);
        
        viewer.draw(gui);
        
        verify(gui).clear();
        verify(gui).refresh();
    }

    @Test
    void testDrawWithIOException() throws IOException {
        model = new GameOverModel(true);
        viewer = new GameOverViewer(model);
        
        doThrow(new IOException("Test exception")).when(gui).drawSprite(anyString(), any(Position.class));
        
        assertThrows(IOException.class, () -> viewer.draw(gui));
    }
}

