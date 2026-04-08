package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InformationWorker extends MediaWorker {
    private ArrayList<CorrectionArea> correctionAreas;

    public InformationWorker(String name, int age, int experience,
                             ArrayList<CorrectionArea> correctionAreas) {
        super(name, age, experience);
        this.correctionAreas = correctionAreas;
    }

    public List<CorrectionArea> getCorrectionAreas() {
        return correctionAreas;
    }

    public void setCorrectionAreas(ArrayList<CorrectionArea> a) {
        this.correctionAreas = a;
    }

    public String giveCamera() {
        return "Инфо-работник " + getName() + " выдал камеру на мероприятие";
    }

    @Override
    public String getExtraInfo() {
        String areas = correctionAreas.stream()
                .map(CorrectionArea::getDescription)
                .collect(Collectors.joining(", "));
        return "Правки: " + (areas.isEmpty() ? "нет" : areas);
    }

    @Override
    public void doWork() {
        System.out.println(getName() + " даёт правки по проделанной работе");
    }

    @Override
    public String getTypeName() {
        return "Инфо-работник";
    }
}
