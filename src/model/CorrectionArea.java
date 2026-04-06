package model;

public enum CorrectionArea {
    PHOTO("Правки фото"),
    DESIGN("Правки дизайна"),
    VIDEO("Правки видео"),
    EDITING("Обработка/Монтаж"),
    ARTICLE("Правки текста/статей");

    private final String description;

    CorrectionArea(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
