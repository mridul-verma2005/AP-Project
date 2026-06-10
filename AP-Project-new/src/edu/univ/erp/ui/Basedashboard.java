package edu.univ.erp.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;

public abstract class Basedashboard extends JFrame {

    protected JTabbedPane tabs;

    public Basedashboard(String title, Color primary, Color accent) {

        setTitle(title);
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // HEADER BAR
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(primary);
        header.setPreferredSize(new Dimension(1100, 65));

        JLabel titleLabel = new JLabel("   " + title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));

        header.add(titleLabel, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // MAIN TABS
        tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tabs.setBorder(new EmptyBorder(10, 10, 10, 10));
        tabs.setBackground(Color.WHITE);

        add(tabs, BorderLayout.CENTER);

        // MENU BAR
        JMenuBar bar = new JMenuBar();
        JMenu viewMenu = new JMenu("View");

        JCheckBoxMenuItem darkToggle =
                new JCheckBoxMenuItem("Dark Mode", ThemeManager.DARK_MODE);

        darkToggle.addActionListener(e -> {
            ThemeManager.DARK_MODE = darkToggle.isSelected();
            applyTheme();
        });

        viewMenu.add(darkToggle);
        bar.add(viewMenu);

        setJMenuBar(bar);
    }

    public void applyTheme() {

        boolean dark = ThemeManager.DARK_MODE;

        Color bg = dark ? new Color(25, 25, 30) : Color.WHITE;
        Color tabBG = dark ? new Color(35, 45, 45) : Color.WHITE;
        Color txt = dark ? Color.WHITE : Color.BLACK;

        getContentPane().setBackground(bg);

        for (int i = 0; i < tabs.getTabCount(); i++) {
            tabs.setBackgroundAt(i, tabBG);
            tabs.setForegroundAt(i, txt);
        }

        repaint();
    }

    protected void styleTable(JTable table, Color headerColor) {

        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setGridColor(new Color(230, 230, 230));
        table.setShowVerticalLines(false);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setOpaque(true);
        header.setBackground(headerColor);
        header.setForeground(Color.WHITE);

        table.setSelectionBackground(new Color(0, 190, 120));
        table.setSelectionForeground(Color.WHITE);
    }

    protected JPanel createSearchableTablePanel(
            String title, Color accent, JTable table, JTextField[] searchFieldContainer) {

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setForeground(accent);

        panel.add(heading, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);

        JTextField search = new JTextField();
        search.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        search.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 190, 120), 2),
                new EmptyBorder(6, 10, 6, 10)
        ));

        searchFieldContainer[0] = search;
        searchPanel.add(search, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220,255,235), 2));

        panel.add(searchPanel, BorderLayout.SOUTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    protected JPanel createSectionPanel(String title, String body, Color pillColor) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(pillColor);

        JTextArea text = new JTextArea(body);
        text.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        text.setEditable(false);
        text.setOpaque(false);
        text.setForeground(new Color(60, 60, 60));

        panel.add(heading, BorderLayout.NORTH);
        panel.add(text, BorderLayout.CENTER);

        return panel;
    }
}




