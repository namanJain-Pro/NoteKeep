package dev.naman.notetaking.model;

public class Note {

    private final int id;
    private String content;
    private String title;
    private String date;
    private String color;
    private Priority priority;

    public Note(int id, String content, String title, String date, String color, Priority priority) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.date = date;
        this.color = color;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getColor() {
        return color;
    }

    public Priority getPriority() {
        return priority;
    }
}
