package dev.naman.notetaking.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "note_table")
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private final int id;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "color")
    private String color;

    @ColumnInfo(name = "priority")
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
