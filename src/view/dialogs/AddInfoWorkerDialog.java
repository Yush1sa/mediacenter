package view.dialogs;

import controller.MainController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AddInfoWorkerDialog extends AddBaseDialog {

    private final MainController controller;
    private JCheckBox[] areaCheckBoxes;

    public AddInfoWorkerDialog(Frame owner, MainController controller) {
        super(owner, "Добавить инфо-работника");
        this.controller = controller;
        initComponents();
    }

    @Override
    protected JPanel buildExtraPanel() {
        ArrayList<String> areaNames = controller.getCorrectionAreaNames();
        areaCheckBoxes = new JCheckBox[areaNames.size()];

        JPanel p = new JPanel(new GridLayout(0, 1, 3, 3));
        p.setBorder(BorderFactory.createTitledBorder("Области правок"));
        for (int i = 0; i < areaNames.size(); i++) {
            areaCheckBoxes[i] = new JCheckBox(areaNames.get(i));
            p.add(areaCheckBoxes[i]);
        }
        return p;
    }

    @Override
    protected void onOk() {
        if (baseFieldsInvalid()) return;

        ArrayList<Boolean> checked = new ArrayList<>();
        for (JCheckBox cb : areaCheckBoxes) {
            checked.add(cb.isSelected());
        }

        controller.addInfoWorker(parsedName(), parsedAge(), parsedExp(), checked);
        dispose();
    }
}