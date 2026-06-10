

package edu.univ.erp.ui;
import javax.swing.*;
import java.awt.*;
import edu.univ.erp.auth.Current_session;
import edu.univ.erp.service.Auth_Service;

public class loginPage extends JFrame {
    private int failedAttempts = 0;

    public loginPage() {
        setTitle("IIIT Delhi ERP - Login");
        setSize(900, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        ImageIcon backIcon = new ImageIcon(getClass().getResource("campus.png"));
        Image scbg = backIcon.getImage().getScaledInstance(900, 600, Image.SCALE_SMOOTH);
        JLabel bgLabel = new JLabel(new ImageIcon(scbg));
        bgLabel.setLayout(new GridBagLayout());
        setContentPane(bgLabel);

        JPanel overlay = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                GradientPaint gp = new GradientPaint(
                        0, 0,
                        new Color(0, 128, 90, 140),
                        0, getHeight(),
                        new Color(0, 190, 120, 180)
                );

                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        overlay.setOpaque(false);
        overlay.setLayout(new GridBagLayout());
        bgLabel.add(overlay);

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(420, 430));
        card.setBackground(new Color(255, 255, 255, 240));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0,190,120), 3, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setLayout(null);
        ImageIcon logoiconn = new ImageIcon(getClass().getResource("logo.png"));
        Image logoScaled = logoiconn.getImage().getScaledInstance(65, 65, Image.SCALE_SMOOTH);

        JLabel logolabell = new JLabel(new ImageIcon(logoScaled));
        logolabell.setBounds(185, 10, 60, 60);
        card.add(logolabell);

        JLabel universitylabel = new JLabel("IIIT Delhi ERP Portal", SwingConstants.CENTER);
        universitylabel.setFont(new Font("Segoe UI", Font.BOLD, 21));
        universitylabel.setForeground(new Color(0,128,90));
        universitylabel.setBounds(30, 72, 360, 30);
        card.add(universitylabel);

        JLabel userkabell = new JLabel("Username");
        userkabell.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        userkabell.setBounds(60, 125, 120, 25);
        card.add(userkabell);

        JTextField username = new JTextField();
        username.setBounds(60, 150, 300, 40);
        username.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        username.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0,190,120), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        card.add(username);

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        passLabel.setBounds(60, 205, 120, 25);
        card.add(passLabel);

        JPasswordField password = new JPasswordField();
        password.setBounds(60, 230, 300, 40);
        password.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        password.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0,190,120), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        card.add(password);

        JButton loginbutton = new JButton("Login");
        loginbutton.setBounds(60, 290, 300, 45);
        loginbutton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginbutton.setBackground(new Color(0,128,90));
        loginbutton.setForeground(Color.WHITE);
        loginbutton.setFocusPainted(false);
        loginbutton.setBorderPainted(false);

        loginbutton.addChangeListener(e -> {
            if (loginbutton.getModel().isRollover()) {
                loginbutton.setBackground(new Color(0,190,120));
            } else {
                loginbutton.setBackground(new Color(0,128,90));
            }
        });

        card.add(loginbutton);

        loginbutton.addActionListener(e -> {

            if (failedAttempts >= 5) {
                JOptionPane.showMessageDialog(
                        this,
                        "Too many failed attempts! Login is locked.",
                        "Locked",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            String user = username.getText().trim();
            String pass = new String(password.getPassword());
            Current_session currentSession = new Current_session(pass,user);
            Auth_Service authService = new Auth_Service();
            int verify = authService.login(pass,user);

            if (verify == 0) {
                failedAttempts++;

                if (failedAttempts >= 5) {
                    loginbutton.setEnabled(false);
                    JOptionPane.showMessageDialog(
                            this,
                            "You have exceeded the maximum login attempts.\nPlease restart the application.",
                            "Locked",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid username or password!\nAttempts left: " + (5 - failedAttempts),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                return;
            }

            failedAttempts = 0;

            if (verify == 3) {
                new Toast(this, "Welcome Admin!");
                new admindashboard(user).setVisible(true);
                dispose();
            }
            else if (verify == 2) {
                new Toast(this, "Welcome Instructor!");
                new InstructorDashboard(user).setVisible(true);
                dispose();
            }
            else if (verify == 1) {
                new Toast(this, "Welcome Student!");
                new Studentdashboard(user).setVisible(true);
                dispose();
            }

            if (verify != 0) {
                authService.update_lastlogin(user);
            }
        });


        overlay.add(card);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new loginPage().setVisible(true));
    }
}