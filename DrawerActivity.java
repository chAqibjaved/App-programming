package com.aqib.mymedreminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.aqib.mymedreminder.activity.AddMedicationActivity;
import com.google.android.material.navigation.NavigationView;

public class DrawerActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener {

   Toolbar toolbar;

    protected DrawerLayout drawerLayout;

    TextView tvName,tvid;

    protected NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        toolbar=findViewById(R.id.toolbar);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        setActionBar(toolbar);
        getActionBar().setTitle("Dashboard");
        View headerView = navigationView.getHeaderView(0);
        tvName = headerView.findViewById(R.id.tvName);
        tvid = headerView.findViewById(R.id.tvid);
        tvName.setText("Your Name");
        tvid.setText("your ID are here");
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_change_password){
           startActivity(new Intent(DrawerActivity.this, AddMedicationActivity.class));
        }
        // Close the drawer after handling the action
        drawerLayout.closeDrawer(GravityCompat.START);
        return true; // Indicate that the event was handled
    }


}