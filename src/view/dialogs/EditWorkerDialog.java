package view.dialogs;

import controller.InputValidator;
import controller.MainController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EditWorkerDialog extends JDialog {

    private final MainController controller;
    private final int row;

    private final JTextField nameField = new JTextField(15);
    private final JTextField ageField = new JTextField(5);
    private final JTextField experienceField = new JTextField(5);

    // поля для фотографа
    private JTextField styleField;
    // поля для видеографа
    private JCheckBox droneCheckBox;
    // поля для инфо-работника
    private JCheckBox[] areaCheckBoxes;

    private final String workerType;

    public EditWorkerDialog(Frame owner, MainController controller, int row) {
        super(owner, "Изменить: " + controller.getWorkerName(row), true);
        this.controller = controller;
        this.row = row;
        this.workerType = controller.getWorkerType(row);
        initComponents();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        form.add(new JLabel("Имя:"));
        form.add(nameField);
        form.add(new JLabel("Возраст:"));
        form.add(ageField);
        form.add(new JLabel("Стаж (лет):"));
        form.add(experienceField);

        nameField.setText(controller.getWorkerName(row));
        ageField.setText(String.valueOf(controller.getWorkerAge(row)));
        experienceField.setText(String.valueOf(controller.getWorkerExperience(row)));

        add(form, BorderLayout.NORTH);

        JPanel extra = buildExtraPanel();
        if (extra != null) add(extra, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Сохранить");
        JButton cancelBtn = new JButton("Отмена");
        buttons.add(saveBtn);
        buttons.add(cancelBtn);
        add(buttons, BorderLayout.SOUTH);

        saveBtn.addActionListener(e -> onSave());
        cancelBtn.addActionListener(e -> dispose());
    }

    private JPanel buildExtraPanel() {
        switch (workerType) {
            case "Photographer": {
                JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
                p.setBorder(BorderFactory.createTitledBorder("Параметры фотографа"));
                styleField = new JTextField(controller.getPhotoStyle(row), 15);
                p.add(new JLabel("Стиль фото:"));
                p.add(styleField);
                return p;
            }
            case "Videographer": {
                JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
                p.setBorder(BorderFactory.createTitledBorder("Параметры видеографа"));
                droneCheckBox = new JCheckBox("Есть дрон", controller.hasDrone(row));
                p.add(droneCheckBox);
                return p;
            }
            case "InformationWorker": {
                JPanel p = new JPanel(new GridLayout(0, 1, 3, 3));
                p.setBorder(BorderFactory.createTitledBorder("Области правок"));
                ArrayList<String> names = controller.getCorrectionAreaNames();
                ArrayList<Boolean> checked = controller.getCheckedAreas(row);
                areaCheckBoxes = new JCheckBox[names.size()];
                for (int i = 0; i < names.size(); i++) {
                    areaCheckBoxes[i] = new JCheckBox(names.get(i), checked.get(i));
                    p.add(areaCheckBoxes[i]);
                }
                return p;
            }
            default:
                return null;
        }
    }

    private void onSave() {
        String name = nameField.getText().trim();
        String ageStr = ageField.getText().trim();
        String expStr = experienceField.getText().trim();

        if (InputValidator.isValid(this, name, ageStr, expStr)) return;

        int age = Integer.parseInt(ageStr);
        int exp = Integer.parseInt(expStr);

        String extraStyle = styleField != null ? styleField.getText().trim() : null;
        Boolean extraDrone = droneCheckBox != null ? droneCheckBox.isSelected() : null;
        ArrayList<Boolean> checkedAreas = null;

        if (areaCheckBoxes != null) {
            checkedAreas = new ArrayList<>();
            for (JCheckBox cb : areaCheckBoxes) {
                checkedAreas.add(cb.isSelected());
            }
        }

        controller.editWorker(row, name, age, exp, extraStyle, extraDrone, checkedAreas);
        dispose();
    }
}
