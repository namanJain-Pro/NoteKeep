package dev.naman.notetaking.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import dev.naman.notetaking.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ImageView profile_image;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing variables
        profile_image = findViewById(R.id.profile_image);
        mAuth = FirebaseAuth.getInstance();

        // Setting Up User Profile
        FirebaseUser user = mAuth.getCurrentUser();
        Picasso.get().load(user.getPhotoUrl()).into(profile_image);
        profile_image.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        });

    }
}