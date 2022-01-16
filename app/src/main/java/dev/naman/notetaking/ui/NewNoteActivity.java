package dev.naman.notetaking.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputLayout;

import dev.naman.notetaking.R;
import dev.naman.notetaking.model.Note;

public class NewNoteActivity extends AppCompatActivity {

    private static final String TAG = "NewNoteActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        Note note = (Note) getIntent().getSerializableExtra("note");

        if (note == null) {
            Log.d(TAG, "onCreate: null");
        } else {
            Log.d(TAG, "onCreate: got a note");
        }

        TextInputLayout mPriorityMenu = findViewById(R.id.priority_menu);
        String[] menu_option = {"Urgent", "High", "Medium", "Low", "Default"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.menu_item, menu_option);
        AutoCompleteTextView menu = (AutoCompleteTextView) mPriorityMenu.getEditText();
        menu.setAdapter(adapter);
    }
}