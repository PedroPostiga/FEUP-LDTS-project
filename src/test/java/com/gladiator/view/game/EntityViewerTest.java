package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.enemy_types.FatZombie;
import com.gladiator.model.enemy.enemy_types.LightZombie;
import com.gladiator.model.enemy.enemy_types.Vampire;
import com.gladiator.model.gladiator.Gladiator;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.mockito.Mockito.*;

public class EntityViewerTest {

    @Test
    public void testGladiatorViewerDraw() throws IOException {
        GUI gui = mock(GUI.class);
        GladiatorViewer viewer = new GladiatorViewer();
        Gladiator gladiator = mock(Gladiator.class);
        Position position = mock(Position.class);

        when(gladiator.getPosition()).thenReturn(position);

        viewer.draw(gladiator, gui);

        verify(gui).drawGladiator(position);
    }

    @Test
    public void testFatZombieViewerDraw() throws IOException {
        GUI gui = mock(GUI.class);
        FatZombieViewer viewer = new FatZombieViewer();
        FatZombie fatZombie = mock(FatZombie.class);
        Position position = mock(Position.class);

        when(fatZombie.getPosition()).thenReturn(position);

        viewer.draw(fatZombie, gui);

        verify(gui).drawFatZombie(position);
    }

    @Test
    public void testLightZombieViewerDraw() throws IOException {
        GUI gui = mock(GUI.class);
        LightZombieViewer viewer = new LightZombieViewer();
        LightZombie lightZombie = mock(LightZombie.class);
        Position position = mock(Position.class);

        when(lightZombie.getPosition()).thenReturn(position);

        viewer.draw(lightZombie, gui);

        verify(gui).drawLightZombie(position);
    }

    @Test
    public void testVampireViewerDraw() {
        GUI gui = mock(GUI.class);
        VampireViewer viewer = new VampireViewer();
        Vampire vampire = mock(Vampire.class);
        Position position = mock(Position.class);

        when(vampire.getPosition()).thenReturn(position);

        viewer.draw(vampire, gui);

        verify(gui).drawVampire(position);
    }

    @Test
    public void testVampireViewerDoesNotThrowIOException() {
        GUI gui = mock(GUI.class);
        VampireViewer viewer = new VampireViewer();
        Vampire vampire = mock(Vampire.class);
        Position position = mock(Position.class);

        when(vampire.getPosition()).thenReturn(position);

        viewer.draw(vampire, gui);

        verify(gui).drawVampire(position);
    }
}