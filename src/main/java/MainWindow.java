import javax.swing.JFrame;
import java.awt.HeadlessException;

public class MainWindow extends JFrame {

    private static final String TITLE = "Maxwell the Cat";
    private static final int WINDOW_WIDTH = 1920;
    private static final int WINDOW_HEIGHT = 1040;
    private static final int TIMER_DELAY_MS = 100;

    private final DrawPanel panel;

    public MainWindow() throws HeadlessException {
        setTitle(TITLE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);

        this.panel = new DrawPanel(WINDOW_WIDTH, WINDOW_HEIGHT, TIMER_DELAY_MS);
        add(this.panel);
    }
}