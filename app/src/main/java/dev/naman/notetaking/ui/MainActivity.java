package dev.naman.notetaking.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import dev.naman.notetaking.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initializing the toolbar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mAuth = FirebaseAuth.getInstance();
        makeDrawer();
    }

    private void makeDrawer() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            // Account Section
            AccountHeader accountSection = new AccountHeaderBuilder()
                    .withActivity(this)
                    .addProfiles(
                            new ProfileDrawerItem().withName(user.getDisplayName()).withEmail(user.getEmail())
                    )
                    .withSelectionListEnabledForSingleProfile(false)
                    .build();

            // Menu items
            PrimaryDrawerItem item = new PrimaryDrawerItem().withIdentifier(1).withName("All notes").withIcon(R.drawable.ic_baseline_note_24);


            Drawer navDrawer = new DrawerBuilder()
                    .withActivity(this)
                    .withToolbar(mToolbar)
                    .addDrawerItems(
                            item
                    )
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            Log.d(TAG, "onItemClick: Drawer item click " + position);
                            return false;
                        }
                    })
                    .withAccountHeader(accountSection)
                    .build();
        }
    }
}