import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            final MainWindow window = new MainWindow();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setExtendedState(JFrame.MAXIMIZED_BOTH);
            window.setVisible(true);
        });
    }
}