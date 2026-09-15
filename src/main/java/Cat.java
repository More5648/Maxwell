import java.awt.*;

public class Cat {

    private int x;
    private int y;
    private int width;
    private int height;
    private double angle = 0.0;


    public Cat(final int x, final int y, final int width, final int height, final Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    double getAngle() {
        return this.angle;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    private double getScale() {
        return Math.min(this.width / 645.0, this.height / 470.0);
    }

    private int s(double originalSize) {
        return (int) (originalSize * getScale());
    }

    private int px(double originalX) {
        double scale = getScale();
        double offsetX = (this.width - 645.0 * scale) / 2.0;
        return (int) (this.x + offsetX + (originalX - 65.0) * scale);
    }

    private int py(double originalY) {
        double scale = getScale();
        double offsetY = (this.height - 470.0 * scale) / 2.0;
        return (int) (this.y + offsetY + (originalY - 410.0) * scale);
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));

        java.awt.geom.AffineTransform old = g2.getTransform();
        g2.rotate(Math.toRadians(angle), x + width / 2.0, y + height / 2.0);

        Color darkBodyColor = new Color(40, 40, 40);
        Color mainBodyColor = new Color(25, 25, 25);
        Color noseColor = new Color(50, 50, 50);
        Color eyeColor = new Color(110, 180, 100);
        Color whispes = new Color(198, 193, 193);

        // 1. Боковая (задняя) часть спины
        int[] side1X = {px(120), px(175), px(120), px(65)};
        int[] side1Y = {py(525), py(685), py(805), py(665)};
        g.setColor(darkBodyColor);
        g.fillPolygon(side1X, side1Y, 4);
        g.setColor(Color.BLACK);
        g.drawPolygon(side1X, side1Y, 4);

        int[] side2X = {px(120), px(265), px(405), px(420), px(425), px(175)};
        int[] side2Y = {py(525), py(470), py(510), py(550), py(630), py(685)};
        g.setColor(darkBodyColor);
        g.fillPolygon(side2X, side2Y, 6);
        g.setColor(Color.BLACK);
        g.drawPolygon(side2X, side2Y, 6);

        // 2. Основное туловище
        int[] bodyX = {px(120), px(175), px(425), px(470), px(545), px(520), px(440)};
        int[] bodyY = {py(805), py(685), py(630), py(655), py(855), py(880), py(885)};
        g.setColor(mainBodyColor);
        g.fillPolygon(bodyX, bodyY, 7);
        g.setColor(Color.BLACK);
        g.drawPolygon(bodyX, bodyY, 7);

        // Треугольник лапки/тени
        int[] triangleX = {px(610), px(615), px(585), px(545)};
        int[] triangleY = {py(655), py(801), py(840), py(855)};
        g.setColor(mainBodyColor);
        g.fillPolygon(triangleX, triangleY, 4);
        g.setColor(Color.BLACK);
        g.drawPolygon(triangleX, triangleY, 4);

        // 3. Белая грудка
        int[] chestX = {px(470), px(610), px(545)};
        int[] chestY = {py(655), py(655), py(850)};
        g.setColor(Color.WHITE);
        g.fillPolygon(chestX, chestY, 3);
        g.setColor(Color.BLACK);
        g.drawPolygon(chestX, chestY, 3);

        // 4. Голова (основа)
        int[] head1X = {px(420), px(460), px(590), px(590), px(540), px(470), px(425)};
        int[] head1Y = {py(550), py(500), py(620), py(635), py(665), py(655), py(630)};
        g.setColor(darkBodyColor);
        g.fillPolygon(head1X, head1Y, 7);
        g.setColor(Color.BLACK);
        g.drawPolygon(head1X, head1Y, 7);

        int[] head2X = {px(590), px(630), px(655), px(615), px(590)};
        int[] head2Y = {py(485), py(505), py(600), py(650),py(635)};
        g.setColor(darkBodyColor);
        g.fillPolygon(head2X, head2Y, 5);
        g.setColor(Color.BLACK);
        g.drawPolygon(head2X, head2Y, 5);

        int[] head3X = {px(460), px(475), px(560), px(590), px(605), px(570)};
        int[] head3Y = {py(500), py(480), py(470), py(485), py(599), py(601)};
        g.setColor(mainBodyColor);
        g.fillPolygon(head3X, head3Y, 6);
        g.setColor(Color.BLACK);
        g.drawPolygon(head3X, head3Y, 6);

        // 5. Левое ухо
        int[] lEar1X = {px(385), px(420), px(460)};
        int[] lEar1Y = {py(445), py(555), py(505)};
        g.setColor(darkBodyColor);
        g.fillPolygon(lEar1X, lEar1Y, 3);
        g.setColor(Color.BLACK);
        g.drawPolygon(lEar1X, lEar1Y, 3);

        int[] lEar2X = {px(385), px(460), px(475)};
        int[] lEar2Y = {py(445), py(505), py(480)};
        g.setColor(mainBodyColor);
        g.fillPolygon(lEar2X, lEar2Y, 3);
        g.setColor(Color.BLACK);
        g.drawPolygon(lEar2X, lEar2Y, 3);

        // 6. Правое ухо
        int[] rEar1X = {px(630), px(555), px(630)};
        int[] rEar1Y = {py(410), py(470), py(505)};
        g.setColor(mainBodyColor);
        g.fillPolygon(rEar1X, rEar1Y, 3);
        g.setColor(Color.BLACK);
        g.drawPolygon(rEar1X, rEar1Y, 3);

        int[] rEar2X = {px(630), px(590), px(630)};
        int[] rEar2Y = {py(410), py(485), py(505)};
        g.setColor(darkBodyColor);
        g.fillPolygon(rEar2X, rEar2Y, 3);
        g.setColor(Color.BLACK);
        g.drawPolygon(rEar2X, rEar2Y, 3);


        // 7. Подбородок
        int[] chinX = {px(540), px(590), px(615), px(590)};
        int[] chinY = {py(665), py(635), py(650), py(665)};
        g.setColor(Color.WHITE);
        g.fillPolygon(chinX, chinY, 4);
        g.setColor(Color.BLACK);
        g.drawPolygon(chinX, chinY, 4);

        // 8. Нос
        int[] noseX = {px(590), px(570), px(605)};
        int[] noseY = {py(620), py(601), py(599)};
        g.setColor(noseColor);
        g.fillPolygon(noseX, noseY, 3);
        g.setColor(Color.BLACK);
        g.drawPolygon(noseX, noseY, 3);

        // Левый глаз
        int eye1X = px(510);
        int eye1Y = py(545);
        int eye1Size = s(35);
        g.setColor(eyeColor);
        g.fillRect(eye1X, eye1Y, eye1Size, eye1Size);
        g.setColor(Color.BLACK);
        g.drawRect(eye1X, eye1Y, eye1Size, eye1Size);

        // Правый глаз
        int eye2X = px(595);
        int eye2Y = py(540);
        int eye2Size = s(30);
        g.setColor(eyeColor);
        g.fillRect(eye2X, eye2Y, eye2Size, eye2Size);
        g.setColor(Color.BLACK);
        g.drawRect(eye2X, eye2Y, eye2Size, eye2Size);

        // 11. Усы
        g.setColor(whispes);
        // Левые
        g.drawLine(px(520), py(620), px(430), py(640));
        g.drawLine(px(530), py(635), px(430), py(670));
        g.drawLine(px(540), py(650), px(450), py(710));
        // Правые
        g.drawLine(px(610), py(610), px(700), py(610));
        g.drawLine(px(620), py(625), px(710), py(645));
        g.drawLine(px(620), py(640), px(690), py(680));

        g2.setTransform(old);
    }

}