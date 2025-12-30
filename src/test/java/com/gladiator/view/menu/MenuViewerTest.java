package com.gladiator.view.menu;

import com.gladiator.gui.GUI;
import com.gladiator.model.component.Position;
import com.gladiator.model.menu.MenuModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuViewerTest {
    private MenuViewer menuViewer;
    private MenuModel menuModel;
    private GUI gui;

    @BeforeEach
    void setUp() {
        menuModel = new MenuModel();
        menuViewer = new MenuViewer(menuModel);
        gui = mock(GUI.class);
    }

    @Test
    void testDrawWithPlaySelected() throws IOException {
        menuModel.setSelected(MenuModel.Option.PLAY);
        menuViewer.draw(gui);
        
        verify(gui).clear();
        verify(gui).drawSprite("sprites/menu/menu_startgame.png", new Position(0, 0));
        verify(gui).refresh();
    }

    @Test
    void testDrawWithCreditsSelected() throws IOException {
        menuModel.setSelected(MenuModel.Option.CREDITS);
        menuViewer.draw(gui);
        
        verify(gui).clear();
        verify(gui).drawSprite("sprites/menu/menu_credits.png", new Position(0, 0));
        verify(gui).refresh();
    }

    @Test
    void testDrawWithExitSelected() throws IOException {
        menuModel.setSelected(MenuModel.Option.EXIT);
        menuViewer.draw(gui);
        
        verify(gui).clear();
        verify(gui).drawSprite("sprites/menu/menu_exit.png", new Position(0, 0));
        verify(gui).refresh();
    }

    @Test
    void testDrawWithDefaultSelection() throws IOException {
        // Default should be PLAY
        menuViewer.draw(gui);
        
        verify(gui).clear();
        verify(gui).drawSprite("sprites/menu/menu_startgame.png", new Position(0, 0));
        verify(gui).refresh();
    }

    @Test
    void testGetModel() {
        assertSame(menuModel, menuViewer.getModel());
    }

    @Test
    void testDrawCallsClearAndRefresh() throws IOException {
        menuViewer.draw(gui);
        
        verify(gui).clear();
        verify(gui).refresh();
    }

    @Test
    void testDrawWithIOException() throws IOException {
        doThrow(new IOException("Test exception")).when(gui).drawSprite(anyString(), any(Position.class));
        
        assertThrows(IOException.class, () -> menuViewer.draw(gui));
    }
}

