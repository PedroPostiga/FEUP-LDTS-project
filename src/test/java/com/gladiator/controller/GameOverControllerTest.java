package com.gladiator.controller;

import com.gladiator.gui.GUI;
import com.gladiator.model.menu.GameOverModel;
import com.gladiator.view.menu.GameOverViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameOverControllerTest {
    private GameOverController controller;
    private GameOverViewer viewer;
    private GameOverModel model;
    private GUI gui;

    @BeforeEach
    void setUp() {
        model = new GameOverModel(false);
        viewer = mock(GameOverViewer.class);
        when(viewer.getModel()).thenReturn(model);
        gui = mock(GUI.class);
        controller = new GameOverController(viewer);
    }

    @Test
    void testInitialState() {
        assertNull(controller.getSelectionResult());
        assertTrue(controller.running);
    }

    @Test
    void testProcessInputUp() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.UP);
        
        controller.processInput(gui);
        
        assertEquals(GameOverModel.Option.QUIT, model.getSelected());
    }

    @Test
    void testProcessInputDown() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.DOWN);
        
        controller.processInput(gui);
        
        assertEquals(GameOverModel.Option.QUIT, model.getSelected());
    }

    @Test
    void testProcessInputSelectMenu() throws IOException {
        model.setSelected(GameOverModel.Option.MENU);
        when(gui.getNextAction()).thenReturn(GUI.ACTION.SELECT);
        
        controller.processInput(gui);
        
        assertEquals("MENU", controller.getSelectionResult());
        assertFalse(controller.running);
    }

    @Test
    void testProcessInputSelectQuit() throws IOException {
        model.setSelected(GameOverModel.Option.QUIT);
        when(gui.getNextAction()).thenReturn(GUI.ACTION.SELECT);
        
        controller.processInput(gui);
        
        assertEquals("QUIT", controller.getSelectionResult());
        assertFalse(controller.running);
    }

    @Test
    void testProcessInputQuitAction() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.QUIT);
        
        controller.processInput(gui);
        
        assertEquals("QUIT", controller.getSelectionResult());
        assertFalse(controller.running);
    }

    @Test
    void testDraw() throws IOException {
        controller.draw(gui);
        
        verify(viewer).draw(gui);
    }

    @Test
    void testUpdate() {
        controller.update();
    }
}

