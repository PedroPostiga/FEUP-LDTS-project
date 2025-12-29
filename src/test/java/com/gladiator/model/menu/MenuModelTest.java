package com.gladiator.model.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuModelTest {
    private MenuModel menuModel;

    @BeforeEach
    void setUp() {
        menuModel = new MenuModel();
    }

    @Test
    void testInitialSelected() {
        assertEquals(MenuModel.Option.PLAY, menuModel.getSelected());
    }

    @Test
    void testSetSelected() {
        menuModel.setSelected(MenuModel.Option.CREDITS);
        assertEquals(MenuModel.Option.CREDITS, menuModel.getSelected());
        
        menuModel.setSelected(MenuModel.Option.EXIT);
        assertEquals(MenuModel.Option.EXIT, menuModel.getSelected());
    }

    @Test
    void testNextSelected() {
        assertEquals(MenuModel.Option.PLAY, menuModel.getSelected());
        
        menuModel.nextSelected();
        assertEquals(MenuModel.Option.CREDITS, menuModel.getSelected());
        
        menuModel.nextSelected();
        assertEquals(MenuModel.Option.EXIT, menuModel.getSelected());
        
        menuModel.nextSelected();
        assertEquals(MenuModel.Option.PLAY, menuModel.getSelected());
    }

    @Test
    void testPreviousSelected() {
        assertEquals(MenuModel.Option.PLAY, menuModel.getSelected());
        
        menuModel.previousSelected();
        assertEquals(MenuModel.Option.EXIT, menuModel.getSelected());
        
        menuModel.previousSelected();
        assertEquals(MenuModel.Option.CREDITS, menuModel.getSelected());
        
        menuModel.previousSelected();
        assertEquals(MenuModel.Option.PLAY, menuModel.getSelected());
    }

    @Test
    void testCyclingThroughOptions() {
        menuModel.setSelected(MenuModel.Option.PLAY);
        menuModel.nextSelected();
        assertEquals(MenuModel.Option.CREDITS, menuModel.getSelected());
        
        menuModel.nextSelected();
        assertEquals(MenuModel.Option.EXIT, menuModel.getSelected());
        
        menuModel.nextSelected();
        assertEquals(MenuModel.Option.PLAY, menuModel.getSelected());
    }
}

