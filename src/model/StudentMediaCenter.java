package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentMediaCenter {
    private final ArrayList<MediaWorker> workers = new ArrayList<>();

    public void add(MediaWorker worker) {
        workers.add(worker);
    }

    public void remove(MediaWorker worker) {
        workers.remove(worker);
    }

    public ArrayList<MediaWorker> getWorkers() {
        return workers;
    }

    public String doGroupWork() {
        if (workers.isEmpty()) return "Нет сотрудников.";
        StringBuilder sb = new StringBuilder("Все приступают к работе:\n");
        for (MediaWorker w : workers) {
            sb.append("• ").append(w.getName()).append(" (").append(w.getTypeName()).append(")\n");
        }
        return sb.toString();
    }


    public List<MediaWorker> filterByName(String query) {
        String q = query.toLowerCase();
        return workers.stream().filter(w -> w.getName().toLowerCase().contains(q)).collect(Collectors.toList());
    }

    public List<MediaWorker> filterByType(Class<? extends MediaWorker> type) {
        return workers.stream().filter(type::isInstance).collect(Collectors.toList());
    }

    public List<MediaWorker> filterByAge(int min, int max) {
        return workers.stream().filter(w -> w.getAge() >= min && w.getAge() <= max).collect(Collectors.toList());
    }

    public List<MediaWorker> filterByExperience(int minExp) {
        return workers.stream().filter(w -> w.getExperience() >= minExp).collect(Collectors.toList());
    }

}
