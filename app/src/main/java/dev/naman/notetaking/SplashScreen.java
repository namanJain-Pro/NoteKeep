package dev.naman.notetaking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private Intent activityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user != null) {
                    activityIntent = new Intent(SplashScreen.this, MainActivity.class);
                } else {
                    activityIntent = new Intent(SplashScreen.this, LoginActivity.class);
                }
                startActivity(activityIntent);
                finish();
            }
        }, 2500);
    }
}