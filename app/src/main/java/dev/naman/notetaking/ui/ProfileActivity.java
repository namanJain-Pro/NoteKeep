package dev.naman.notetaking.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import dev.naman.notetaking.R;

public class ProfileActivity extends AppCompatActivity {

    private Button mLogout;
    private MaterialToolbar mToolbar;
    private ImageView mUserImage;
    private TextView mUserName;
    private TextView mUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mToolbar = findViewById(R.id.toolbar);
        mLogout = findViewById(R.id.logout_btn);
        mUserImage = findViewById(R.id.user_image);
        mUserName = findViewById(R.id.user_name);
        mUserEmail = findViewById(R.id.user_email);

        mToolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        mLogout.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        setUpUser();
    }

    private void setUpUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Picasso.get().load(user.getPhotoUrl()).into(mUserImage);
        mUserName.setText(user.getDisplayName());
        mUserEmail.setText(user.getEmail());
    }
}