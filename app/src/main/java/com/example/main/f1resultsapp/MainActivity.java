package com.example.main.f1resultsapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.main.f1resultsapp.Fragments.FavouritesFragment;
import com.example.main.f1resultsapp.Fragments.RaceFragment;
import com.example.main.f1resultsapp.Fragments.ScheduleFragment;
import com.example.main.f1resultsapp.Fragments.StandingsFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.main.f1resultsapp.Fragments.FavouritesFragment.favDriv;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigationView = findViewById(R.id.nav_bar);

        final RaceFragment raceFragment = new RaceFragment();
        final StandingsFragment standingsFragment = new StandingsFragment();
        final ScheduleFragment scheduleFragment = new ScheduleFragment();
        final FavouritesFragment favouritesFragment = new FavouritesFragment();

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.race){
                    setFragment(raceFragment);
                    return true;
                } else if (id == R.id.standings){
                    setFragment(standingsFragment);
                    return true;
                } else if (id == R.id.schedule){
                    setFragment(scheduleFragment);
                    return true;
                }else if (id == R.id.favourites){
                    setFragment(favouritesFragment);
                    return true;
                }
                return false;
            }
        });
        navigationView.setSelectedItemId(R.id.race);
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment).addToBackStack(null).commit();
    }
}