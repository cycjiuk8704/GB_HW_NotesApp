package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements INavigator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);

        NoteDataClass noteOne = new NoteDataClass("note1", "someNoteDescription1", "date", "there might be your text");

        showNotes(noteOne);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showNoteDetails(noteOne);
        }

    }

    @Override
    public void showNotes(@NonNull NoteDataClass note) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            showFragment(NoteListFragment.newInstance(note), R.id.fragmentContainerView);
        } else {
            showFragment(NoteListFragment.newInstance(note), R.id.fragmentContainerView2);
        }
    }

    @Override
    public void showNoteDetails(@NonNull NoteDataClass note) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            showFragment(NoteTextFragment.newInstance(note), R.id.fragmentContainerView);
        } else {
            showFragment(NoteTextFragment.newInstance(note), R.id.fragmentContainerView3);
        }
    }

    private void showFragment(@NonNull Fragment fragment, int fragmentContainerView) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(fragmentContainerView, fragment)
                .addToBackStack("name")
                .commit();
    }

    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (navigateFragment(id)){
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });
    }


    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Toast.makeText(MainActivity.this, id + "selected", Toast.LENGTH_SHORT).show();

        if (navigateFragment(id)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    private boolean navigateFragment(int id) {
        switch (id) {
            case R.id.action_settings:
                Toast.makeText(MainActivity.this, id + "there might be settings fragment", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_add:
                Toast.makeText(MainActivity.this, id + "there might be adding note fragment", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return true;
    }


}