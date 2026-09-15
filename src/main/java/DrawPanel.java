import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawPanel extends JPanel implements ActionListener {

    private final int PANEL_WIDTH;
    private final int PANEL_HEIGHT;
    private final int TIMER_DELAY;
    private Timer timer;
    private int ticksFromStart = 0;


    private Cat maxwell;

    Cat getCat() {
        return this.maxwell;
    }

    public DrawPanel(final int width, final int height, final int timerDelay) {
        this.PANEL_WIDTH = width;
        this.PANEL_HEIGHT = height;
        this.TIMER_DELAY = timerDelay;
        timer = new Timer(timerDelay, this);
        timer.start();

        int catW = (int)(PANEL_WIDTH * 0.7);
        int catH = (int)(PANEL_HEIGHT * 0.7);

        int catX = (PANEL_WIDTH - catW) / 2;   // 120
        int catY = (PANEL_HEIGHT - catH) / 2;  // 90

        this.maxwell = new Cat(catX, catY, catW, catH, Color.BLACK);
        new Music("maxwell.wav");
    }

    @Override
    public void paint(final Graphics gr) {
        super.paint(gr);
        maxwell.draw(gr);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == timer) {
            double angle = Math.sin(ticksFromStart * 0.7) * 10;
            maxwell.setAngle(angle);
            repaint();
            ++ticksFromStart;
        }
    }
}