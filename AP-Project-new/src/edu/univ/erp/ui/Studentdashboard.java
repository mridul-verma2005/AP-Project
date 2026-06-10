package edu.univ.erp.ui;

import edu.univ.erp.data.Enrollment_Access;
import edu.univ.erp.data.Settings_Access;
import edu.univ.erp.data.Student_Access;
import edu.univ.erp.service.Student_Service;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.Objects;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.PrintWriter;
public class Studentdashboard extends Basedashboard {

    private static final Color DARK_GREEN  = new Color(0, 128, 90);
    private static final Color LIGHT_GREEN = new Color(0, 190, 120);
    private final String username;
    private boolean maintenancemode = false;
    private static final int COL_COURSE_CODE = 0;
    private static final int COL_SECTION_ID  = 1;
    private static final int COL_TIMINGS     = 2;
    private static final int COL_TITLE       = 3;
    private static final int COL_CREDITS     = 4;
    private static final int COL_INSTRUCTOR  = 5;
    private static final int COL_ACTION      = 6;

    public Studentdashboard(String username) {
        super("Student Dashboard", DARK_GREEN, LIGHT_GREEN);
        this.username = username;
        Settings_Access s = new Settings_Access();
        maintenancemode = (s.getsetting("maintenance") == 1);
        if (maintenancemode) {
            JOptionPane.showMessageDialog(this,
                    "System is under maintenance.\nAll actions are disabled.",
                    "Maintenance Mode",
                    JOptionPane.WARNING_MESSAGE);
        }
        tabs.addTab(" Catalog", buildCourseCatalogPanel());
        tabs.addTab(" My Registrations", buildMyRegistrationsPanel());
        tabs.addTab("️ Timetable", buildTimetablePanel());
        tabs.addTab(" Grades", buildGradesPanel());
        tabs.addTab(" Profile", buildProfilePanel());
        applyTabIcons();
        applyTheme();
    }

    private JPanel buildCourseCatalogPanel() {
        String[] columns = {"Course Code","Section ID", "Timings" ,"Title", "Credits", "Instructor", "Action"};

        Student_Service student_service = new Student_Service();
        Object[][] rows = student_service.browseCatalog();

        DefaultTableModel model = new DefaultTableModel(rows, columns) {
            @Override public boolean isCellEditable(int r, int c) { return c == COL_ACTION; }
        };
        JTable table = new JTable(model);
        styleTable(table, DARK_GREEN);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i <= COL_TIMINGS; i++) table.getColumnModel().getColumn(i).setCellRenderer(center);
        table.getColumnModel().getColumn(COL_ACTION).setCellRenderer(new ActionButtonRenderer(LIGHT_GREEN));
        table.getColumnModel().getColumn(COL_ACTION).setCellEditor(new Registeract_editor(model, table));
        JTextField[] searchbox = new JTextField[1];
        return createSearchableTablePanel(" Course Catalog", LIGHT_GREEN, table, searchbox);
    }
    private static class ActionButtonRenderer extends JButton implements TableCellRenderer {
        private final Color baseColor;
        private final Color registeredColor = new Color(0, 150, 90);
        ActionButtonRenderer(Color base) {
            this.baseColor = base;
            setOpaque(true);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 13));
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            String text = Objects.toString(value, "");
            if (text.equalsIgnoreCase("Registered ✓")) setBackground(registeredColor);
            else setBackground(baseColor);
            setText(text);
            return this;
        }
    }
    private class Registeract_editor extends DefaultCellEditor {
        private final JButton button = new JButton();
        private final DefaultTableModel model;
        private final JTable table;
        private boolean clicked = false;
        private int editingRow = -1;
        Registeract_editor(DefaultTableModel model, JTable table) {
            super(new JCheckBox());
            this.model = model;
            this.table = table;
            button.setOpaque(true);
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Segoe UI", Font.BOLD, 13));
            button.addActionListener(e -> fireEditingStopped());
        }
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            clicked = true;
            editingRow = row;
            String text = Objects.toString(value, "Register");
            button.setText(text);

            if (maintenancemode) {
                button.setBackground(new Color(150,150,150));
            }
            return button;
        }
        @Override
        public Object getCellEditorValue() {
            if (maintenancemode) {
                new Toast(Studentdashboard.this, "Maintenance mode: registration disabled");
                return "";
            }
            if (!clicked) return "";
            try {
                String sectionid = Objects.toString(model.getValueAt(editingRow, COL_SECTION_ID), "");
                Object currentaction = model.getValueAt(editingRow, COL_ACTION);
                String actiontext = Objects.toString(currentaction, "");
                Enrollment_Access enroll = new Enrollment_Access();
                if (actiontext.equalsIgnoreCase("Registered ✓") ||
                        enroll.CheckCurrentEnrollment(username, sectionid) == 1) {
                    new Toast(Studentdashboard.this, "Already registered!");
                }
                else {
                    Student_Service svc = new Student_Service();
                    int result = svc.registerCourses(username, sectionid);
                    if (result == 1) {
                        model.setValueAt("Registered ✓", editingRow, COL_ACTION);
                        new Toast(Studentdashboard.this, "Successfully registered!");
                    } else if(result == 2) {
                        new Toast(Studentdashboard.this, "section is full");
                    }
                    else{
                        new Toast(Studentdashboard.this, "Registration failed");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                new Toast(Studentdashboard.this, "Error while registering");
            }
            clicked = false;
            return "Registered ✓";
        }
    }
    private JPanel buildMyRegistrationsPanel() {
        String[] columns = {"Course Code","Section ID", "Timings" , "Action"};
        Enrollment_Access enrollmentAccess = new Enrollment_Access();
        Object[][] data = enrollmentAccess.seeEnrollmentbyStudent_username(username);
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override public boolean isCellEditable(int r, int c) { return c == 3; }
        };
        JTable table = new JTable(model);
        styleTable(table, DARK_GREEN);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < 4; i++) table.getColumnModel().getColumn(i).setCellRenderer(center);
        table.getColumnModel().getColumn(3).setCellRenderer(new DropButtonRenderer());
        table.getColumnModel().getColumn(3).setCellEditor(new DropButtonEditor(model, table));
        JTextField[] searchBox = new JTextField[1];
        return createSearchableTablePanel(" My Registrations", LIGHT_GREEN, table, searchBox);
    }
    private static class DropButtonRenderer extends JButton implements TableCellRenderer {
        DropButtonRenderer() {
            setOpaque(true);
            setBackground(new Color(200, 0, 0));
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 13));
            setText("Drop");
        }
        public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
            return this;
        }
    }
    private class DropButtonEditor extends DefaultCellEditor {
        private final JButton btn = new JButton("Drop");
        private final JTable table;
        private final DefaultTableModel model;
        private boolean clicked = false;
        DropButtonEditor(DefaultTableModel m, JTable t) {
            super(new JCheckBox());
            this.model = m;
            this.table = t;
            btn.setOpaque(true);
            btn.setBackground(new Color(200,0,0));
            btn.setForeground(Color.WHITE);
            btn.addActionListener(e -> fireEditingStopped());
        }
        @Override
        public Component getTableCellEditorComponent(JTable t, Object v, boolean s, int r, int c) {
            if (maintenancemode) {
                btn.setBackground(new Color(150,150,150));
            }
            clicked = true;
            return btn;
        }
        @Override
        public Object getCellEditorValue() {
            if (maintenancemode) {
                new Toast(Studentdashboard.this,
                        "Maintenance mode: dropping disabled");
                return "Drop";
            }
            if (clicked) {
                int row = table.getSelectedRow();
                if (row < 0) return "Drop";
                String sectionId = Objects.toString(model.getValueAt(row, 1), "");
                String courseCode = Objects.toString(model.getValueAt(row, 0), "");
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Drop " + courseCode + "?",
                        "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    Student_Service svc = new Student_Service();
                    int result = svc.dropCourse(username, sectionId);
                    if (result == 1) {
                        new Toast(Studentdashboard.this, "Section Dropped!");
                        model.removeRow(row);
                    } else {
                        new Toast(Studentdashboard.this, "Error in Dropping");
                    }
                }
            }
            clicked = false;
            return "Drop";
        }
    }
    private JPanel buildTimetablePanel() {
        JPanel panel = new JPanel(new GridLayout(6, 6, 2, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        String[][] timetable = {
                {"", "Mon", "Tue", "Wed", "Thu", "Fri"},
                {"9-10", "CS101", "", "MA102", "", ""},
                {"10-11", "CS101", "", "MA102", "PH103", ""},
                {"11-12", "", "HS104", "", "PH103", ""},
                {"2-3", "PH103", "", "", "", "HS104"},
                {"3-4", "", "", "", "", ""},
        };
        Color slotColor = new Color(0,190,120,200);
        for (int r = 0; r < timetable.length; r++) {
            for (int c = 0; c < timetable[0].length; c++) {
                JLabel lbl = new JLabel(timetable[r][c], SwingConstants.CENTER);
                lbl.setOpaque(true);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
                lbl.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                if (r == 0 || c == 0) {
                    lbl.setBackground(new Color(220,255,235));
                    lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
                } else if (!timetable[r][c].isEmpty()) {
                    lbl.setBackground(slotColor);
                    lbl.setForeground(Color.WHITE);
                } else {
                    lbl.setBackground(new Color(245,255,250));
                }

                panel.add(lbl);
            }
        }
        return panel;
    }
    private JPanel buildGradesPanel() {
        String[] cols = {"Course Code","Quiz","Midsems","Endsem","Total","Grade"};
        Student_Service studentService = new Student_Service();
        Object[][] data = studentService.get_grades(username);

        DefaultTableModel model = new DefaultTableModel(data, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        styleTable(table, DARK_GREEN);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < cols.length; i++) table.getColumnModel().getColumn(i).setCellRenderer(center);

        JTextField[] searchBox = new JTextField[1];

        JPanel panel = createSearchableTablePanel("My Grades", LIGHT_GREEN, table, searchBox);
        JButton csvbutton = new JButton("Download Transcript (CSV)");
        csvbutton.setBackground(new Color(0,128,90));
        csvbutton.setForeground(Color.WHITE);
        csvbutton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JButton pdfbutton = new JButton("Download Transcript (PDF)");
        pdfbutton.setBackground(new Color(0,190,120));
        pdfbutton.setForeground(Color.WHITE);
        pdfbutton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(csvbutton);
        btnPanel.add(pdfbutton);
        panel.add(btnPanel, BorderLayout.SOUTH);
        csvbutton.addActionListener(e -> {
            try {
                FileWriter fw = new FileWriter("transcript.csv");
                PrintWriter pw = new PrintWriter(fw);
                for (int c = 0; c < table.getColumnCount(); c++) {
                    pw.print(table.getColumnName(c));
                    if (c < table.getColumnCount() - 1) pw.print(",");
                }
                pw.println();
                for (int r = 0; r < table.getRowCount(); r++) {
                    for (int c = 0; c < table.getColumnCount(); c++) {
                        pw.print(table.getValueAt(r, c));
                        if (c < table.getColumnCount() - 1) pw.print(",");
                    }
                    pw.println();
                }
                pw.close();
                JOptionPane.showMessageDialog(this, "Transcript saved as transcript.csv");
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving CSV");
            }
        });
        pdfbutton.addActionListener(e -> {
            try {
                FileOutputStream out = new FileOutputStream("transcript.pdf");
                StringBuilder sb = new StringBuilder();
                sb.append("IIITD Transcript\n\n");
                sb.append("Student: ").append(username).append("\n\n");
                for (int c = 0; c < table.getColumnCount(); c++) {
                    sb.append(table.getColumnName(c)).append("   ");
                }
                sb.append("\n----------------------------------------\n");
                for (int r = 0; r < table.getRowCount(); r++) {
                    for (int c = 0; c < table.getColumnCount(); c++) {
                        sb.append(table.getValueAt(r, c)).append("   ");
                    }
                    sb.append("\n");
                }

                out.write(sb.toString().getBytes());
                out.close();
                JOptionPane.showMessageDialog(this, "Transcript saved as transcript.pdf");
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving PDF");
            }
        });
        return panel;
    }

    private JPanel buildProfilePanel() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(Color.WHITE);
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT_GREEN, 2, true),
                BorderFactory.createEmptyBorder(20,25,20,25)
        ));
        GridBagConstraints ggbbcc = new GridBagConstraints();
        ggbbcc.insets = new Insets(10,10,10,10);
        Student_Access studentAccess = new Student_Access();
        Object[] arr = studentAccess.getStudentbyUsername(username);
        int roll_no = (int) arr[0];
        String student_name = (String) arr[1];
        JLabel name = new JLabel("Student name: " + student_name);
        name.setFont(new Font("Segoe UI", Font.BOLD, 22));
        name.setForeground(DARK_GREEN);
        JLabel email = new JLabel("username: " + username);
        email.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JLabel roll = new JLabel("Roll No: " + roll_no);
        roll.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        ggbbcc.gridy = 0; card.add(name, ggbbcc);
        ggbbcc.gridy = 1; card.add(email, ggbbcc);
        ggbbcc.gridy = 2; card.add(roll, ggbbcc);
        outer.add(card);
        return outer;
    }
    private void applyTabIcons() {
        for (int i = 0; i < tabs.getTabCount(); i++) {
            String title = tabs.getTitleAt(i);
            Color color;
            if (title.contains("Catalog")) color = LIGHT_GREEN;
            else if (title.contains("Registrations")) color = new Color(0,150,110);
            else if (title.contains("Timetable")) color = new Color(0,170,120);
            else if (title.contains("Grades")) color = new Color(0,128,90);
            else color = Color.GRAY;
            tabs.setIconAt(i, new InstructorDashboard.CircleIcon(color));
        }
    }
}