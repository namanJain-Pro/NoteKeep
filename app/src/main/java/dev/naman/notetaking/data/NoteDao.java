package dev.naman.notetaking.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import dev.naman.notetaking.model.Note;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM note_table")
    LiveData<List<Note>> getAllNotes();
}
