package dev.naman.notetaking.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "note_table")
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "color")
    private String color;

    @ColumnInfo(name = "priority")
    private int priority;

    public Note() {
        this.id = 0;
    }

    public Note(int id, String content, String title, String date, String color, int priority) {
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

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id && priority == note.priority && Objects.equals(content, note.content) && Objects.equals(title, note.title) && Objects.equals(date, note.date) && Objects.equals(color, note.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, title, date, color, priority);
    }
}
