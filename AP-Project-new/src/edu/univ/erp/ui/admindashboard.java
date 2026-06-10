package edu.univ.erp.ui;

import edu.univ.erp.data.Section_Access;
import edu.univ.erp.data.Settings_Access;
import edu.univ.erp.domain.Section;
import edu.univ.erp.service.Admin_Service;
import edu.univ.erp.service.Instructor_Service;
import edu.univ.erp.service.Student_Service;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class admindashboard extends Basedashboard {
    private static final Color DARK_GREEN  = new Color(0,128,90);
    private static final Color LIGHT_GREEN = new Color(0,190,120);
    private final String username;
    public admindashboard(String username) {
        this.username = username;
        super("Admin Dashboard", DARK_GREEN, LIGHT_GREEN);
        JPanel profilePanel   = createProfilePanel();
        JPanel usersPanel     = createManageUsersPanel();
        JPanel coursesPanel   = createManageCoursesPanel();
        JPanel sectionsPanel  = createManageSectionsPanel();
        JPanel maintenancePanel = createMaintenancePanel();
        tabs.addTab(" Profile", profilePanel);
        tabs.addTab(" Users", usersPanel);
        tabs.addTab(" Courses", coursesPanel);
        tabs.addTab(" Sections", sectionsPanel);
        tabs.addTab("️ Maintenance", maintenancePanel);
        applyTabIcons();
    }
    private JPanel createProfilePanel() {
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
        JLabel adminname = new JLabel("IIIT Delhi Administrator");
        adminname.setFont(new Font("Segoe UI", Font.BOLD, 24));
        adminname.setForeground(DARK_GREEN);
        JLabel email = new JLabel("username: " + username);
        email.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JLabel role = new JLabel("Role: admin");
        role.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        gbc.gridy = 0; card.add(adminname, gbc);
        gbc.gridy = 1; card.add(email, gbc);
        gbc.gridy = 2; card.add(role, gbc);
        panel.add(card);
        return panel;
    }
    private JPanel createManageUsersPanel() {
        String[] cols = {"User ID", "Status", "Role", "Edit"};
        Admin_Service adminService = new Admin_Service();
        Object[][] data = adminService.get_ALluser();
        DefaultTableModel model = new DefaultTableModel(data, cols){
            public boolean isCellEditable(int r, int c){
                return false;
            }
        };
        JTable table = new JTable(model);
        styleTable(table, DARK_GREEN);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i = 0; i < 3; i++)
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        table.getColumn("Edit").setCellRenderer(new ActionButtonRenderer("Edit", LIGHT_GREEN));
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int viewRow = table.rowAtPoint(e.getPoint());
                int viewCol = table.columnAtPoint(e.getPoint());
                if (viewRow < 0 || viewCol < 0) return;
                int row = table.convertRowIndexToModel(viewRow);
                String col = table.getColumnName(viewCol);
                if (col.equals("Edit")) {
                    editUserPopup(model, row);
                }
            }
        });
        JButton addUserBtn = new JButton("➕ Add User");
        styleActionButton(addUserBtn, LIGHT_GREEN);
        addUserBtn.addActionListener(e -> addUserPopup(model));
        JPanel panel = createSearchableTablePanel("👥 Manage Users", LIGHT_GREEN, table, new JTextField[1]);
        panel.add(addUserBtn, BorderLayout.SOUTH);
        return panel;
    }
    private void addUserPopup(DefaultTableModel model) {
        JTextField id = new JTextField();
        JTextField status = new JTextField();
        JComboBox<String> role = new JComboBox<>(new String[]{"Student","Instructor","Admin"});
        JPasswordField pass = new JPasswordField();
        JTextField rollNo = new JTextField();
        JTextField program = new JTextField();
        JTextField year = new JTextField();
        JTextField StudentName = new JTextField();
        JTextField instrName = new JTextField();
        JTextField instrDept = new JTextField();
        JPanel form = new JPanel(new GridLayout(0,1,6,6));
        form.add(new JLabel("User ID:"));  form.add(id);
        form.add(new JLabel("Status:"));   form.add(status);
        form.add(new JLabel("Role:"));     form.add(role);
        form.add(new JLabel("Password:")); form.add(pass);

        form.add(new JLabel("Roll No:"));  form.add(rollNo);
        form.add(new JLabel("Program:"));  form.add(program);
        form.add(new JLabel("Year:"));     form.add(year);
        form.add(new JLabel("Student Name:")); form.add(StudentName);
        form.add(new JLabel("Instructor Name:")); form.add(instrName);
        form.add(new JLabel("Department:")); form.add(instrDept);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(form, BorderLayout.CENTER);
        wrapper.setPreferredSize(new Dimension(500, 500)); // adjust ratio
        int res = JOptionPane.showConfirmDialog(
                this,
                wrapper,
                "Add User",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (res == JOptionPane.OK_OPTION) {
            if (id.getText().trim().isEmpty() || status.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,"User ID & Status required.");
                return;
            }
            if (new String(pass.getPassword()).trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,"Password required.");
                return;
            }
            Admin_Service s = new Admin_Service();
            Instructor_Service instructorService = new Instructor_Service();
            Student_Service studentService = new Student_Service();
            int result_student = 0;
            int result_inst = 0;
            int result_admin = 0;
            if (!rollNo.getText().trim().isEmpty() && instrDept.getText().trim().isEmpty()) {
                 result_student = studentService.add_student(
                        id.getText().trim(),
                        StudentName.getText().trim(),
                        program.getText().trim(),
                        Integer.parseInt(rollNo.getText().trim()),
                        Integer.parseInt(year.getText().trim())
                );
            } else if (!instrDept.getText().trim().isEmpty() && rollNo.getText().trim().isEmpty()) {
                 result_inst = instructorService.add_instrutor(
                        id.getText().trim(),
                        instrName.getText().trim(),
                        instrDept.getText().trim()
                );
            }
            else if(!instrDept.getText().trim().isEmpty() && !rollNo.getText().trim().isEmpty()) {
                result_admin = 1;
            }
            if ((result_student == 1) || (result_inst == 1) || (result_admin == 1)) {
                int result = s.add_user(
                        id.getText().trim(),
                        status.getText().trim(),
                        role.getSelectedItem().toString(),
                        new String(pass.getPassword()).trim()
                );
                if(result == 1){
                    model.addRow(new Object[]{
                            id.getText().trim(),
                            status.getText().trim(),
                            role.getSelectedItem().toString(),
                            "Edit"
                    });
                    JOptionPane.showMessageDialog(this,"User added.");
                }
            } else {
                JOptionPane.showMessageDialog(this,"","Error in adding", JOptionPane.ERROR_MESSAGE);
            }
        }


    }
    private void editUserPopup(DefaultTableModel model, int row) {

        String currentId = model.getValueAt(row,0).toString();
        String currentStatus = model.getValueAt(row,1).toString();
        String currentRole = model.getValueAt(row,2).toString();
        JTextField statusField = new JTextField(currentStatus);
        JComboBox<String> role_Field =
                new JComboBox<>(new String[]{"Student","Instructor","Admin"});
        role_Field.setSelectedItem(currentRole);
        JPasswordField passwordField = new JPasswordField();
        JPanel form = new JPanel(new GridLayout(0,1,6,6));
        form.add(new JLabel("User ID:"));
        JTextField identityFixed = new JTextField(currentId);
        identityFixed.setEditable(false);
        form.add(identityFixed);
        form.add(new JLabel("Status:"));
        form.add(statusField);
        form.add(new JLabel("Role:"));
        form.add(role_Field);
        form.add(new JLabel("New Password (optional):"));
        form.add(passwordField);
        int res = JOptionPane.showConfirmDialog(this, form, "Edit User",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            if (statusField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,"Status cannot be empty.");
                return;
            }
            Admin_Service s = new Admin_Service();
            int result;
            String newPassword = new String(passwordField.getPassword()).trim();
            if (newPassword.isEmpty()) {
                s.update_user_withoutpassword(
                        role_Field.getSelectedItem().toString(),
                        statusField.getText().trim(),
                        currentId
                );
                result = 1;
            }
            else {
                result = s.update_user_withpassword(
                        role_Field.getSelectedItem().toString(),
                        statusField.getText().trim(),
                        newPassword,
                        currentId
                );
            }
            if (result == 1) {
                model.setValueAt(statusField.getText().trim(), row, 1);
                model.setValueAt(role_Field.getSelectedItem(), row, 2);
                JOptionPane.showMessageDialog(this,"User updated.");
            }
        }
    }
    private JPanel createManageCoursesPanel() {
        String[] cols = {"Course Code","Title","Credits","Department","Edit","Delete"};
        Admin_Service adminService = new Admin_Service();
        Object[][] data = adminService.get_AllCourses();
        DefaultTableModel model = new DefaultTableModel(data, cols){
            public boolean isCellEditable(int r, int c){ return false; }
        };
        JTable table = new JTable(model);
        styleTable(table, DARK_GREEN);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<4;i++)
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        table.getColumn("Edit").setCellRenderer(new ActionButtonRenderer("Edit", LIGHT_GREEN));
        table.getColumn("Delete").setCellRenderer(new ActionButtonRenderer("Delete", new Color(200,0,0)));
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int view_Row = table.rowAtPoint(e.getPoint());
                int view_Col = table.columnAtPoint(e.getPoint());
                if (view_Row < 0 || view_Col < 0) return;
                int row = table.convertRowIndexToModel(view_Row);
                String col = table.getColumnName(view_Col);
                if (col.equals("Edit")) editedCoursesPop(model, row);
                if (col.equals("Delete")) {
                    int c = JOptionPane.showConfirmDialog(
                            admindashboard.this,"Delete this course?",
                            "Confirm",JOptionPane.YES_NO_OPTION
                    );
                    if (c == JOptionPane.YES_OPTION) {
                        String courseCode = model.getValueAt(row, 0).toString();
                        Admin_Service adminService1 = new Admin_Service();
                        adminService1.delete_course(courseCode);
                        model.removeRow(row);
                        JOptionPane.showMessageDialog(admindashboard.this,"Course removed.");
                    }
                }
            }
        });
        JTextField[] searchBox = new JTextField[1];
        JPanel panel = createSearchableTablePanel("📘 Manage Courses", LIGHT_GREEN, table, searchBox);
        JButton addBtn = new JButton("➕ Add Course");
        styleActionButton(addBtn);
        addBtn.addActionListener(e -> showAddCoursePopup(model));
        panel.add(addBtn, BorderLayout.SOUTH);
        return panel;
    }
    private void editedCoursesPop(DefaultTableModel model, int row) {
        String code = model.getValueAt(row,0).toString();
        String title = model.getValueAt(row,1).toString();
        String credits = model.getValueAt(row,2).toString();
        String dept = model.getValueAt(row,3).toString();
        JTextField tTitle = new JTextField(title);
        JTextField tCred = new JTextField(credits);
        JTextField tDept = new JTextField(dept);
        JPanel form = new JPanel(new GridLayout(0,1,6,6));
        JTextField codeField = new JTextField(code);
        codeField.setEditable(false);
        form.add(new JLabel("Course Code:")); form.add(codeField);
        form.add(new JLabel("Title:")); form.add(tTitle);
        form.add(new JLabel("Credits:")); form.add(tCred);
        form.add(new JLabel("Department:")); form.add(tDept);
        int res = JOptionPane.showConfirmDialog(
                this, form, "Edit Course",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (res == JOptionPane.OK_OPTION) {
            if (tTitle.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,"Title cannot be empty.");
                return;
            }
            String cr = tCred.getText().trim();
            if (!cr.matches("\\d+")) {
                JOptionPane.showMessageDialog(this,"Credits must be numeric.");
                return;
            }
            Admin_Service s = new Admin_Service();
            int result = s.update_course(code, tTitle.getText().trim(),
                    Integer.parseInt(cr), tDept.getText().trim());

            if (result == 1) {
                model.setValueAt(tTitle.getText().trim(), row, 1);
                model.setValueAt(cr, row, 2);
                model.setValueAt(tDept.getText().trim(), row, 3);
                JOptionPane.showMessageDialog(this,"Course updated.");
            }
        }
    }
    private void showAddCoursePopup(DefaultTableModel model) {
        JTextField code = new JTextField();
        JTextField title = new JTextField();
        JTextField credits = new JTextField();
        JTextField dept = new JTextField();
        JPanel form = new JPanel(new GridLayout(0,1,6,6));
        form.add(new JLabel("Course Code:")); form.add(code);
        form.add(new JLabel("Title:")); form.add(title);
        form.add(new JLabel("Credits:")); form.add(credits);
        form.add(new JLabel("Department:")); form.add(dept);
        int res = JOptionPane.showConfirmDialog(
                this, form, "Add Course",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (res == JOptionPane.OK_OPTION) {
            if (code.getText().trim().isEmpty() || title.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,"Code & Title required.");
                return;
            }
            String cr = credits.getText().trim();
            if (!cr.matches("\\d+")) {
                JOptionPane.showMessageDialog(this,"Credits must be numeric.");
                return;
            }
            Admin_Service s = new Admin_Service();
            int result = s.add_course(code.getText().trim(),
                    title.getText().trim(),
                    Integer.parseInt(cr),
                    dept.getText().trim()
            );
            if (result == 1) {
                model.addRow(new Object[]{
                        code.getText().trim(),
                        title.getText().trim(),
                        cr,
                        dept.getText().trim(),
                        "Edit",
                        "Delete"
                });
                JOptionPane.showMessageDialog(this,"Course added.");
            }
            else {
                JOptionPane.showMessageDialog(this,"Duplicate course.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private JPanel createManageSectionsPanel() {
        String[] cols = {
                "Section ID","Course","Instructor_Id","Time",
                "Room","Capacity","Current Avalible Seats","Year","Semester",
                "Edit","Delete"
        };
        Admin_Service adminService = new Admin_Service();
        Object[][] data = adminService.get_AllSections();
        DefaultTableModel model = new DefaultTableModel(data, cols){
            public boolean isCellEditable(int r, int c){ return false; }
        };
        JTable table = new JTable(model);
        styleTable(table, DARK_GREEN);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<5;i++)
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        table.getColumn("Edit").setCellRenderer(new ActionButtonRenderer("Edit", LIGHT_GREEN));
        table.getColumn("Delete").setCellRenderer(new ActionButtonRenderer("Delete", new Color(200,0,0)));
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int viewRow = table.rowAtPoint(e.getPoint());
                int viewCol = table.columnAtPoint(e.getPoint());
                if (viewRow < 0 || viewCol < 0) return;
                int row = table.convertRowIndexToModel(viewRow);

                String col = table.getColumnName(viewCol);

                if (col.equals("Edit")) {
                    editSectionPopup(model, row);
                }

                else if (col.equals("Delete")) {
                    int capacity = Integer.parseInt(model.getValueAt(row, 5).toString());
                    int available = Integer.parseInt(model.getValueAt(row, 6).toString());

                    int enrolled = capacity - available;
                    if (enrolled > 0) {
                        JOptionPane.showMessageDialog(
                                admindashboard.this,
                                "❌ Cannot delete section!\n" +
                                        "Students are already enrolled (" + enrolled + ").",
                                "Delete Blocked",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    int c = JOptionPane.showConfirmDialog(
                            admindashboard.this,
                            "Delete this section?",
                            "Confirm",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (c == JOptionPane.YES_OPTION) {
                        String sectionId = model.getValueAt(row, 0).toString();
                        Section_Access sectionAccess = new Section_Access();
                        sectionAccess.deleteSection(sectionId);
                        model.removeRow(row);
                        JOptionPane.showMessageDialog(
                                admindashboard.this,
                                "Section removed."
                        );
                    }
                }
            }
        });
        JPanel panel = createSearchableTablePanel("Manage Sections", LIGHT_GREEN, table, new JTextField[1]);
        JButton addBtn = new JButton("➕ Add Section");
        styleActionButton(addBtn);
        addBtn.addActionListener(e -> addSectionPopup(model));
        panel.add(addBtn, BorderLayout.SOUTH);
        return panel;
    }
    private void addSectionPopup(DefaultTableModel model) {
        JTextField sectionId = new JTextField();
        JTextField courseCode = new JTextField();
        JTextField instructor = new JTextField();
        JTextField daytime = new JTextField();
        JTextField room = new JTextField();
        JTextField capacity = new JTextField();
        JTextField currentSeats = new JTextField();
        JTextField semester = new JTextField();
        JTextField year = new JTextField();
        JPanel panel = new JPanel(new GridLayout(9, 2, 8, 8));
        panel.add(new JLabel("Section ID:"));
        panel.add(sectionId);
        panel.add(new JLabel("Course Code:"));
        panel.add(courseCode);
        panel.add(new JLabel("Instructor Username:"));
        panel.add(instructor);
        panel.add(new JLabel("Day & Time:"));
        panel.add(daytime);
        panel.add(new JLabel("Room:"));
        panel.add(room);
        panel.add(new JLabel("Capacity:"));
        panel.add(capacity);
        panel.add(new JLabel("Current Available Seats:"));
        panel.add(currentSeats);
        panel.add(new JLabel("Semester:"));
        panel.add(semester);
        panel.add(new JLabel("Year:"));
        panel.add(year);
        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Add New Section",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (result == JOptionPane.OK_OPTION) {
            try {
                int cap = Integer.parseInt(capacity.getText().trim());
                int cur = Integer.parseInt(currentSeats.getText().trim());
                int yr = Integer.parseInt(year.getText().trim());
                Section s = new Section(
                        sectionId.getText().trim(),
                        courseCode.getText().trim(),
                        instructor.getText().trim(),
                        daytime.getText().trim(),
                        room.getText().trim(),
                        cap,
                        cur,
                        semester.getText().trim(),
                        yr
                );
                Section_Access sectionAccess = new Section_Access();

                if((cap>0) && (cur >=0) && (yr > 0)){
                    int result1 = sectionAccess.addSection(s);
                    if(result1 == 1){
                        JOptionPane.showMessageDialog(null, "Section Added Successfully!");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Capacity, Current Seats and Year must be numbers and positives!");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Capacity, Current Seats and Year must be numbers and positives!");
            }
        }
    }

    private void editSectionPopup(DefaultTableModel model, int row) {

        String sid = model.getValueAt(row,0).toString();
        String course = model.getValueAt(row,1).toString();
        String inst = model.getValueAt(row,2).toString();
        String time = model.getValueAt(row,3).toString();
        String room = model.getValueAt(row,4).toString();
        String cap = model.getValueAt(row,5).toString();
        String avail = model.getValueAt(row,6).toString();
        String semester = model.getValueAt(row,7).toString();
        String year = model.getValueAt(row,8).toString();


        JTextField t1 = new JTextField(sid);
        JTextField t2 = new JTextField(course);
        JTextField t3 = new JTextField(inst);
        JTextField t4 = new JTextField(time);
        JTextField t5 = new JTextField(room);
        JTextField t6 = new JTextField(cap);
        JTextField t7 = new JTextField(avail);
        JTextField t8 = new JTextField(semester);
        JTextField t9 = new JTextField(year);
        JPanel form = new JPanel(new GridLayout(0,1,6,6));
        form.add(new JLabel("Section ID:")); form.add(t1);
        form.add(new JLabel("Course:")); form.add(t2);
        form.add(new JLabel("Instructor:")); form.add(t3);
        form.add(new JLabel("Time:")); form.add(t4);
        form.add(new JLabel("Room:")); form.add(t5);
        form.add(new JLabel("Capacity:")); form.add(t6);
        form.add(new JLabel("Available Seats:")); form.add(t7);
        form.add(new JLabel("Semester:")); form.add(t8);
        form.add(new JLabel("Year:")); form.add(t9);
        int res = JOptionPane.showConfirmDialog(this, form, "Edit Section",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (res == JOptionPane.OK_OPTION) {
            int capacity, available, yearVal;
            try { capacity = Integer.parseInt(t6.getText().trim()); }
            catch (Exception ex) { capacity = 0; }
            try { available = Integer.parseInt(t7.getText().trim()); }
            catch (Exception ex) { available = 0; }
            try { yearVal = Integer.parseInt(t9.getText().trim()); }
            catch (Exception ex) { yearVal = 0; }
            Section_Access sectionAccess = new Section_Access();
            sectionAccess.updateSection(
                    t2.getText().trim(),
                    t3.getText().trim(),
                    t4.getText().trim(),
                    t5.getText().trim(),
                    capacity,
                    available,
                    t8.getText().trim(),
                    yearVal,
                    t1.getText().trim()
            );
            model.setValueAt(t1.getText().trim(), row, 0);
            model.setValueAt(t2.getText().trim(), row, 1);
            model.setValueAt(t3.getText().trim(), row, 2);
            model.setValueAt(t4.getText().trim(), row, 3);
            model.setValueAt(t5.getText().trim(), row, 4);
            model.setValueAt(capacity, row, 5);
            model.setValueAt(available, row, 6);
            model.setValueAt(t8.getText().trim(), row, 7);
            model.setValueAt(yearVal, row, 8);

            JOptionPane.showMessageDialog(this,"Section updated.");
        }
    }
    private JPanel createMaintenancePanel() {
        Settings_Access s = new Settings_Access();
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        JLabel title = new JLabel("⚙️ Maintenance Mode", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(LIGHT_GREEN);
        panel.add(title, BorderLayout.NORTH);
        JPanel center = new JPanel();
        center.setOpaque(false);
        JLabel desc = new JLabel("Toggle maintenance mode (blocks student + instructor actions)");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        center.add(desc);
        panel.add(center, BorderLayout.CENTER);
        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        int current = s.getsetting("maintenance");
        JToggleButton toggle = new JToggleButton(
                current == 1 ? "Maintenance ON" : "Maintenance OFF"
        );
        toggle.setSelected(current == 1);
        toggle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        toggle.setBackground(new Color(230,230,230));
        toggle.setFocusPainted(false);

        toggle.addActionListener(e -> {
            if (toggle.isSelected()) {
                s.editsetting("maintenance", 1);
                toggle.setText("Maintenance ON");
                JOptionPane.showMessageDialog(this,"Maintenance Mode Enabled.");
            }
            else {
                s.editsetting("maintenance", 0);
                toggle.setText("Maintenance OFF");
                JOptionPane.showMessageDialog(this,"Maintenance Mode Disabled.");
            }
        });
        bottom.add(toggle);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }
    private static class ActionButtonRenderer extends JButton implements TableCellRenderer {
        public ActionButtonRenderer(String label, Color bg){
            setText(label);
            setOpaque(true);
            setBackground(bg);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 13));
            setFocusPainted(false);
        }
        public Component getTableCellRendererComponent(JTable t,Object v,
                                                       boolean s,boolean f,int r,int c){
            return this;
        }
    }
    private void styleActionButton(JButton b) {
        styleActionButton(b, LIGHT_GREEN);
    }
    private void styleActionButton(JButton b, Color bg) {
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
    }
    private void applyTabIcons() {
        for(int i=0;i<tabs.getTabCount();i++){
            String t = tabs.getTitleAt(i);

            Color c;
            if (t.contains("Profile"))      c = LIGHT_GREEN;
            else if (t.contains("Users"))   c = DARK_GREEN;
            else if (t.contains("Courses")) c = new Color(0,150,100);
            else if (t.contains("Sections"))c = new Color(0,170,115);
            else if (t.contains("Maintenance")) c = new Color(200,160,0);
            else                            c = Color.GRAY;

            tabs.setIconAt(i, new CircleIcon(c));
        }
    }

    public static class CircleIcon implements Icon {

        private final Color color;

        public CircleIcon(Color c){ this.color = c; }

        public void paintIcon(Component c, Graphics g, int x, int y){
            g.setColor(color);
            g.fillOval(x,y,12,12);
        }

        public int getIconWidth(){ return 12; }
        public int getIconHeight(){ return 12; }
    }
}

