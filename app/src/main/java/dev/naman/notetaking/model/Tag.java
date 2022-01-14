package dev.naman.notetaking.model;

public class Tag {

    private final int id;
    private final String name;
    private final int iconId;

    public Tag(int id, String name, int iconId) {
        this.id = id;
        this.name = name;
        this.iconId = iconId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getIconId() {
        return iconId;
    }
}
