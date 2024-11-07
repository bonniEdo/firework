import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class FireworksDisplay extends JPanel {
    private final Random random = new Random();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Randomly generate colors and positions for fireworks
        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(getWidth());
            int y = random.nextInt(getHeight() / 2);
            Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            drawFirework(g, x, y, color);
        }
    }

    // Draw a single firework
    private void drawFirework(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        for (int i = 0; i < 20; i++) {
            int dx = (int) (Math.cos(i * Math.PI / 10) * 30);
            int dy = (int) (Math.sin(i * Math.PI / 10) * 30);
            g.drawLine(x, y, x + dx, y + dy);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Fireworks Display");
        FireworksDisplay fireworks = new FireworksDisplay();

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(fireworks);
        frame.setVisible(true);

        // Refresh display periodically to simulate fireworks
        Timer timer = new Timer(500, e -> fireworks.repaint());
        timer.start();
    }
}
