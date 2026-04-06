package controller;

import model.*;
import view.MainFrame;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    private final StudentMediaCenter model;
    private MainFrame view;

    public MainController(StudentMediaCenter model) {
        this.model = model;
    }

    public void setView(MainFrame view) {
        this.view = view;
    }

    public void init() {
        addSampleData();
        refreshTable();
        bindEvents();
    }

    // ─── методы для диалогов добавления ───────────────────────────────────────

    public void addPhotographer(String name, int age, int exp, String style) {
        model.add(new Photographer(name, age, exp, style));
        refreshTable();
        view.setStatus("Добавлен: " + name);
    }

    public void addVideographer(String name, int age, int exp, boolean drone) {
        model.add(new Videographer(name, age, exp, drone));
        refreshTable();
        view.setStatus("Добавлен: " + name);
    }

    public void addInfoWorker(String name, int age, int exp, ArrayList<Boolean> checkedAreas) {
        ArrayList<CorrectionArea> areas = new ArrayList<>();
        CorrectionArea[] values = CorrectionArea.values();
        for (int i = 0; i < checkedAreas.size(); i++) {
            if (checkedAreas.get(i)) areas.add(values[i]);
        }
        model.add(new InformationWorker(name, age, exp, areas));
        refreshTable();
        view.setStatus("Добавлен: " + name);
    }

    // ─── методы для GiveWorkDialog ────────────────────────────────────────────

    public String getWorkerDisplayName(int row) {
        MediaWorker w = getWorkerAt(row);
        return w.getName() + " - " + w.getTypeName();
    }

    public String getWorkerType(int row) {
        return getWorkerAt(row).getClass().getSimpleName();
    }

    public String doWork(int row) {
        return getWorkerAt(row).getName() + " выполняет работу!";
    }

    public String shootPhotoStyle(int row) {
        return ((Photographer) getWorkerAt(row)).shootPhotoStyle();
    }

    public String getPhotoStyle(int row) {
        return ((Photographer) getWorkerAt(row)).getPhotoStyle();
    }

    public boolean hasDrone(int row) {
        return ((Videographer) getWorkerAt(row)).hasDrone();
    }

    public String shootWithDrone(int row) {
        return ((Videographer) getWorkerAt(row)).shootWithDrone();
    }

    public String giveCamera(int row) {
        return ((InformationWorker) getWorkerAt(row)).giveCamera();
    }

    // ─── методы для EditWorkerDialog ──────────────────────────────────────────

    public String getWorkerName(int row) {
        return getWorkerAt(row).getName();
    }

    public int getWorkerAge(int row) {
        return getWorkerAt(row).getAge();
    }

    public int getWorkerExperience(int row) {
        return getWorkerAt(row).getExperience();
    }

    public ArrayList<String> getCorrectionAreaNames() {
        ArrayList<String> names = new ArrayList<>();
        for (CorrectionArea area : CorrectionArea.values()) {
            names.add(area.getDescription());
        }
        return names;
    }

    public ArrayList<Boolean> getCheckedAreas(int row) {
        InformationWorker iw = (InformationWorker) getWorkerAt(row);
        ArrayList<Boolean> checked = new ArrayList<>();
        for (CorrectionArea area : CorrectionArea.values()) {
            checked.add(iw.getCorrectionAreas().contains(area));
        }
        return checked;
    }

    public void editWorker(int row, String name, int age, int exp,
                           String extraStyle, Boolean extraDrone, ArrayList<Boolean> checkedAreas) {
        MediaWorker w = getWorkerAt(row);
        w.setName(name);
        w.setAge(age);
        w.setExperience(exp);

        if (w instanceof Photographer p && extraStyle != null) {
            p.setPhotoStyle(extraStyle);
        } else if (w instanceof Videographer v && extraDrone != null) {
            v.setDroneAvailable(extraDrone);
        } else if (w instanceof InformationWorker iw && checkedAreas != null) {
            ArrayList<CorrectionArea> areas = new ArrayList<>();
            CorrectionArea[] values = CorrectionArea.values();
            for (int i = 0; i < checkedAreas.size(); i++) {
                if (checkedAreas.get(i)) areas.add(values[i]);
            }
            iw.setCorrectionAreas(areas);
        }

        refreshTable();
        view.setStatus("Изменён: " + name);
    }

    // ─── методы для FilterDialog ──────────────────────────────────────────────

    public List<MediaWorker> getAllWorkers() {
        return model.getWorkers();
    }

    public List<MediaWorker> filterPhotographers() {
        return model.filterByType(Photographer.class);
    }

    public List<MediaWorker> filterVideographers() {
        return model.filterByType(Videographer.class);
    }

    public List<MediaWorker> filterInfoWorkers() {
        return model.filterByType(InformationWorker.class);
    }

    public List<MediaWorker> filterByAge(int min, int max) {
        return model.filterByAge(min, max);
    }

    public List<MediaWorker> filterByExperience(int min) {
        return model.filterByExperience(min);
    }

    public void applyFilter(List<MediaWorker> result) {
        view.getTableModel().setData(result);
        view.setStatus("Фильтр применён. Найдено: " + result.size());
    }

    // ─── внутренние методы ────────────────────────────────────────────────────

    private void bindEvents() {

        view.onAddPhotographer(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                view.openAddPhotographerDialog();
            }
        });

        view.onAddVideographer(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                view.openAddVideographerDialog();
            }
        });

        view.onAddInfoWorker(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                view.openAddInfoWorkerDialog();
            }
        });

        view.onEdit(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                int row = view.getSelectedRow();
                if (row != -1) {
                    view.openEditDialog(row);
                }
            }
        });

        view.onDelete(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                int row = view.getSelectedRow();
                if (row == -1) return;

                String name = getWorkerName(row);
                if (view.confirmDelete(name)) {
                    model.remove(getWorkerAt(row));
                    refreshTable();
                    view.setStatus("Удалён: " + name);
                }
            }
        });

        view.onGiveWork(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                int row = view.getSelectedRow();
                if (row != -1) {
                    view.openGiveWorkDialog(row);
                }
            }
        });

        view.onAllWork(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                view.showInfo(model.doGroupWork());
            }
        });

        view.onFilter(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                view.openFilterDialog();
            }
        });

        view.onSearch(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                handleSearch();
            }
        });

        view.onRowSelect(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    view.setRowSelected(view.getSelectedRow() != -1);
                }
            }
        });
    }

    private void handleSearch() {
        String query = view.getSearchText();
        if (query.isEmpty()) {
            view.getTableModel().setData(model.getWorkers());
            view.setStatus("Сотрудников: " + model.getWorkers().size());
        } else {
            List<MediaWorker> found = model.filterByName(query);
            view.getTableModel().setData(found);
            view.setStatus("Поиск \"" + query + "\": найдено " + found.size());
        }
    }

    private MediaWorker getWorkerAt(int row) {
        return view.getTableModel().getWorkerAt(row);
    }

    private void refreshTable() {
        view.getTableModel().setData(model.getWorkers());
        view.setStatus("Сотрудников: " + model.getWorkers().size());
    }

    private void addSampleData() {
        ArrayList<CorrectionArea> areas = new ArrayList<>();
        areas.add(CorrectionArea.PHOTO);
        areas.add(CorrectionArea.DESIGN);

        model.add(new Photographer("Алиса Петрова", 25, 3, "Портрет"));
        model.add(new Photographer("Иван Соколов", 30, 8, "Репортаж"));
        model.add(new Videographer("Марина Новикова", 27, 5, true));
        model.add(new Videographer("Денис Орлов", 22, 2, false));
        model.add(new InformationWorker("Светлана Козлова", 35, 10, areas));
    }
}
