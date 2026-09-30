import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class BackGround {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private Color color;
    private float hueOffset = 0f;

    public BackGround(final int x, final int y, final int width, final int height, final Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHueOffset(final float hueOffset) {
        this.hueOffset = hueOffset;
    }

    public float getHueOffset() {
        return this.hueOffset;
    }

    public void draw(final Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;

        final AffineTransform old = g2.getTransform();

        int cx = getWidth()  / 2;
        int cy = getHeight() / 2;
        int radius = 1100;
        for (int i = 0; i < 10; i++) {
            float hue = (i / 10f + hueOffset) % 1f;
            if (hue < 0) hue += 1f;

            g2.setColor(Color.getHSBColor(hue, 1f, 1f));
            g2.fillOval(cx - radius, cy - radius, radius * 2, radius * 2);

            radius -= 110;
        }

        g2.setTransform(old);
    }





}