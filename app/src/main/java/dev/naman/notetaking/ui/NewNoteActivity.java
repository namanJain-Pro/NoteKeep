package dev.naman.notetaking.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dev.naman.notetaking.R;
import dev.naman.notetaking.model.Note;
import dev.naman.notetaking.model.State;

public class NewNoteActivity extends AppCompatActivity {

    private static final String TAG = "NewNoteActivity";
    private State state;
    private final String[] menu_option = {"Urgent", "High", "Medium", "Low", "Default"};

    // Initializing
    private Note note;
    private TextInputLayout mPriorityMenu;
    private TextView mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        note = (Note) getIntent().getSerializableExtra("note");

        mPriorityMenu = findViewById(R.id.priority_menu);
        mDate = findViewById(R.id.date);

        if (note == null) {
            Log.d(TAG, "onCreate: Creating new note");
            state = State.New;
            note = new Note();
            createNewNote();
        } else {
            Log.d(TAG, "onCreate: got a note");
            state = State.Existing;
        }

    }

    private void createNewNote() {
        // setting up priority menu
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.menu_item, menu_option);
        AutoCompleteTextView menu = (AutoCompleteTextView) mPriorityMenu.getEditText();

        if (menu != null) {
            menu.setAdapter(adapter);
            menu.setText(menu_option[4], false);
            menu.setOnItemClickListener((adapterView, view, i, l) -> {
                Log.d(TAG, "onCreate: item clicked " + menu_option[i]);
                note.setPriority(i);
                switch (i) {
                    case 0:
                        note.setColor("#ffb8b8");
                        break;
                    case 1:
                        note.setColor("#ffcfb8");
                        break;
                    case 2:
                        note.setColor("#ebffb8");
                        break;
                    case 3:
                        note.setColor("#b8faff");
                        break;
                    default:
                        note.setColor("#fefff0");
                        break;
                }
            });
        }

        // setting the date
        String date = new SimpleDateFormat("EEEE dd-MM-yyyy", new Locale("en", "IN")).format(new Date());
        note.setDate(date);
        mDate.setText(date.split(" ")[0] + "\n" + date.split(" ")[1]);
    }
}