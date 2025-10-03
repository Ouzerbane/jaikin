import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class ChaikinSwingApp extends JPanel implements KeyListener, MouseListener, ActionListener {

    private final List<Vec2> positions = new ArrayList<>();
    private List<Vec2> points = new ArrayList<>();

    private boolean animationTime = false;
    private int counter = 1;

    private final Timer timer;

    public ChaikinSwingApp() {
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

        // 60 FPS timer
        timer = new Timer(1000 / 60, this);
        timer.start();
    }

    // Animation and drawing
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Cast to Graphics2D for better quality
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.WHITE);
        g2d.drawString("Hello, Swing!", 20, 20);

        if (animationTime) {
            drawLines(g2d, points);
            g2d.setColor(Color.GREEN);
            g2d.drawString("Animation Step: " + ((counter / 50) + 1) + "/7", 20, 40);

            if (counter % 50 == 0) {
                points = chaikinSmooth(points);
            }

            counter++;

            if (counter == 350) {
                points = new ArrayList<>(positions);
                counter = 1;
            }
        }

        drawCircles(g2d, positions);
    }

    private void drawLines(Graphics2D g, List<Vec2> pts) {
        if (pts.size() < 2) return;
        g.setColor(Color.WHITE);
        for (int i = 0; i < pts.size() - 1; i++) {
            Vec2 p1 = pts.get(i);
            Vec2 p2 = pts.get(i + 1);
            g.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
        }
    }

    private void drawCircles(Graphics2D g, List<Vec2> pts) {
        g.setColor(Color.WHITE);
        for (Vec2 p : pts) {
            g.drawOval((int) (p.x - 2.5), (int) (p.y - 2.5), 5, 5);
        }
    }

    private List<Vec2> chaikinSmooth(List<Vec2> input) {
        List<Vec2> output = new ArrayList<>();
        if (input.size() < 2) return input;

        output.add(input.get(0)); // Optionally keep endpoints

        for (int i = 0; i < input.size() - 1; i++) {
            Vec2 p0 = input.get(i);
            Vec2 p1 = input.get(i + 1);

            Vec2 q = p0.mul(0.75).add(p1.mul(0.25));
            Vec2 r = p0.mul(0.25).add(p1.mul(0.75));

            output.add(q);
            output.add(r);
        }

        output.add(input.get(input.size() - 1)); // Optionally keep endpoints
        return output;
    }

    // Main loop triggered by the timer
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    // Mouse input
    @Override
    public void mousePressed(MouseEvent e) {
        if (!animationTime && SwingUtilities.isLeftMouseButton(e)) {
            positions.add(new Vec2(e.getX(), e.getY()));
        }
    }

    // Keyboard input
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER -> {
                if (positions.size() > 1) {
                    animationTime = true;
                    counter = 1;
                    points = new ArrayList<>(positions);
                }
            }
            case KeyEvent.VK_SPACE -> {
                animationTime = false;
                counter = 1;
                positions.clear();
                points.clear();
            }
            case KeyEvent.VK_ESCAPE -> System.exit(0);
        }
    }

    // Required unused listener methods
    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    // Vec2 class (like Macroquad's Vec2)
    static class Vec2 {
        double x, y;

        Vec2(double x, double y) {
            this.x = x;
            this.y = y;
        }

        Vec2 add(Vec2 other) {
            return new Vec2(this.x + other.x, this.y + other.y);
        }

        Vec2 mul(double scalar) {
            return new Vec2(this.x * scalar, this.y * scalar);
        }
    }

    // Entry point
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chaikin Smoothing with Swing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setResizable(false);

            ChaikinSwingApp panel = new ChaikinSwingApp();
            frame.add(panel);
            frame.setVisible(true);
            panel.requestFocusInWindow(); // Ensure key events work
        });
    }
}
