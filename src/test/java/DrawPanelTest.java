import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DrawPanelTest {

    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 600;
    private static final int LONG_DELAY_MS = 100_000;

    private static final double SIZE_RATIO = 0.7;
    private static final double ROTATION_SPEED = 0.7;
    private static final double ROTATION_AMPLITUDE = 10.0;
    private static final double EPSILON = 0.0001;

    private DrawPanel panel;

    @BeforeEach
    void setUp() throws InvocationTargetException, InterruptedException {
        // Swing-компоненты должны создаваться в Event Dispatch Thread
        SwingUtilities.invokeAndWait(() ->
                panel = new DrawPanel(PANEL_WIDTH, PANEL_HEIGHT, LONG_DELAY_MS));
    }

    @Test
    @DisplayName("Панель создаётся и содержит кота")
    void panelCreatesCat() {
        assertNotNull(panel);
        assertNotNull(panel.getCat(), "Кот должен быть создан в конструкторе");
    }

    @Test
    @DisplayName("Кот занимает ~70% ширины и высоты панели")
    void catTakesSeventyPercent() {
        final Cat cat = panel.getCat();
        assertEquals((int) (PANEL_WIDTH * SIZE_RATIO), cat.getWidth());
        assertEquals((int) (PANEL_HEIGHT * SIZE_RATIO), cat.getHeight());
    }

    @Test
    @DisplayName("actionPerformed считает угол по формуле sin(ticks * 0.7) * 10")
    void actionPerformedComputesAngleByFormula() {
        final Cat cat = panel.getCat();
        final Timer timer = panel.getTimer();

        // тик №1: ticksFromStart = 0 → sin(0) = 0
        panel.actionPerformed(new ActionEvent(timer,
                ActionEvent.ACTION_PERFORMED, "tick"));
        assertEquals(0.0, cat.getAngle(), EPSILON);

        // тик №2: ticksFromStart = 1 → sin(0.7) * 10
        panel.actionPerformed(new ActionEvent(timer,
                ActionEvent.ACTION_PERFORMED, "tick"));
        assertEquals(Math.sin(1 * ROTATION_SPEED) * ROTATION_AMPLITUDE,
                cat.getAngle(), EPSILON);

        // тик №3: ticksFromStart = 2 → sin(1.4) * 10
        panel.actionPerformed(new ActionEvent(timer,
                ActionEvent.ACTION_PERFORMED, "tick"));
        assertEquals(Math.sin(2 * ROTATION_SPEED) * ROTATION_AMPLITUDE,
                cat.getAngle(), EPSILON);
    }

    @Test
    @DisplayName("Угол остаётся в пределах [-10, 10] градусов")
    void angleStaysInRange() {
        final Cat cat = panel.getCat();
        final Timer timer = panel.getTimer();

        for (int i = 0; i < 100; i++) {
            panel.actionPerformed(new ActionEvent(timer,
                    ActionEvent.ACTION_PERFORMED, "tick"));

            final double angle = cat.getAngle();
            assertTrue(angle >= -ROTATION_AMPLITUDE && angle <= ROTATION_AMPLITUDE,
                    "Угол " + angle + " вне диапазона [-10, 10]");
        }
    }

    @Test
    @DisplayName("Многократные вызовы actionPerformed не падают")
    void repeatedTicksDoNotThrow() {
        final Timer timer = panel.getTimer();

        assertDoesNotThrow(() -> {
            for (int i = 0; i < 500; i++) {
                panel.actionPerformed(new ActionEvent(timer,
                        ActionEvent.ACTION_PERFORMED, "tick"));
            }
        });
    }

    @Test
    @DisplayName("Событие от чужого источника игнорируется")
    void foreignEventIgnored() {
        final Cat cat = panel.getCat();
        final double before = cat.getAngle();

        final Object foreignSource = new Object();
        panel.actionPerformed(new ActionEvent(foreignSource,
                ActionEvent.ACTION_PERFORMED, "tick"));

        assertEquals(before, cat.getAngle(), EPSILON,
                "Событие от чужого источника не должно менять угол");
    }
}