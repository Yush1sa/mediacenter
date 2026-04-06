package view;

import model.MediaWorker;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class WorkerTableModel extends AbstractTableModel {

    private static final String[] columns = {"Имя", "Тип", "Возраст", "Стаж", "Доп. инфо"};

    private List<MediaWorker> displayList = new ArrayList<>();

    public void setData(List<MediaWorker> workers) {
        this.displayList = new ArrayList<>(workers);
        fireTableDataChanged();
    }

    public MediaWorker getWorkerAt(int row) {
        return displayList.get(row);
    }

    @Override
    public int getRowCount() {
        return displayList.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int col) {
        return columns[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        MediaWorker w = displayList.get(row);
        switch (col) {
            case 0:
                return w.getName();
            case 1:
                return w.getTypeName();
            case 2:
                return w.getAge();
            case 3:
                return w.getExperience();
            case 4:
                return w.getExtraInfo();
            default:
                return "";
        }
    }
}
