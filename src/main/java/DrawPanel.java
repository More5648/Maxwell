import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawPanel extends JPanel implements ActionListener {

    private static final double CAT_SIZE_RATIO = 0.7;
    private static final double ROTATION_AMPLITUDE = 10.0;
    private static final double ROTATION_SPEED = 0.7;
    private static final String MUSIC_FILE = "maxwell.wav";

    private final int panelWidth;
    private final int panelHeight;
    private final Timer timer;
    private final Cat maxwell;
    private int ticksFromStart;

    public DrawPanel(final int width, final int height, final int timerDelay) {
        this.panelWidth = width;
        this.panelHeight = height;
        this.ticksFromStart = 0;

        this.timer = new Timer(timerDelay, this);
        this.timer.start();

        final int catWidth = (int) (this.panelWidth * CAT_SIZE_RATIO);
        final int catHeight = (int) (this.panelHeight * CAT_SIZE_RATIO);
        final int catX = (this.panelWidth - catWidth) / 2;
        final int catY = (this.panelHeight - catHeight) / 2;

        this.maxwell = new Cat(catX, catY, catWidth, catHeight);

        new Music(MUSIC_FILE);
    }

    public Cat getCat() {
        return this.maxwell;
    }

    public Timer getTimer() {
        return this.timer;
    }

    @Override
    public void paint(final Graphics gr) {
        super.paint(gr);
        this.maxwell.draw(gr);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == this.timer) {
            final double angle = Math.sin(this.ticksFromStart * ROTATION_SPEED)
                    * ROTATION_AMPLITUDE;
            this.maxwell.setAngle(angle);
            repaint();
            this.ticksFromStart++;
        }
    }
}