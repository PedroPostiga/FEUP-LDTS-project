package com.gladiator.view;

import com.gladiator.gui.GUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ViewerTest {
    private TestViewer viewer;
    private GUI gui;
    private String testModel;

    @BeforeEach
    void setUp() {
        testModel = "TestModel";
        viewer = new TestViewer(testModel);
        gui = mock(GUI.class);
    }

    @Test
    void testGetModel() {
        assertEquals(testModel, viewer.getModel());
    }

    @Test
    void testDraw() throws IOException {
        viewer.draw(gui);
        
        verify(gui).clear();
        verify(gui).refresh();
        assertTrue(viewer.drawElementsCalled);
    }

    @Test
    void testDrawWithIOException() throws IOException {
        doThrow(new IOException("Test exception")).when(gui).refresh();
        
        assertThrows(IOException.class, () -> viewer.draw(gui));
        verify(gui).clear();
    }

    @Test
    void testDrawWithIOExceptionInDrawElements() throws IOException {
        viewer.throwExceptionInDrawElements = true;
        
        assertThrows(IOException.class, () -> viewer.draw(gui));
        verify(gui).clear();
    }

    // Test implementation of abstract Viewer
    private static class TestViewer extends Viewer<String> {
        boolean drawElementsCalled = false;
        boolean throwExceptionInDrawElements = false;

        public TestViewer(String model) {
            super(model);
        }

        @Override
        protected void drawElements(GUI gui) throws IOException {
            drawElementsCalled = true;
            if (throwExceptionInDrawElements) {
                throw new IOException("Test exception");
            }
        }
    }
}

