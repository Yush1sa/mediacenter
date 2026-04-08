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
    JLabel infoLabel;
    JPanel actions;
    JButton doWorkBtn;
    JButton extraBtn;


    public GiveWorkDialog(Frame owner, MainController controller, int row) {
        super(owner, "Выдать работу: " + controller.getWorkerName(row), true);
        this.controller = controller;
        this.row = row;
        initComponents();
        pack();
        setMinimumSize(new Dimension(400, 250));
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 10));

        infoLabel = new JLabel(controller.getWorkerDisplayName(row));
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        add(infoLabel, BorderLayout.NORTH);

        actions = new JPanel(new GridLayout(0, 1, 5, 5));
        actions.setBorder(BorderFactory.createTitledBorder("Доступные действия"));

        doWorkBtn = new JButton("Выполнить работу");
        doWorkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log(controller.doWork(row));
            }
        });
        actions.add(doWorkBtn);

        String workerType = controller.getWorkerType(row);

        extraBtn = createExtraActionButton(workerType);
        if (extraBtn != null) {
            actions.add(extraBtn);
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

    private JButton createExtraActionButton(String workerType) {

        if ("Photographer".equals(workerType)) {
            JButton btn = new JButton("Снять в стиле: " + controller.getPhotoStyle(row));
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    log(controller.shootPhotoStyle(row));
                }
            });
            return btn;
        }

        if ("Videographer".equals(workerType)) {
            JButton btn = new JButton("Снять с дрона");
            btn.setEnabled(controller.hasDrone(row));
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    log(controller.shootWithDrone(row));
                }
            });
            return btn;
        }

        if ("InformationWorker".equals(workerType)) {
            JButton btn = new JButton("Выдать камеру");
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    log(controller.giveCamera(row));
                }
            });
            return btn;
        }

        return null;
    }
}
