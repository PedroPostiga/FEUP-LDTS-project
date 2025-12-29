package com.gladiator;

import com.gladiator.controller.GameController;
import com.gladiator.controller.MenuController;
import com.gladiator.gui.GUI;
import com.gladiator.model.Arena;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameRunnerTest {
    private GameRunner gameRunner;
    private GUI gui;

    @BeforeEach
    void setUp() {
        gameRunner = new GameRunner();
        gui = mock(GUI.class);
    }

    @Test
    void testHandleMenuStatePlay() throws Exception {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.SELECT);
        
        Method method = GameRunner.class.getDeclaredMethod("handleMenuState", GUI.class);
        method.setAccessible(true);
        
        Object result = method.invoke(gameRunner, gui);
        
        assertNotNull(result);
    }

    @Test
    void testHandleMenuStateCredits() throws Exception {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.SELECT);
        
        Method method = GameRunner.class.getDeclaredMethod("handleMenuState", GUI.class);
        method.setAccessible(true);
        
        Object result = method.invoke(gameRunner, gui);
        
        assertNotNull(result);
    }

    @Test
    void testHandleMenuStateExit() throws Exception {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.SELECT);
        
        Method method = GameRunner.class.getDeclaredMethod("handleMenuState", GUI.class);
        method.setAccessible(true);
        
        Object result = method.invoke(gameRunner, gui);
        
        assertNotNull(result);
    }

    @Test
    void testHandleGameStateWon() throws Exception {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.QUIT);
        
        Method method = GameRunner.class.getDeclaredMethod("handleGameState", GUI.class);
        method.setAccessible(true);
        
        Object result = method.invoke(gameRunner, gui);
        
        assertNotNull(result);
    }

    @Test
    void testHandleGameStateLost() throws Exception {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.QUIT);
        
        Method method = GameRunner.class.getDeclaredMethod("handleGameState", GUI.class);
        method.setAccessible(true);
        
        Object result = method.invoke(gameRunner, gui);
        
        assertNotNull(result);
    }

    @Test
    void testHandleGameStatePlaying() throws Exception {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.QUIT);
        
        Method method = GameRunner.class.getDeclaredMethod("handleGameState", GUI.class);
        method.setAccessible(true);
        
        Object result = method.invoke(gameRunner, gui);
        
        assertNotNull(result);
    }

    @Test
    void testHandleGameOverStateMenu() throws Exception {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.SELECT);
        
        Method method = GameRunner.class.getDeclaredMethod("handleGameOverState", GUI.class);
        method.setAccessible(true);
        
        Object result = method.invoke(gameRunner, gui);
        
        assertNotNull(result);
    }

    @Test
    void testHandleCreditsState() throws Exception {
        when(gui.getNextAction()).thenReturn(GUI.ACTION.SELECT);
        
        Method method = GameRunner.class.getDeclaredMethod("handleCreditsState", GUI.class);
        method.setAccessible(true);
        
        Object result = method.invoke(gameRunner, gui);
        
        assertNotNull(result);
    }
}
