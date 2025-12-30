package com.gladiator.controller.menu;

import com.gladiator.gui.GUI;
import com.gladiator.model.menu.MenuModel;
import com.gladiator.view.menu.MenuViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuControllerTest {
    private MenuController menuController;
    private MenuViewer viewer;
    private MenuModel model;
    private GUI gui;

    @BeforeEach
    void setUp() {
        model = new MenuModel();
        viewer = mock(MenuViewer.class);
        when(viewer.getModel()).thenReturn(model);
        gui = mock(GUI.class);
        menuController = new MenuController(viewer);
    }

    @Test
    void testInitialState() {
        assertNull(menuController.getSelectionResult());
        assertTrue(menuController.running);
    }

    @Test
    void testProcessInputUp() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.UP);
        
        menuController.processInput(gui);
        
        assertEquals(MenuModel.Option.EXIT, model.getSelected());
    }

    @Test
    void testProcessInputDown() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.DOWN);
        
        menuController.processInput(gui);
        
        assertEquals(MenuModel.Option.CREDITS, model.getSelected());
    }

    @Test
    void testProcessInputSelectPlay() throws IOException {
        model.setSelected(MenuModel.Option.PLAY);
        when(gui.getNextAction()).thenReturn(GUI.ACTION.SELECT);
        
        menuController.processInput(gui);
        
        assertEquals("PLAY", menuController.getSelectionResult());
        assertFalse(menuController.running);
    }

    @Test
    void testProcessInputSelectCredits() throws IOException {
        model.setSelected(MenuModel.Option.CREDITS);
        when(gui.getNextAction()).thenReturn(GUI.ACTION.SELECT);
        
        menuController.processInput(gui);
        
        assertEquals("CREDITS", menuController.getSelectionResult());
        assertFalse(menuController.running);
    }

    @Test
    void testProcessInputSelectExit() throws IOException {
        model.setSelected(MenuModel.Option.EXIT);
        when(gui.getNextAction()).thenReturn(GUI.ACTION.SELECT);
        
        menuController.processInput(gui);
        
        assertEquals("EXIT", menuController.getSelectionResult());
        assertFalse(menuController.running);
    }

    @Test
    void testProcessInputQuit() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.QUIT);
        
        menuController.processInput(gui);
        
        assertFalse(menuController.running);
        assertNull(menuController.getSelectionResult());
    }

    @Test
    void testDraw() throws IOException {
        menuController.draw(gui);
        
        verify(viewer).draw(gui);
    }

    @Test
    void testUpdate() {
        menuController.update();
    }

    @Test
    void testProcessInputNone() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.NONE);
        
        menuController.processInput(gui);
        
        // Should not change selection or stop
        assertTrue(menuController.running);
    }

    @Test
    void testProcessInputLeft() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.LEFT);
        
        menuController.processInput(gui);
        
        // LEFT is not handled, so should remain at default (PLAY)
        assertEquals(MenuModel.Option.PLAY, model.getSelected());
    }

    @Test
    void testProcessInputRight() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.RIGHT);
        
        menuController.processInput(gui);
        
        // RIGHT is not handled, so should remain at default (PLAY)
        assertEquals(MenuModel.Option.PLAY, model.getSelected());
    }

    @Test
    void testMultipleNavigation() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.DOWN);
        menuController.processInput(gui);
        assertEquals(MenuModel.Option.CREDITS, model.getSelected());
        
        when(gui.getNextAction()).thenReturn(GUI.ACTION.DOWN);
        menuController.processInput(gui);
        assertEquals(MenuModel.Option.EXIT, model.getSelected());
        
        when(gui.getNextAction()).thenReturn(GUI.ACTION.DOWN);
        menuController.processInput(gui);
        assertEquals(MenuModel.Option.PLAY, model.getSelected());
    }
}

