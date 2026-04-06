package model;

public class Photographer extends MediaWorker {
    private String photoStyle;

    public Photographer(String name, int age, int experience, String photoStyle) {
        super(name, age, experience);
        this.photoStyle = photoStyle;
    }

    public String getPhotoStyle() {
        return photoStyle;
    }

    public void setPhotoStyle(String s) {
        this.photoStyle = s;
    }

    public String shootPhotoStyle() {
        return getName() + " сделал фото в стиле: " + photoStyle;
    }

    @Override
    public String getExtraInfo() {
        return "Стиль: " + photoStyle;
    }

    @Override
    public void doWork() {
        System.out.println(getName() + " пошёл освещать мероприятие");
    }

    @Override
    public String getTypeName() {
        return "Фотограф";
    }
}
