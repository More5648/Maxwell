import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class CatTest {

    private Cat cat;

    @BeforeEach
    void setUp() {
        cat = new Cat(100, 100, 645, 470, Color.BLACK);
    }

    @Test
    @DisplayName("Конструктор сохраняет ширину и высоту")
    void constructorStoresSize() {
        assertEquals(645, cat.getWidth());
        assertEquals(470, cat.getHeight());
    }

    @Test
    @DisplayName("setWidth / setHeight меняют размеры")
    void settersChangeSize() {
        cat.setWidth(300);
        cat.setHeight(200);
        assertEquals(300, cat.getWidth());
        assertEquals(200, cat.getHeight());
    }

    @Test
    @DisplayName("setX / setY не выбрасывают исключений")
    void settersCoordinatesDoNotThrow() {
        assertDoesNotThrow(() -> {
            cat.setX(0);
            cat.setY(0);
            cat.setX(-100);
            cat.setY(9999);
        });
    }

    @Test
    @DisplayName("Начальный угол равен 0")
    void initialAngleIsZero() {
        assertEquals(0.0, cat.getAngle(), 0.0001);
    }

    @Test
    @DisplayName("setAngle сохраняет значение")
    void setAngleStoresValue() {
        cat.setAngle(45.5);
        assertEquals(45.5, cat.getAngle(), 0.0001);
    }

    @Test
    @DisplayName("setAngle принимает любые значения без ошибок")
    void setAngleAcceptsAnyValue() {
        assertDoesNotThrow(() -> {
            cat.setAngle(0);
            cat.setAngle(360);
            cat.setAngle(-90);
            cat.setAngle(1e9);
        });
    }

    @Test
    @DisplayName("draw() не выбрасывает исключений")
    void drawDoesNotThrow() {
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() -> cat.draw(g));

        g.dispose();
    }

    @Test
    @DisplayName("draw() рисует непрозрачные пиксели")
    void drawProducesOpaquePixels() {
        Cat big = new Cat(50, 50, 645, 470, Color.BLACK);
        BufferedImage image = new BufferedImage(900, 700, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        big.draw(g);
        g.dispose();

        assertTrue(hasOpaquePixel(image),
                "После draw() должны быть непрозрачные пиксели");
    }

    @Test
    @DisplayName("draw() работает при нулевых размерах")
    void drawWithZeroSize() {
        Cat zero = new Cat(0, 0, 0, 0, Color.BLACK);
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() -> zero.draw(g));

        g.dispose();
    }

    @Test
    @DisplayName("draw() работает с отрицательными координатами")
    void drawWithNegativeCoordinates() {
        Cat neg = new Cat(-100, -100, 645, 470, Color.BLACK);
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() -> neg.draw(g));

        g.dispose();
    }

    @Test
    @DisplayName("draw() восстанавливает трансформацию Graphics2D")
    void drawRestoresTransform() {
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        var before = g.getTransform();

        cat.setAngle(30);
        cat.draw(g);

        assertEquals(before, g.getTransform(),
                "draw() должен восстанавливать исходную трансформацию");
        g.dispose();
    }

    @Test
    @DisplayName("draw() при разных углах не падает")
    void drawWithVariousAngles() {
        BufferedImage image = new BufferedImage(900, 700, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() -> {
            for (double angle = -180; angle <= 180; angle += 15) {
                cat.setAngle(angle);
                cat.draw(g);
            }
        });

        g.dispose();
    }

    private boolean hasOpaquePixel(BufferedImage image) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if ((image.getRGB(x, y) >>> 24) != 0) {
                    return true;
                }
            }
        }
        return false;
    }
}