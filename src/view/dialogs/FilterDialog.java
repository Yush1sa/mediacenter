package view.dialogs;

import controller.MainController;
import model.MediaWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FilterDialog extends JDialog {

    private final MainController controller;

    JTabbedPane tabs;
    JPanel panelBtn;
    JButton resetBtn;
    JButton btnPhoto;
    JButton btnVideo;
    JButton btnInfo;



    public FilterDialog(Frame owner, MainController controller) {
        super(owner, "Фильтры", true);
        this.controller = controller;
        initComponents();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        tabs = new JTabbedPane();
        tabs.addTab("Тип", buildTypePanel());
        add(tabs, BorderLayout.CENTER);

        resetBtn = new JButton("Показать всех");
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.applyFilter(controller.getAllWorkers());
                dispose();
            }
        });
        add(resetBtn, BorderLayout.SOUTH);
    }

    private JPanel buildTypePanel() {
        panelBtn = new JPanel(new GridLayout(3, 1, 5, 5));

        btnPhoto = new JButton("Фотографы");
        btnPhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyAndClose(controller.filterPhotographers());
            }
        });

        btnVideo = new JButton("Видеографы");
        btnVideo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyAndClose(controller.filterVideographers());
            }
        });

        btnInfo = new JButton("Информ. работники");
        btnInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyAndClose(controller.filterInfoWorkers());
            }
        });

        panelBtn.add(btnPhoto);
        panelBtn.add(btnVideo);
        panelBtn.add(btnInfo);
        return panelBtn;
    }

    private void applyAndClose(List<MediaWorker> result) {
        controller.applyFilter(result);
        dispose();
    }
}