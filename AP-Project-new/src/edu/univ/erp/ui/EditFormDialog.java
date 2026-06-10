package frontend;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class EditFormDialog extends JDialog {

    private boolean saved = false;
    private HashMap<String, JTextField> fields = new HashMap<>();

    public EditFormDialog(JFrame parent, String title, String[] labels, String[] initialValues) {
        super(parent, title, true);
        setLayout(new BorderLayout(10, 10));
        setSize(350, 400);
        setLocationRelativeTo(parent);

        JPanel form = new JPanel(new GridLayout(labels.length, 1, 5, 5));

        for (int i = 0; i < labels.length; i++) {
            JPanel row = new JPanel(new BorderLayout());
            JLabel l = new JLabel(labels[i] + ":");
            JTextField tf = new JTextField(initialValues[i]);
            fields.put(labels[i], tf);

            row.add(l, BorderLayout.NORTH);
            row.add(tf, BorderLayout.CENTER);

            form.add(row);
        }

        add(form, BorderLayout.CENTER);

        JButton save = new JButton("Save");
        save.addActionListener(e -> {
            if (validateFields()) {
                saved = true;
                dispose();
            }
        });

        JPanel bottom = new JPanel();
        bottom.add(save);
        add(bottom, BorderLayout.SOUTH);
    }

    private boolean validateFields() {
        boolean ok = true;

        for (JTextField tf : fields.values()) {
            if (tf.getText().trim().isEmpty()) {
                tf.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                ok = false;
            } else {
                tf.setBorder(UIManager.getBorder("TextField.border"));
            }
        }

        return ok;
    }

    public boolean isSaved() {
        return saved;
    }

    public String get(String label) {
        return fields.get(label).getText().trim();
    }
}
