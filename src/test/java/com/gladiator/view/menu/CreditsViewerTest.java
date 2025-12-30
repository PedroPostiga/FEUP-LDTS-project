package com.gladiator.view.menu;

import com.gladiator.gui.GUI;
import com.gladiator.model.component.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreditsViewerTest {
    private CreditsViewer creditsViewer;
    private GUI gui;

    @BeforeEach
    void setUp() {
        creditsViewer = new CreditsViewer();
        gui = mock(GUI.class);
    }

    @Test
    void testDraw() throws IOException {
        creditsViewer.draw(gui);
        
        verify(gui).clear();
        verify(gui).drawSprite("sprites/menu/credits_screen.png", new Position(0, 0));
        verify(gui).refresh();
    }

    @Test
    void testGetModel() {
        assertNull(creditsViewer.getModel());
    }

    @Test
    void testDrawCallsClearAndRefresh() throws IOException {
        creditsViewer.draw(gui);
        
        verify(gui).clear();
        verify(gui).refresh();
    }

    @Test
    void testDrawWithIOException() throws IOException {
        doThrow(new IOException("Test exception")).when(gui).drawSprite(anyString(), any(Position.class));
        
        assertThrows(IOException.class, () -> creditsViewer.draw(gui));
    }

    @Test
    void testDrawMultipleTimes() throws IOException {
        creditsViewer.draw(gui);
        creditsViewer.draw(gui);
        
        verify(gui, times(2)).clear();
        verify(gui, times(2)).drawSprite("sprites/menu/credits_screen.png", new Position(0, 0));
        verify(gui, times(2)).refresh();
    }
}

