package view.dialogs;

import controller.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GiveWorkDialog extends JDialog {

    private final MainController controller;
    private final int row;
    private JTextArea logArea;

    public GiveWorkDialog(Frame owner, MainController controller, int row) {
        super(owner, "Выдать работу: " + controller.getWorkerName(row), true);
        this.controller = controller;
        this.row = row;
        initComponents();
        pack();
        setMinimumSize(new Dimension(350, 250));
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 10));

        JLabel infoLabel = new JLabel(controller.getWorkerDisplayName(row));
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        add(infoLabel, BorderLayout.NORTH);

        JPanel actions = new JPanel(new GridLayout(0, 1, 5, 5));
        actions.setBorder(BorderFactory.createTitledBorder("Доступные действия"));

        JButton doWorkBtn = new JButton("Выполнить работу");
        doWorkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log(controller.doWork(row));
            }
        });
        actions.add(doWorkBtn);

        String workerType = controller.getWorkerType(row);

        if (workerType.equals("Photographer")) {
            JButton styleBtn = new JButton("Снять в стиле: " + controller.getPhotoStyle(row));
            styleBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    log(controller.shootPhotoStyle(row));
                }
            });
            actions.add(styleBtn);
        }

        if (workerType.equals("Videographer")) {
            JButton droneBtn = new JButton("Снять с дрона");
            droneBtn.setEnabled(controller.hasDrone(row));
            droneBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    log(controller.shootWithDrone(row));
                }
            });
            actions.add(droneBtn);
        }

        if (workerType.equals("InformationWorker")) {
            JButton camBtn = new JButton("Выдать камеру");
            camBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    log(controller.giveCamera(row));
                }
            });
            actions.add(camBtn);
        }

        add(actions, BorderLayout.CENTER);

        logArea = new JTextArea(4, 30);
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setBorder(BorderFactory.createTitledBorder("Лог"));
        add(new JScrollPane(logArea), BorderLayout.SOUTH);
    }

    private void log(String msg) {
        logArea.append("• " + msg + "\n");
    }
}
