import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CatTest {

    private static final int CAT_X = 100;
    private static final int CAT_Y = 100;
    private static final int CAT_WIDTH = 645;
    private static final int CAT_HEIGHT = 470;

    private static final int IMAGE_WIDTH = 900;
    private static final int IMAGE_HEIGHT = 700;

    private static final double EPSILON = 0.0001;

    private Cat cat;

    @BeforeEach
    void setUp() {
        cat = new Cat(CAT_X, CAT_Y, CAT_WIDTH, CAT_HEIGHT);
    }

    @Test
    @DisplayName("Начальный угол равен 0")
    void initialAngleIsZero() {
        assertEquals(0.0, cat.getAngle(), EPSILON);
    }

    @Test
    @DisplayName("setAngle сохраняет значение")
    void setAngleStoresValue() {
        cat.setAngle(45.5);
        assertEquals(45.5, cat.getAngle(), EPSILON);
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
        final BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() -> cat.draw(g));

        g.dispose();
    }

    @Test
    @DisplayName("draw() рисует непрозрачные пиксели")
    void drawProducesOpaquePixels() {
        final BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();
        cat.draw(g);
        g.dispose();

        assertTrue(hasOpaquePixel(image),
                "После draw() должны быть непрозрачные пиксели");
    }

    @Test
    @DisplayName("draw() работает при нулевых размерах")
    void drawWithZeroSize() {
        final Cat zero = new Cat(0, 0, 0, 0);
        final BufferedImage image =
                new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() -> zero.draw(g));

        g.dispose();
    }

    @Test
    @DisplayName("draw() работает с отрицательными координатами")
    void drawWithNegativeCoordinates() {
        final Cat negative = new Cat(-100, -100, CAT_WIDTH, CAT_HEIGHT);
        final BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() -> negative.draw(g));

        g.dispose();
    }

    @Test
    @DisplayName("draw() восстанавливает трансформацию Graphics2D")
    void drawRestoresTransform() {
        final BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();
        final AffineTransform before = g.getTransform();

        cat.setAngle(30);
        cat.draw(g);

        assertEquals(before, g.getTransform(),
                "draw() должен восстанавливать исходную трансформацию");
        g.dispose();
    }

    @Test
    @DisplayName("draw() при разных углах не падает")
    void drawWithVariousAngles() {
        final BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() -> {
            for (double angle = -180; angle <= 180; angle += 15) {
                cat.setAngle(angle);
                cat.draw(g);
            }
        });

        g.dispose();
    }

    private boolean hasOpaquePixel(final BufferedImage image) {
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