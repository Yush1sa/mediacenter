package view.dialogs;

import controller.MainController;
import model.MediaWorker;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FilterDialog extends JDialog {

    private final MainController controller;

    public FilterDialog(Frame owner, MainController controller) {
        super(owner, "Фильтры", true);
        this.controller = controller;
        initComponents();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Тип", buildTypePanel());
        tabs.addTab("Возраст и Стаж", buildNumericPanel());
        add(tabs, BorderLayout.CENTER);

        JButton resetBtn = new JButton("Показать всех");
        resetBtn.addActionListener(e -> {
            controller.applyFilter(controller.getAllWorkers());
            dispose();
        });
        add(resetBtn, BorderLayout.SOUTH);
    }

    private JPanel buildTypePanel() {
        JPanel p = new JPanel(new GridLayout(3, 1, 5, 5));

        JButton btnPhoto = new JButton("Фотографы");
        btnPhoto.addActionListener(e -> applyAndClose(controller.filterPhotographers()));

        JButton btnVideo = new JButton("Видеографы");
        btnVideo.addActionListener(e -> applyAndClose(controller.filterVideographers()));

        JButton btnInfo = new JButton("Информ. работники");
        btnInfo.addActionListener(e -> applyAndClose(controller.filterInfoWorkers()));

        p.add(btnPhoto);
        p.add(btnVideo);
        p.add(btnInfo);
        return p;
    }

    private JPanel buildNumericPanel() {
        JPanel p = new JPanel(new GridLayout(3, 1, 10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel ageRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField minAge = new JTextField("0", 3);
        JTextField maxAge = new JTextField("100", 3);
        JButton ageBtn = new JButton("Фильтр по возрасту");
        ageRow.add(new JLabel("От:"));
        ageRow.add(minAge);
        ageRow.add(new JLabel("До:"));
        ageRow.add(maxAge);
        ageRow.add(ageBtn);
        ageBtn.addActionListener(e -> {
            try {
                int min = Integer.parseInt(minAge.getText().trim());
                int max = Integer.parseInt(maxAge.getText().trim());
                applyAndClose(controller.filterByAge(min, max));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Введите числа!");
            }
        });

        JPanel expRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField expField = new JTextField("0", 3);
        JButton expBtn = new JButton("Фильтр по стажу");
        expRow.add(new JLabel("Мин. стаж:"));
        expRow.add(expField);
        expRow.add(expBtn);
        expBtn.addActionListener(e -> {
            try {
                int minExp = Integer.parseInt(expField.getText().trim());
                applyAndClose(controller.filterByExperience(minExp));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Введите число!");
            }
        });

        p.add(ageRow);
        p.add(expRow);
        return p;
    }


    private void applyAndClose(List<MediaWorker> result) {
        controller.applyFilter(result);
        dispose();
    }
}
