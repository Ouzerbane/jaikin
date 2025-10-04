import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chaikin Smoothing with Swing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setResizable(false);

            ChaikinPanel panel = new ChaikinPanel();
            frame.add(panel);
            frame.setVisible(true);
            panel.requestFocusInWindow();
        });
    }
}
