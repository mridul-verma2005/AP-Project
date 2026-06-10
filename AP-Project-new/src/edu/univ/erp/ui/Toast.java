package edu.univ.erp.ui;

import javax.swing.*;
import java.awt.*;

public class Toast extends JWindow {

    public Toast(JFrame parent, String message) {
        super(parent);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(50, 50, 50, 220));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

        JLabel label = new JLabel(message);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));

        panel.add(label);
        add(panel);

        // set location
        int x = parent.getLocationOnScreen().x + parent.getWidth() - 300;
        int y = parent.getLocationOnScreen().y + parent.getHeight() - 120;
        setLocation(x, y);

        pack();

        new Thread(() -> {
            try {
                setVisible(true);
                Thread.sleep(1800);
                setVisible(false);
                dispose();
            } catch (Exception ignored) {}
        }).start();
    }
}
