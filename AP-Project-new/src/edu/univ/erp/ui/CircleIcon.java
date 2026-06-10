package frontend;

import javax.swing.*;
import java.awt.*;

public class CircleIcon implements Icon {

    private final int size = 14;   // icon size
    private final Color color;

    public CircleIcon(Color col) {
        this.color = col;
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(color);
        g2.fillOval(x, y, size, size);

        g2.dispose();
    }
}
