import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Cat {

    private static final double BASE_WIDTH = 645.0;
    private static final double BASE_HEIGHT = 470.0;
    private static final double BASE_OFFSET_X = 65.0;
    private static final double BASE_OFFSET_Y = 410.0;

    private static final Color DARK_BODY_COLOR = new Color(40, 40, 40);
    private static final Color MAIN_BODY_COLOR = new Color(25, 25, 25);
    private static final Color NOSE_COLOR = new Color(50, 50, 50);
    private static final Color EYE_COLOR = new Color(110, 180, 100);
    private static final Color WHISKERS_COLOR = new Color(198, 193, 193);

    private static final int STROKE_WIDTH = 4;

    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private double angle;

    public Cat(final int x, final int y, final int width, final int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = 0.0;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public double getAngle() {
        return this.angle;
    }

    public void setAngle(final double angle) {
        this.angle = angle;
    }

    public void draw(final Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(STROKE_WIDTH));

        final AffineTransform old = g2.getTransform();
        g2.rotate(Math.toRadians(this.angle),
                this.x + this.width / 2.0,
                this.y + this.height / 2.0);

        drawBack(g);
        drawBody(g);
        drawPawShadow(g);
        drawChest(g);
        drawHead(g);
        drawLeftEar(g);
        drawRightEar(g);
        drawChin(g);
        drawNose(g);
        drawEyes(g);
        drawWhiskers(g);

        g2.setTransform(old);
    }

    private double getScale() {
        return Math.min(this.width / BASE_WIDTH, this.height / BASE_HEIGHT);
    }

    private int s(final double originalSize) {
        return (int) (originalSize * getScale());
    }

    private int px(final double originalX) {
        final double scale = getScale();
        final double offsetX = (this.width - BASE_WIDTH * scale) / 2.0;
        return (int) (this.x + offsetX + (originalX - BASE_OFFSET_X) * scale);
    }

    private int py(final double originalY) {
        final double scale = getScale();
        final double offsetY = (this.height - BASE_HEIGHT * scale) / 2.0;
        return (int) (this.y + offsetY + (originalY - BASE_OFFSET_Y) * scale);
    }

    private void fillAndDrawPolygon(final Graphics g, final Color fillColor,
                                    final int[] xs, final int[] ys) {
        g.setColor(fillColor);
        g.fillPolygon(xs, ys, xs.length);
        g.setColor(Color.BLACK);
        g.drawPolygon(xs, ys, xs.length);
    }

    private void drawBack(final Graphics g) {
        fillAndDrawPolygon(g, DARK_BODY_COLOR,
                new int[]{px(120), px(175), px(120), px(65)},
                new int[]{py(525), py(685), py(805), py(665)});

        fillAndDrawPolygon(g, DARK_BODY_COLOR,
                new int[]{px(120), px(265), px(405), px(420), px(425), px(175)},
                new int[]{py(525), py(470), py(510), py(550), py(630), py(685)});
    }

    private void drawBody(final Graphics g) {
        fillAndDrawPolygon(g, MAIN_BODY_COLOR,
                new int[]{px(120), px(175), px(425), px(470), px(545), px(520), px(440)},
                new int[]{py(805), py(685), py(630), py(655), py(855), py(880), py(885)});
    }

    private void drawPawShadow(final Graphics g) {
        fillAndDrawPolygon(g, MAIN_BODY_COLOR,
                new int[]{px(610), px(615), px(585), px(545)},
                new int[]{py(655), py(801), py(840), py(855)});
    }

    private void drawChest(final Graphics g) {
        fillAndDrawPolygon(g, Color.WHITE,
                new int[]{px(470), px(610), px(545)},
                new int[]{py(655), py(655), py(850)});
    }

    private void drawHead(final Graphics g) {
        fillAndDrawPolygon(g, DARK_BODY_COLOR,
                new int[]{px(420), px(460), px(590), px(590), px(540), px(470), px(425)},
                new int[]{py(550), py(500), py(620), py(635), py(665), py(655), py(630)});

        fillAndDrawPolygon(g, DARK_BODY_COLOR,
                new int[]{px(590), px(630), px(655), px(615), px(590)},
                new int[]{py(485), py(505), py(600), py(650), py(635)});

        fillAndDrawPolygon(g, MAIN_BODY_COLOR,
                new int[]{px(460), px(475), px(560), px(590), px(605), px(570)},
                new int[]{py(500), py(480), py(470), py(485), py(599), py(601)});
    }

    private void drawLeftEar(final Graphics g) {
        fillAndDrawPolygon(g, DARK_BODY_COLOR,
                new int[]{px(385), px(420), px(460)},
                new int[]{py(445), py(555), py(505)});

        fillAndDrawPolygon(g, MAIN_BODY_COLOR,
                new int[]{px(385), px(460), px(475)},
                new int[]{py(445), py(505), py(480)});
    }

    private void drawRightEar(final Graphics g) {
        fillAndDrawPolygon(g, MAIN_BODY_COLOR,
                new int[]{px(630), px(555), px(630)},
                new int[]{py(410), py(470), py(505)});

        fillAndDrawPolygon(g, DARK_BODY_COLOR,
                new int[]{px(630), px(590), px(630)},
                new int[]{py(410), py(485), py(505)});
    }

    private void drawChin(final Graphics g) {
        fillAndDrawPolygon(g, Color.WHITE,
                new int[]{px(540), px(590), px(615), px(590)},
                new int[]{py(665), py(635), py(650), py(665)});
    }

    private void drawNose(final Graphics g) {
        fillAndDrawPolygon(g, NOSE_COLOR,
                new int[]{px(590), px(570), px(605)},
                new int[]{py(620), py(601), py(599)});
    }

    private void drawEyes(final Graphics g) {
        drawEye(g, px(510), py(545), s(35));
        drawEye(g, px(595), py(540), s(30));
    }

    private void drawEye(final Graphics g, final int eyeX, final int eyeY,
                         final int eyeSize) {
        g.setColor(EYE_COLOR);
        g.fillRect(eyeX, eyeY, eyeSize, eyeSize);
        g.setColor(Color.BLACK);
        g.drawRect(eyeX, eyeY, eyeSize, eyeSize);
    }

    private void drawWhiskers(final Graphics g) {
        g.setColor(WHISKERS_COLOR);
        // Левые усы
        g.drawLine(px(520), py(620), px(430), py(640));
        g.drawLine(px(530), py(635), px(430), py(670));
        g.drawLine(px(540), py(650), px(450), py(710));
        // Правые усы
        g.drawLine(px(610), py(610), px(700), py(610));
        g.drawLine(px(620), py(625), px(710), py(645));
        g.drawLine(px(620), py(640), px(690), py(680));
    }
}