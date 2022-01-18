package dev.naman.notetaking.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import dev.naman.notetaking.R;
import dev.naman.notetaking.model.Note;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private NoteViewModel mViewModel;
    private NoteRecyclerAdapter mAdapter;

    private ImageView mProfileImage;
    private RecyclerView mRecyclerView;
    private LottieAnimationView mEmptyAnimation;
    private FloatingActionButton mNewNoteBTN;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing variables
        mProfileImage = findViewById(R.id.profile_image);
        mRecyclerView = findViewById(R.id.recycler_view);
        mEmptyAnimation = findViewById(R.id.empty_lottie);
        mNewNoteBTN = findViewById(R.id.new_note_fab);

        mAuth = FirebaseAuth.getInstance();
        mViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        mAdapter = new NoteRecyclerAdapter();

        // Setting Up User Profile
        FirebaseUser user = mAuth.getCurrentUser();
        Picasso.get().load(user.getPhotoUrl()).into(mProfileImage);
        mProfileImage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        // Checking if database has any data and updating UI
        final Observer<List<Note>> observer = new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if (notes.isEmpty()) {
                    mEmptyAnimation.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                } else {
                    mEmptyAnimation.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }

                Log.d(TAG, "onChanged: List size: " + notes.size());
                mAdapter.submitList(notes);
            }
        };
        mViewModel.getAllNotes().observe(this, observer);

        // Setting on click listener for new note button
        mNewNoteBTN.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
            startActivity(intent);
        });

        // Setting up recycler view
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}