package view.dialogs;

import controller.MainController;

import javax.swing.*;
import java.awt.*;

public class AddPhotographerDialog extends AddBaseDialog {

    private final MainController controller;
    private final JTextField styleField = new JTextField(15);

    public AddPhotographerDialog(Frame owner, MainController controller) {
        super(owner, "Добавить фотографа");
        this.controller = controller;
        initComponents();
    }

    @Override
    protected JPanel buildExtraPanel() {
        JPanel p = new JPanel(new GridLayout(1, 2));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        p.add(new JLabel("Стиль фото:"));
        p.add(styleField);
        return p;
    }

    @Override
    protected void onOk() {
        if (baseFieldsInvalid()) return;
        controller.addPhotographer(parsedName(), parsedAge(), parsedExp(), styleField.getText().trim());
        dispose();
    }
}
