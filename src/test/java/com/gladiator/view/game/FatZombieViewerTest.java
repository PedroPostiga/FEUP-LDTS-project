package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.enemy.enemy_types.FatZombie;
import com.gladiator.model.movement.ChaseMovement;
import com.gladiator.model.gladiator.Gladiator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FatZombieViewerTest {
    private FatZombieViewer viewer;
    private FatZombie fatZombie;
    private GUI gui;
    private AttackStrategy attackStrategy;

    @BeforeEach
    void setUp() {
        viewer = new FatZombieViewer();
        gui = mock(GUI.class);
        
        Gladiator gladiator = mock(Gladiator.class);
        attackStrategy = mock(SwordAttack.class);
        fatZombie = new FatZombie(100, 100, 
            new ChaseMovement(1.0, gladiator),
            new SwordAttack(12, 30, new ArrayList<>()));
        fatZombie.setAttack(attackStrategy);
    }

    @Test
    void testDrawWhenNotAttacking() throws IOException {
        when(attackStrategy.isAttacking(fatZombie)).thenReturn(false);
        
        viewer.draw(fatZombie, gui);
        
        verify(gui).drawSprite("sprites/movingEntity/fat_zombie.png", fatZombie.getPosition());
    }

    @Test
    void testDrawWhenAttacking() throws IOException {
        when(attackStrategy.isAttacking(fatZombie)).thenReturn(true);
        
        viewer.draw(fatZombie, gui);
        
        verify(gui).drawSprite("sprites/movingEntity/fat_zombie_attack.png", fatZombie.getPosition());
    }

    @Test
    void testDrawWithNullAttackStrategy() throws IOException {
        fatZombie.setAttack(null);
        
        viewer.draw(fatZombie, gui);
        
        verify(gui).drawSprite("sprites/movingEntity/fat_zombie.png", fatZombie.getPosition());
    }

    @Test
    void testDrawWithIOException() throws IOException {
        when(attackStrategy.isAttacking(fatZombie)).thenReturn(false);
        doThrow(new IOException("Test exception")).when(gui).drawSprite(anyString(), any());
        
        assertThrows(IOException.class, () -> viewer.draw(fatZombie, gui));
    }

    @Test
    void testDrawMultipleTimes() throws IOException {
        when(attackStrategy.isAttacking(fatZombie)).thenReturn(false);
        
        viewer.draw(fatZombie, gui);
        viewer.draw(fatZombie, gui);
        
        verify(gui, times(2)).drawSprite("sprites/movingEntity/fat_zombie.png", fatZombie.getPosition());
    }
}

