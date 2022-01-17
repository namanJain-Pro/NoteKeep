package dev.naman.notetaking.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dev.naman.notetaking.R;
import dev.naman.notetaking.model.Note;
import dev.naman.notetaking.model.State;

public class NewNoteActivity extends AppCompatActivity {

    private static final String TAG = "NewNoteActivity";
    private State state;
    private NoteViewModel mViewModel;
    private final String[] menu_option = {"Urgent", "High", "Medium", "Low", "Default"};
    private final String[] colors = {"#ffb8b8", "#ffcfb8", "#ebffb8", "#b8faff", "#fefff0"};

    // Initializing
    private Note note;
    private TextInputLayout mPriorityMenu, mTitleEditText, mContentEditText;
    private TextView mDate;
    private Button mDeleteBTN;
    private MaterialToolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        note = (Note) getIntent().getSerializableExtra("note");
        mViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        mPriorityMenu = findViewById(R.id.priority_menu);
        mDate = findViewById(R.id.date);
        mTitleEditText = findViewById(R.id.title);
        mContentEditText = findViewById(R.id.content);
        mDeleteBTN = findViewById(R.id.delete_btn);
        mToolbar = findViewById(R.id.new_note_toolbar);

        if (note == null) {
            Log.d(TAG, "onCreate: Creating new note");
            state = State.New;
            note = new Note();
        } else {
            Log.d(TAG, "onCreate: got a note");
            state = State.Existing;
            mDeleteBTN.setVisibility(View.VISIBLE);
            setupExistingNote();
        }

        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.save) {
                Log.d(TAG, "onCreate: Note saved");
                save();
            }
            return false;
        });

        // Putting listener on EditText
        mTitleEditText.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (state == State.New) {
                    state = State.Unsaved;
                } else if (state == State.Existing) {
                    state = State.Updated;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mContentEditText.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (state == State.New) {
                    state = State.Unsaved;
                } else if (state == State.Existing) {
                    state = State.Updated;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (mDeleteBTN.getVisibility() == View.VISIBLE) {
            mDeleteBTN.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Delete " + note.getTitle())
                        .setMessage("Are you sure you want to delete " + note.getTitle() + " note?")
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            state = State.Delete;
                            mViewModel.delete(note);
                            onBackPressed();
                        })
                        .setNegativeButton("No", null)
                        .show();
            });
        }

        // Helper function call
        setupPriorityMenu();
        setupDate();
    }

    @Override
    public void onBackPressed() {
        // here we are giving a user a chance to save his stuff
        if (state == State.Unsaved || state == State.Updated) {
            new AlertDialog.Builder(this)
                    .setTitle("Are you sure?")
                    .setMessage("You're note will not be saved.")
                    .setPositiveButton("Yes", (dialogInterface, i) -> super.onBackPressed())
                    .setNegativeButton("No", null)
                    .show();
        } else {
            super.onBackPressed();
        }
    }

    private void setupPriorityMenu() {
        // setting up priority menu
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.menu_item, menu_option);
        AutoCompleteTextView menu = (AutoCompleteTextView) mPriorityMenu.getEditText();

        if (menu != null) {
            menu.setAdapter(adapter);
            if (state == State.New) {
                note.setPriority(4);
                menu.setText(menu_option[4], false);
            } else {
                menu.setText(menu_option[note.getPriority()], false);
            }
            menu.setOnItemClickListener((adapterView, view, i, l) -> {
                Log.d(TAG, "onCreate: item clicked " + menu_option[i]);
                note.setPriority(i);

                if (state == State.New) {
                    state = State.Unsaved;
                } else if (state == State.Existing) {
                    state = State.Updated;
                }

                note.setColor(colors[i]);
            });
        }
    }

    private void setupDate() {
        // setting the date
        if (state == State.New) {
            String date = new SimpleDateFormat("EEEE dd-MM-yyyy", new Locale("en", "IN")).format(new Date());
            date = date.split(" ")[0] + "\n" + date.split(" ")[1];
            note.setDate(date);
            mDate.setText(date);
        } else {
            mDate.setText(note.getDate());
        }
    }

    private void setupExistingNote() {
        if (mTitleEditText.getEditText() != null) {
            mTitleEditText.getEditText().setText(note.getTitle());
        }

        if (mContentEditText.getEditText() != null) {
            mContentEditText.getEditText().setText(note.getContent());
        }
    }

    private void save() {
        // Write a save function
        if (mTitleEditText.getEditText() != null) {
            if (mTitleEditText.getEditText().getText().toString().trim().isEmpty()) {
                note.setTitle("Untitled");
            } else {
                note.setTitle(mTitleEditText.getEditText().getText().toString().trim());
            }
        }

        if (mContentEditText.getEditText() != null) {
            note.setContent(mContentEditText.getEditText().getText().toString().trim());
        }

        if (state == State.Unsaved || state == State.New) {
            mViewModel.insert(note);
        } else if (state == State.Updated || state == State.Existing) {
            mViewModel.update(note);
        }
        state = State.Saved;
        onBackPressed();
    }
}