package model;

public abstract class MediaWorker {
    private String fullName;
    private int age;
    private int experience;

    public MediaWorker(String name, int age, int experience) {
        this.fullName = name;
        this.age = age;
        this.experience = experience;
    }

    public String getName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public int getExperience() {
        return experience;
    }

    public void setName(String name) {
        this.fullName = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setExperience(int e) {
        this.experience = e;
    }

    public abstract void doWork();

    public abstract String getExtraInfo();

    public String getTypeName() {
        return getClass().getSimpleName();
    }
}
