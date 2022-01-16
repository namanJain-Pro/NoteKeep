package dev.naman.notetaking.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


import dev.naman.notetaking.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private NoteViewModel mNoteViewModel;

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
        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        // Setting Up User Profile
        FirebaseUser user = mAuth.getCurrentUser();
        Picasso.get().load(user.getPhotoUrl()).into(mProfileImage);
        mProfileImage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        // Checking if database has any data and updating UI
        if (mNoteViewModel.getAllNotes().getValue() == null) {
            mEmptyAnimation.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mEmptyAnimation.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }

        // Setting on click listener for new note button
        mNewNoteBTN.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
            startActivity(intent);
        });
    }
}