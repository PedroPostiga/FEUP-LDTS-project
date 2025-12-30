package com.gladiator.controller.menu;

import com.gladiator.gui.GUI;
import com.gladiator.view.menu.CreditsViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreditsControllerTest {
    private CreditsController controller;
    private CreditsViewer viewer;
    private GUI gui;

    @BeforeEach
    void setUp() {
        viewer = mock(CreditsViewer.class);
        gui = mock(GUI.class);
        controller = new CreditsController(viewer);
    }

    @Test
    void testInitialState() {
        assertTrue(controller.running);
    }

    @Test
    void testProcessInputAnyActionStops() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.UP);
        
        controller.processInput(gui);
        
        assertFalse(controller.running);
    }

    @Test
    void testProcessInputSelectStops() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.SELECT);
        
        controller.processInput(gui);
        
        assertFalse(controller.running);
    }

    @Test
    void testProcessInputQuitStops() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.QUIT);
        
        controller.processInput(gui);
        
        assertFalse(controller.running);
    }

    @Test
    void testProcessInputNoneDoesNotStop() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.NONE);
        
        controller.processInput(gui);
        
        assertTrue(controller.running);
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

    @Test
    void testProcessInputLeft() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.LEFT);
        
        controller.processInput(gui);
        
        assertFalse(controller.running);
    }

    @Test
    void testProcessInputRight() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.RIGHT);
        
        controller.processInput(gui);
        
        assertFalse(controller.running);
    }

    @Test
    void testProcessInputDown() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.DOWN);
        
        controller.processInput(gui);
        
        assertFalse(controller.running);
    }

    @Test
    void testProcessInputUp() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.UP);
        
        controller.processInput(gui);
        
        assertFalse(controller.running);
    }
}

