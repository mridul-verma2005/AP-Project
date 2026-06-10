

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ProfilePicture extends JPanel {

    private final Image img;

    public ProfilePicture(int size, Color color) {
        setPreferredSize(new Dimension(size, size));

        // default colored circle image
        img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = ((BufferedImage) img).createGraphics();
        g.setColor(color);
        g.fillOval(0, 0, size, size);
        g.dispose();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
    }
}
