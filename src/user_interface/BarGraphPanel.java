package user_interface;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

public class BarGraphPanel extends JPanel {

    private final Map<Integer, Integer> orderCounts;

    public BarGraphPanel() {
        MenuItemOrderTracker tracker = new MenuItemOrderTracker();
        this.orderCounts = new TreeMap<>(tracker.getAllOrderCounts()); // sorted by item ID
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (orderCounts.isEmpty()) {
            g.drawString("No data available", 50, 50);
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        int padding = 50;
        int barWidth = 40;
        int gap = 30;

        int maxCount = orderCounts.values().stream().max(Integer::compareTo).orElse(1);
        int x = padding;

        g2d.drawString("Most Ordered Items (by ID)", padding, padding - 20);

        for (Map.Entry<Integer, Integer> entry : orderCounts.entrySet()) {
            int itemId = entry.getKey();
            int count = entry.getValue();

            int barHeight = (int) (((double) count / maxCount) * (height - 2 * padding));
            g2d.setColor(Color.BLUE);
            g2d.fillRect(x, height - barHeight - padding, barWidth, barHeight);

            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, height - barHeight - padding, barWidth, barHeight);
            g2d.drawString(String.valueOf(itemId), x + 10, height - padding + 15);
            g2d.drawString(String.valueOf(count), x + 10, height - barHeight - padding - 5);

            x += barWidth + gap;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 400);
    }
}
