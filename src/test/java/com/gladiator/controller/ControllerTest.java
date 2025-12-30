package com.gladiator.controller;

import com.gladiator.gui.GUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerTest {
    private TestController controller;
    private GUI gui;

    @BeforeEach
    void setUp() {
        controller = new TestController(60);
        gui = mock(GUI.class);
    }

    @Test
    void testInitialState() {
        assertTrue(controller.running);
        assertNotNull(controller.timer);
    }

    @Test
    void testStop() {
        controller.stop();
        
        assertFalse(controller.running);
    }

    @Test
    void testRunCallsProcessInput() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.NONE);
        controller.running = true;
        
        // Run in a separate thread to avoid blocking
        Thread thread = new Thread(() -> {
            try {
                controller.run(gui);
            } catch (IOException e) {
                // Expected when we stop
            }
        });
        thread.start();
        
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        controller.stop();
        
        try {
            thread.join(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        verify(gui, atLeastOnce()).getNextAction();
    }

    @Test
    void testRunCallsUpdateWhenTimerTicks() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.NONE);
        controller.running = true;
        
        Thread thread = new Thread(() -> {
            try {
                controller.run(gui);
            } catch (IOException e) {
                // Expected when we stop
            }
        });
        thread.start();
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        controller.stop();
        
        try {
            thread.join(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertTrue(controller.updateCalled);
    }

    @Test
    void testRunCallsDraw() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.NONE);
        controller.running = true;
        
        Thread thread = new Thread(() -> {
            try {
                controller.run(gui);
            } catch (IOException e) {
                // Expected when we stop
            }
        });
        thread.start();
        
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        controller.stop();
        
        try {
            thread.join(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        verify(gui, atLeastOnce()).clear();
    }

    @Test
    void testRunStopsWhenRunningIsFalse() throws IOException {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.NONE);
        controller.running = true;
        
        Thread thread = new Thread(() -> {
            try {
                controller.run(gui);
            } catch (IOException e) {
                // Expected
            }
        });
        thread.start();
        
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        controller.stop();
        
        try {
            thread.join(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertFalse(controller.running);
        assertFalse(thread.isAlive());
    }

    @Test
    void testRunHandlesIOException() throws IOException {
        when(gui.getNextAction()).thenThrow(new IOException("Test exception"));
        controller.running = true;
        
        assertThrows(IOException.class, () -> controller.run(gui));
    }

    @Test
    void testRunWithDifferentTickRates() {
        TestController fastController = new TestController(120);
        TestController slowController = new TestController(30);
        
        assertNotNull(fastController.timer);
        assertNotNull(slowController.timer);
    }

    // Test implementation of abstract Controller
    private static class TestController extends Controller {
        boolean updateCalled = false;
        boolean drawCalled = false;

        public TestController(int ticksPerSecond) {
            super(ticksPerSecond);
        }

        @Override
        protected void processInput(GUI gui) throws IOException {
            gui.getNextAction();
        }

        @Override
        protected void update() {
            updateCalled = true;
        }

        @Override
        protected void draw(GUI gui) throws IOException {
            drawCalled = true;
            gui.clear();
            gui.refresh();
        }
    }
}

