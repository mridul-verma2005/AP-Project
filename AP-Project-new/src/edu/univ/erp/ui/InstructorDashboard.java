
package edu.univ.erp.ui;
import edu.univ.erp.data.Grade_Access;
import edu.univ.erp.data.Section_Access;
import edu.univ.erp.data.Settings_Access;
import edu.univ.erp.service.Instructor_Service;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;

public class InstructorDashboard extends Basedashboard {
    private boolean MAINTENANCE_MODE;

    private static final Color DARK_GREEN  = new Color(0,128,90);
    private static final Color LIGHT_GREEN = new Color(0,190,120);
    private JTable gradesTable;
    private DefaultTableModel gradesModel;
    private JLabel currentSectionLabel;
    private final String username;
    private Map<String, Object[][]> sectionGrades = new HashMap<>();
    public InstructorDashboard(String username1) {
        this.username = username1;
        super("Instructor Dashboard", DARK_GREEN, LIGHT_GREEN);
        JPanel profilePanel   = createProfilePanel();
        JPanel sectionsPanel  = createMySectionsPanel();
        JPanel gradesPanel    = createEnterGradesPanel();
        JPanel statsPanel     = createStatisticsPanel();
        tabs.addTab("Profile", profilePanel);
        tabs.addTab(" My Sections", sectionsPanel);
        tabs.addTab("️ Enter Grades", gradesPanel);
        tabs.addTab(" Statistics", statsPanel);
        applyTabIcons();
    }
    private JPanel createProfilePanel() {
        Instructor_Service instructorService = new Instructor_Service();
        Object data[] = instructorService.get_details(username);
        String department = (String) data[1];
        String instructor_name = (String) data[0];
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT_GREEN, 2, true),
                BorderFactory.createEmptyBorder(20,25,20,25)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        JLabel name = new JLabel("Instructor: " + instructor_name);
        name.setFont(new Font("Segoe UI", Font.BOLD, 22));
        name.setForeground(DARK_GREEN);
        JLabel email = new JLabel("username: " + username);
        email.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JLabel dept = new JLabel("Department: " + department);
        dept.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        gbc.gridy = 0; card.add(name, gbc);
        gbc.gridy = 1; card.add(email, gbc);
        gbc.gridy = 2; card.add(dept, gbc);
        panel.add(card);
        return panel;
    }

    private JPanel createMySectionsPanel() {
        String[] cols = {"Section ID", "Course", "Semester", "Enrolled", "Action"};
        Section_Access section_Access = new Section_Access();
        Object[][] data = section_Access.getAllSectionByUsername(username);
        DefaultTableModel model = new DefaultTableModel(data, cols){
            public boolean isCellEditable(int r, int c){ return false; }
        };
        JTable table = new JTable(model);
        styleTable(table, DARK_GREEN);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0; i<4; i++)
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        table.getColumn("Action").setCellRenderer(new OpenGradesButtonRenderer());
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int viewRow = table.rowAtPoint(evt.getPoint());
                int col     = table.columnAtPoint(evt.getPoint());
                if (viewRow >= 0 && col == 4) {
                    Settings_Access settingsAccess = new Settings_Access();
                    if(settingsAccess.getsetting("maintenance") == 1){
                        MAINTENANCE_MODE = true;
                    }
                    else {
                        MAINTENANCE_MODE = false;
                    }
                    if (MAINTENANCE_MODE) {
                        JOptionPane.showMessageDialog(
                                InstructorDashboard.this,
                                "Maintenance Mode is ON.\nInstructor actions are temporarily disabled."
                        );
                        return;
                    }
                    int modelRow = table.convertRowIndexToModel(viewRow);
                    String sec = table.getModel().getValueAt(modelRow, 0).toString();
                    String course = table.getModel().getValueAt(modelRow, 1).toString();
                    currentSectionLabel.setText(" Enter Grades — " + sec + " (" + course + ")");
                    loadGradesForSection(sec);
                    tabs.setSelectedIndex(2);
                }
            }
        });
        JTextField[] searchBox = new JTextField[1];
        return createSearchableTablePanel(
                " My Sections", LIGHT_GREEN, table, searchBox
        );
    }
    private class OpenGradesButtonRenderer extends JButton implements TableCellRenderer {
        public OpenGradesButtonRenderer(){
            setOpaque(true);
            setBackground(LIGHT_GREEN);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 13));
            setText("Open Grades");
        }
        public Component getTableCellRendererComponent(JTable t,Object v,boolean s,boolean f,int r,int c){
            return this;
        }
    }
    private JPanel createEnterGradesPanel() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        currentSectionLabel = new JLabel("️ Enter Grades — (No Section Selected)",
                SwingConstants.CENTER);
        currentSectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        currentSectionLabel.setForeground(DARK_GREEN);
        wrapper.add(currentSectionLabel, BorderLayout.NORTH);
        String[] cols = {"Student ID", "Name", "Quiz", "Midsems", "Endsems", "Total", "Grade"};
        gradesModel = new DefaultTableModel(cols, 0){
            public boolean isCellEditable(int r, int c){
                return c == 2 || c == 3 || c == 4;
            }
        };
        gradesTable = new JTable(gradesModel);
        styleTable(gradesTable, DARK_GREEN);
        if (MAINTENANCE_MODE) {
            gradesTable.setEnabled(false);
        }
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<cols.length;i++)
            gradesTable.getColumnModel().getColumn(i).setCellRenderer(center);

        JTextField[] searchBox = new JTextField[1];
        JPanel searchPanel = createSearchableTablePanel(
                "Enter Grades", LIGHT_GREEN, gradesTable, searchBox
        );
        wrapper.add(searchPanel, BorderLayout.CENTER);
        JButton computeBtn = new JButton("Compute Totals & Grades");
        computeBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        computeBtn.setBackground(LIGHT_GREEN);
        computeBtn.setForeground(Color.WHITE);
        computeBtn.addActionListener(e -> {
            if (MAINTENANCE_MODE) {
                JOptionPane.showMessageDialog(this, "Maintenance Mode is ON.");
                return;
            }
            computeGrades();
        });
        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.add(computeBtn);
        JButton saveBtn = new JButton("Save Grades");
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        saveBtn.setBackground(LIGHT_GREEN);
        saveBtn.setForeground(Color.WHITE);;
        saveBtn.addActionListener(e -> {
            if (MAINTENANCE_MODE) {
                JOptionPane.showMessageDialog(this, "Maintenance Mode is ON.");
                return;
            }
            saveGrades();
        });
        bottom.add(saveBtn);
        wrapper.add(bottom, BorderLayout.SOUTH);
        return wrapper;
    }
    private void saveGrades() {
        if (gradesTable.isEditing()) {
            gradesTable.getCellEditor().stopCellEditing();
        }
        Grade_Access gradeAccess = new Grade_Access();
        String sectionId = currentSectionLabel.getText().split("—")[1].trim().split(" ")[0];
        for(int i=0;i<gradesModel.getRowCount();i++){
            Object qObj = gradesModel.getValueAt(i, 2);
            Object mObj = gradesModel.getValueAt(i, 3);
            Object eObj = gradesModel.getValueAt(i, 4);
            if (qObj.toString().trim().isEmpty() ||  mObj.toString().trim().isEmpty() || eObj.toString().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cant be empty");
                return;
            }
            String studentUsername = gradesModel.getValueAt(i,0).toString();
            int quiz      = Integer.parseInt(gradesModel.getValueAt(i,2).toString());
            int midsems   = Integer.parseInt(gradesModel.getValueAt(i,3).toString());
            int endsems   = Integer.parseInt(gradesModel.getValueAt(i,4).toString());
            int total     = Integer.parseInt(gradesModel.getValueAt(i,5).toString());
            String grade  = gradesModel.getValueAt(i,6).toString();
            System.out.println("Saving: " + studentUsername + ", " + sectionId + ", quiz=" + quiz + ", midsems=" + midsems + ", endsems=" + endsems + ", total=" + total + ", grade=" + grade);
            gradeAccess.updateGrade(studentUsername, sectionId, quiz, midsems, endsems, total, grade);
        }

        JOptionPane.showMessageDialog(this, "Grades saved to backend successfully!");
    }
    private void computeGrades() {
        for(int i=0;i<gradesModel.getRowCount();i++){
            Object qObj = gradesModel.getValueAt(i, 2);
            Object mObj = gradesModel.getValueAt(i, 3);
            Object eObj = gradesModel.getValueAt(i, 4);
            if (qObj.toString().trim().isEmpty() ||  mObj.toString().trim().isEmpty() || eObj.toString().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cant be empty");
                return;
            }
            try {
                int quiz = Integer.parseInt(gradesModel.getValueAt(i,2).toString());
                int mid  = Integer.parseInt(gradesModel.getValueAt(i,3).toString());
                int endterms  = Integer.parseInt(gradesModel.getValueAt(i,4).toString());
                System.out.println(endterms);
                System.out.println(mid);
                System.out.println(quiz);
                int total = quiz + mid + endterms;
                String grade =
                        (total>=90)?"A":
                                (total>=80)?"B":
                                        (total>=70)?"C":
                                                (total>=60)?"D":"F";

                gradesModel.setValueAt(total, i, 5);
                gradesModel.setValueAt(grade, i, 6);

            } catch(Exception ex){
                gradesModel.setValueAt(0, i, 5);
                gradesModel.setValueAt("F", i, 6);
            }
        }
        JOptionPane.showMessageDialog(this, "Grades computed successfully!");
    }
    private void loadGradesForSection(String sec) {
        gradesModel.setRowCount(0);
        Instructor_Service instructorService = new Instructor_Service();
        Object[][] rows = instructorService.get_AllStudentsOfSection(sec);
        System.out.println("\n=== LOADING GRADES FOR SECTION: " + sec + " ===");

        if (rows != null) {
            int i = 0;
            for (Object[] r : rows) {
                System.out.println("Row " + i + " → " + java.util.Arrays.toString(r));
                if (r.length >= 5) {
                    System.out.println("   quiz=" + r[2] +
                            " midsem=" + r[3] +
                            " endsem=" + r[4] +
                            " total=" + (r.length > 5 ? r[5] : "NULL") +
                            " grade=" + (r.length > 6 ? r[6] : "NULL"));
                } else {
                    System.out.println("   ERROR: Row does not contain enough columns!");
                }
                gradesModel.addRow(r);
                i++;
            }
        }
        System.out.println("=== DONE LOADING ===\n");
    }
    private JPanel createStatisticsPanel() {

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridLayout(0, 1, 10, 10));
        JLabel title = new JLabel("📊 Section Statistics", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(DARK_GREEN);
        panel.add(title);
        String sectionId = null;
        try {
            sectionId = currentSectionLabel.getText().split("—")[1].trim().split(" ")[0];
        } catch (Exception ignored) {}

        int[][] scores = null;

        if (sectionId != null && !sectionId.contains("No Section")) {
            Instructor_Service is = new Instructor_Service();
            Object[][] rows = is.get_AllStudentsOfSection(sectionId);
            scores = new int[rows.length][3];
            for (int i = 0; i < rows.length; i++) {
                try {
                    scores[i][0] = Integer.parseInt(rows[i][2].toString());
                    scores[i][1] = Integer.parseInt(rows[i][3].toString());
                    scores[i][2] = Integer.parseInt(rows[i][4].toString());
                } catch (Exception ex) {
                    scores[i][0] = scores[i][1] = scores[i][2] = 0;
                }
            }
        }
        if (scores == null || scores.length == 0) {
            panel.add(new JLabel("No data available. Select a section first.", SwingConstants.CENTER));
            return panel;
        }
        int maxQ = 0, maxM = 0, maxE = 0;
        int minQ = 999, minM = 999, minE = 999;
        double sumQ = 0, sumM = 0, sumE = 0;
        for (int[] s : scores) {
            maxQ = Math.max(maxQ, s[0]);
            maxM = Math.max(maxM, s[1]);
            maxE = Math.max(maxE, s[2]);

            minQ = Math.min(minQ, s[0]);
            minM = Math.min(minM, s[1]);
            minE = Math.min(minE, s[2]);

            sumQ += s[0];
            sumM += s[1];
            sumE += s[2];
        }

        int n = scores.length;
        panel.add(new JLabel("Highest Quiz Score: " + maxQ));
        panel.add(new JLabel("Lowest Quiz Score : " + minQ));
        panel.add(new JLabel("Average Quiz Score: " + (sumQ / n)));
        panel.add(new JLabel("Highest Midsem Score: " + maxM));
        panel.add(new JLabel("Lowest Midsem Score : " + minM));
        panel.add(new JLabel("Average Midsem Score: " + (sumM / n)));
        panel.add(new JLabel("Highest Endsem Score: " + maxE));
        panel.add(new JLabel("Lowest Endsem Score : " + minE));
        panel.add(new JLabel("Average Endsem Score: " + (sumE / n)));

        return panel;
    }

    private void applyTabIcons() {

        for(int i=0; i<tabs.getTabCount(); i++){
            String t = tabs.getTitleAt(i);
            Color c;

            if(t.contains("Profile"))     c = LIGHT_GREEN;
            else if(t.contains("Sections"))c = new Color(0,170,120);
            else if(t.contains("Enter"))   c = new Color(0,150,110);
            else if(t.contains("Statistics")) c = DARK_GREEN;
            else                           c = Color.GRAY;

            tabs.setIconAt(i, new CircleIcon(c));
        }
    }
    public static class CircleIcon implements Icon {

        private final Color color;

        public CircleIcon(Color c){ this.color = c; }

        public void paintIcon(Component c, Graphics g, int x, int y){
            g.setColor(color);
            g.fillOval(x, y, getIconWidth(), getIconHeight());
        }
        public int getIconWidth(){ return 12; }
        public int getIconHeight(){ return 12; }
    }
}
