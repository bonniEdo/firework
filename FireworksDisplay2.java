import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class FireworksDisplay extends JPanel {
    private final Random random = new Random();
    private final List<Firework> fireworks = new ArrayList<>();
    private static final int MAX_RADIUS = 100; // 定義煙火的最大半徑

    public FireworksDisplay() {
        // Timer to update fireworks animation
        Timer timer = new Timer(30, e -> {
            Iterator<Firework> iterator = fireworks.iterator();
            while (iterator.hasNext()) {
                Firework firework = iterator.next();
                if (!firework.hasExploded) {
                    firework.ascend(); // 上升
                    if (firework.y <= firework.explosionHeight) {
                        firework.explode(); // 達到隨機爆炸高度後觸發爆炸
                    }
                } else {
                    firework.expand(); // 爆炸後擴散
                    if (firework.radius > MAX_RADIUS) {
                        iterator.remove(); // 半徑超過最大值，移除煙火
                    }
                }
            }
            repaint();
        });
        timer.start();

        // Timer to add new fireworks
        Timer newFireworkTimer = new Timer(1000, e -> {
            fireworks.add(new Firework(random.nextInt(getWidth()), getHeight()));
        });
        newFireworkTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Firework firework : fireworks) {
            firework.draw(g);
        }
    }

    private class Firework {
        private int x, y;
        private final int explosionHeight; // 隨機設定的爆炸高度
        private final Color color;
        private int radius = 0; // 初始爆炸半徑
        private boolean hasExploded = false; // 是否已經爆炸

        public Firework(int x, int startY) {
            this.x = x;
            this.y = startY;
            this.color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)); // 隨機顏色
            this.explosionHeight = random.nextInt(getHeight() / 2) + 50; // 隨機爆炸高度
        }

        public void ascend() {
            y -= 5; // 控制上升速度
        }

        public void explode() {
            hasExploded = true; // 設定已經爆炸
        }

        public void expand() {
            radius += 2; // 增加爆炸半徑
        }

        public void draw(Graphics g) {
            if (!hasExploded) {
                // 畫上升中的煙火軌跡，使用相同顏色
                g.setColor(color);
                g.fillOval(x - 2, y - 2, 4, 4); // 煙火上升時的小點
            } else {
                // 畫爆炸後的煙火效果
                g.setColor(color);
                for (int i = 0; i < 20; i++) {
                    int dx = (int) (Math.cos(i * Math.PI / 10) * radius);
                    int dy = (int) (Math.sin(i * Math.PI / 10) * radius);
                    g.drawLine(x, y, x + dx, y + dy);
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Fireworks Display");
        FireworksDisplay fireworksDisplay = new FireworksDisplay();

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(fireworksDisplay);
        frame.setVisible(true);
    }
}
