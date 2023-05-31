package com.example.researchsample;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.researchsample.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SessionManager session;
    DrawerLayout drawer;
    FrameLayout frameLayout;
    private boolean shouldLoadHomeFragOnBackPress = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadFragment(new StoreFragment());
        //imgVw.setBackgroundResource(R.drawable.applogo);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "ধন্যবাদ, আমাদের সাথে থাকার জন্য। যোগাযোগ করুন ০১৮১৩...", Snackbar.LENGTH_LONG)
                        .setAction("close", null).show();
            }
        });

        DatabaseHandler db = new DatabaseHandler(MainActivity.this);
        //user = db.getUserDetails();

        // session manager
        session = new SessionManager(MainActivity.this);

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }*/
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            finish();
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


          if (id == R.id.nav_addthesis) {
              //finish();
              getCallingActivity();
              Intent intent = new Intent(MainActivity.this, AddThesisActivity.class);
              startActivity(intent);

        }

          else if (id == R.id.nav_Studentthesis)
          {
              //finish();
              getCallingActivity();
              Intent intent = new Intent(MainActivity.this, StudentThesisActivity.class);
              startActivity(intent);
          }

          else if (id == R.id.nav_Teacherthesis)
          {
              //finish();
              getCallingActivity();
              Intent intent = new Intent(MainActivity.this, TeacherThesisActivity.class);
              startActivity(intent);
          }

          else if (id == R.id.nav_ThesisIdea)
          {
              //finish();
              getCallingActivity();
              Intent intent = new Intent(MainActivity.this, AddThesisIdeaActivity.class);
              startActivity(intent);
          }
          else if (id == R.id.nav_ThesisIdeaList)
          {
              //finish();
              getCallingActivity();
              Intent intent = new Intent(MainActivity.this, ThesisIdeaListActivity.class);
              startActivity(intent);
          }

          else if (id == R.id.nav_reference)
          {
              //finish();
              getCallingActivity();
              Intent intent = new Intent(MainActivity.this, ReferenceActivity.class);
              startActivity(intent);
          }

          else if (id == R.id.nav_Profile)
          {
              //finish();
              getCallingActivity();
              Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
              startActivity(intent);
          }

          else if (id == R.id.nav_logout) {
            logoutUser();
            drawer.closeDrawers();
            return true;
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutUser() {
        session.setLogin(false);
        // Launching the login activity
        Functions logout = new Functions();
        logout.logoutUser(MainActivity.this);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
