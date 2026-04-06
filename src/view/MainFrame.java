package view;

import controller.MainController;
import view.dialogs.*;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame extends JFrame {

    private final MainController controller;

    private JToolBar toolbar = new JToolBar();
    private JButton addBtn = new JButton("Добавить");
    private JButton editBtn = new JButton("Изменить");
    private JButton deleteBtn = new JButton("Удалить");
    private JButton giveWorkBtn = new JButton("Выдать работу");
    private JButton allWorkBtn = new JButton("Работать всем");
    private JButton filterBtn = new JButton("Фильтры");
    private JTextField searchField = new JTextField(8);

    private WorkerTableModel tableModel = new WorkerTableModel();
    private JTable table = new JTable(tableModel);

    private JPopupMenu addPopup = new JPopupMenu();
    private JMenuItem addPhotoItem = new JMenuItem("Фотограф");
    private JMenuItem addVideoItem = new JMenuItem("Видеограф");
    private JMenuItem addInfoItem = new JMenuItem("Инфо-работник");

    private JLabel statusLabel = new JLabel("Готово");

    public MainFrame(MainController controller) {
        super("Студенческий медиацентр");
        this.controller = controller;
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setMinimumSize(new Dimension(900, 450));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setLayout(new BorderLayout(5, 5));

        toolbar.setFloatable(false);
        toolbar.setBorderPainted(false);
        toolbar.setRollover(true);
        toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));

        addPopup.add(addPhotoItem);
        addPopup.add(addVideoItem);
        addPopup.add(addInfoItem);

        addBtn.addActionListener(e -> addPopup.show(addBtn, 0, addBtn.getHeight()));

        toolbar.add(addBtn);
        toolbar.add(editBtn);
        toolbar.add(deleteBtn);
        toolbar.addSeparator(new Dimension(10, 20));
        toolbar.add(giveWorkBtn);
        toolbar.add(allWorkBtn);
        toolbar.addSeparator(new Dimension(10, 20));
        toolbar.add(filterBtn);
        toolbar.addSeparator(new Dimension(10, 20));
        toolbar.add(new JLabel("Поиск:"));
        toolbar.add(searchField);
        add(toolbar, BorderLayout.NORTH);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoCreateRowSorter(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 8, 10, 8));
        add(statusLabel, BorderLayout.SOUTH);

        editBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        giveWorkBtn.setEnabled(false);
    }


    public void openAddPhotographerDialog() {
        new AddPhotographerDialog(this, controller).setVisible(true);
    }

    public void openAddVideographerDialog() {
        new AddVideographerDialog(this, controller).setVisible(true);
    }

    public void openAddInfoWorkerDialog() {
        new AddInfoWorkerDialog(this, controller).setVisible(true);
    }

    public void openEditDialog(int row) {
        new EditWorkerDialog(this, controller, row).setVisible(true);
    }

    public void openGiveWorkDialog(int row) {
        new GiveWorkDialog(this, controller, row).setVisible(true);
    }

    public void openFilterDialog() {
        FilterDialog dlg = new FilterDialog(this, controller);
        dlg.setVisible(true);
    }


    public void onAddPhotographer(ActionListener l) {
        addPhotoItem.addActionListener(l);
    }

    public void onAddVideographer(ActionListener l) {
        addVideoItem.addActionListener(l);
    }

    public void onAddInfoWorker(ActionListener l) {
        addInfoItem.addActionListener(l);
    }

    public void onEdit(ActionListener l) {
        editBtn.addActionListener(l);
    }

    public void onDelete(ActionListener l) {
        deleteBtn.addActionListener(l);
    }

    public void onGiveWork(ActionListener l) {
        giveWorkBtn.addActionListener(l);
    }

    public void onAllWork(ActionListener l) {
        allWorkBtn.addActionListener(l);
    }

    public void onFilter(ActionListener l) {
        filterBtn.addActionListener(l);
    }

    public void onSearch(DocumentListener l) {
        searchField.getDocument().addDocumentListener(l);
    }

    public void onRowSelect(ListSelectionListener l) {
        table.getSelectionModel().addListSelectionListener(l);
    }

    public WorkerTableModel getTableModel() {
        return tableModel;
    }

    public int getSelectedRow() {
        int viewRow = table.getSelectedRow();
        if (viewRow == -1) return -1;
        return table.convertRowIndexToModel(viewRow);
    }

    public String getSearchText() {
        return searchField.getText().trim();
    }

    public void setRowSelected(boolean selected) {
        editBtn.setEnabled(selected);
        deleteBtn.setEnabled(selected);
        giveWorkBtn.setEnabled(selected);
    }

    public void setStatus(String text) {
        statusLabel.setText(text);
    }

    public void showInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Информация", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean confirmDelete(String name) {
        return JOptionPane.showConfirmDialog(this,
                "Удалить сотрудника \"" + name + "\"?",
                "Подтверждение", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
