package view.dialogs;

import controller.InputValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AddBaseDialog extends JDialog {

    protected final JTextField nameField = new JTextField(15);
    protected final JTextField ageField = new JTextField(5);
    protected final JTextField experienceField = new JTextField(5);

    public AddBaseDialog(Frame owner, String title) {
        super(owner, title, true);
    }

    protected void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel baseForm = new JPanel(new GridLayout(3, 2, 5, 5));
        baseForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        baseForm.add(new JLabel("Имя:"));
        baseForm.add(nameField);
        baseForm.add(new JLabel("Возраст:"));
        baseForm.add(ageField);
        baseForm.add(new JLabel("Стаж (лет):"));
        baseForm.add(experienceField);

        JPanel extra = buildExtraPanel();
        JPanel center = new JPanel(new BorderLayout(5, 5));
        center.add(baseForm, BorderLayout.NORTH);
        if (extra != null) center.add(extra, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okBtn = new JButton("Добавить");
        JButton cancelBtn = new JButton("Отмена");
        buttons.add(okBtn);
        buttons.add(cancelBtn);
        add(buttons, BorderLayout.SOUTH);

        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOk();
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        pack();
        setLocationRelativeTo(getOwner());
    }

    protected abstract JPanel buildExtraPanel();

    protected abstract void onOk();

    protected boolean baseFieldsInvalid() {
        return InputValidator.isValid(
                this,
                nameField.getText().trim(),
                ageField.getText().trim(),
                experienceField.getText().trim()
        );
    }

    protected int parsedAge() {
        return Integer.parseInt(ageField.getText().trim());
    }

    protected int parsedExp() {
        return Integer.parseInt(experienceField.getText().trim());
    }

    protected String parsedName() {
        return nameField.getText().trim();
    }
}