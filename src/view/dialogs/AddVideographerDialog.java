package view.dialogs;

import controller.MainController;

import javax.swing.*;
import java.awt.*;

public class AddVideographerDialog extends AddBaseDialog {

    private final MainController controller;
    private final JCheckBox droneCheckBox = new JCheckBox("Есть дрон");

    public AddVideographerDialog(Frame owner, MainController controller) {
        super(owner, "Добавить видеографа");
        this.controller = controller;
        initComponents();
    }

    @Override
    protected JPanel buildExtraPanel() {
        JPanel p = new JPanel(new GridLayout(1, 2));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        p.add(new JLabel("Есть дрон?:"));
        p.add(droneCheckBox);
        return p;
    }

    @Override
    protected void onOk() {
        if (baseFieldsInvalid()) return;
        controller.addVideographer(parsedName(), parsedAge(), parsedExp(), droneCheckBox.isSelected());
        dispose();
    }
}
