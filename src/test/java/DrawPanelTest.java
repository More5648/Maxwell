import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

class DrawPanelTest {

    private DrawPanel panel;

    @BeforeEach
    void setUp() {
        // Большой delay, чтобы реальный таймер не срабатывал во время теста
        panel = new DrawPanel(800, 600, 100_000);
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
        Cat cat = panel.getCat();
        assertEquals((int) (800 * 0.7), cat.getWidth());
        assertEquals((int) (600 * 0.7), cat.getHeight());
    }

    @Test
    @DisplayName("actionPerformed меняет угол кота")
    void actionPerformedChangesAngle() {
        Cat cat = panel.getCat();
        double before = cat.getAngle();

        panel.actionPerformed(new ActionEvent(panel,
                ActionEvent.ACTION_PERFORMED, "tick"));

        double after = cat.getAngle();
        assertNotEquals(before, after, "Угол должен измениться после тика");
    }

    @Test
    @DisplayName("Угол остаётся в пределах [-10, 10] градусов")
    void angleStaysInRange() {
        Cat cat = panel.getCat();

        for (int i = 0; i < 100; i++) {
            panel.actionPerformed(new ActionEvent(panel,
                    ActionEvent.ACTION_PERFORMED, "tick"));

            double angle = cat.getAngle();
            assertTrue(angle >= -10.0 && angle <= 10.0,
                    "Угол " + angle + " вне диапазона [-10, 10]");
        }
    }

    @Test
    @DisplayName("Многократные вызовы actionPerformed не падают")
    void repeatedTicksDoNotThrow() {
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 500; i++) {
                panel.actionPerformed(new ActionEvent(panel,
                        ActionEvent.ACTION_PERFORMED, "tick"));
            }
        });
    }

    @Test
    @DisplayName("Событие от чужого источника игнорируется")
    void foreignEventIgnored() {
        Cat cat = panel.getCat();
        double before = cat.getAngle();

        Object foreignSource = new Object();
        panel.actionPerformed(new ActionEvent(foreignSource,
                ActionEvent.ACTION_PERFORMED, "tick"));

        assertEquals(before, cat.getAngle(), 0.0001,
                "Событие от чужого источника не должно менять угол");
    }
}