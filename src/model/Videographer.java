package model;

public class Videographer extends MediaWorker {
    private boolean droneAvailable;

    public Videographer(String name, int age, int experience, boolean droneAvailable) {
        super(name, age, experience);
        this.droneAvailable = droneAvailable;
    }

    public boolean hasDrone() {
        return droneAvailable;
    }

    public void setDroneAvailable(boolean d) {
        this.droneAvailable = d;
    }

    public String shootWithDrone() {
        if (!droneAvailable) return "У видеографа нет дрона!";
        return getName() + " выполняет съёмку с дрона!";
    }

    @Override
    public String getExtraInfo() {
        return droneAvailable ? "Есть дрон" : "Нет дрона";
    }

    @Override
    public void doWork() {
        System.out.println(getName() + " пошёл снимать видео");
    }

    @Override
    public String getTypeName() {
        return "Видеограф";
    }
}
