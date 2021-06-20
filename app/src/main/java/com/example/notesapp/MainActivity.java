package com.example.notesapp;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements INavigator {

    private int stackCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);

        NoteDataClass noteOne = new NoteDataClass("note1", "someNoteDescription1", "date", "there might be your text");

        showNotes(noteOne);

        if (!isPortrait()) {
            showNoteDetails(noteOne);
        }

    }

    @Override
    public void showNotes(@NonNull NoteDataClass note) {
        if (isPortrait()) {
            showFragment(NoteListFragment.newInstance(note), R.id.fragmentContainerView);
        } else {
            showFragment(NoteListFragment.newInstance(note), R.id.fragmentContainerView2);
        }
    }

    private boolean isPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    @Override
    public void showNoteDetails(@NonNull NoteDataClass note) {
        if (isPortrait()) {
            showFragment(NoteTextFragment.newInstance(note), R.id.fragmentContainerView);
        } else {
            showFragment(NoteTextFragment.newInstance(note), R.id.fragmentContainerView3);
        }
    }

    private void showFragment(@NonNull Fragment fragment, int fragmentContainerView) {
        FragmentTransaction fTrans = getSupportFragmentManager()
                .beginTransaction().replace(fragmentContainerView, fragment);
        if (stackCount > 0) {
            fTrans.addToBackStack(null);
        }
        fTrans.commit();

        stackCount++;
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
            if (navigateFragment(id)) {
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });
        getSupportFragmentManager().addOnBackStackChangedListener(() -> toggle.setDrawerIndicatorEnabled(!canGoBack()));
        toggle.setToolbarNavigationClickListener(v -> {
                    if (canGoBack()) {
                        getSupportFragmentManager().popBackStack();
                    } else {
                        drawer.openDrawer(GravityCompat.START);
                    }
                }
        );
    }

    private boolean canGoBack() {
        return getSupportFragmentManager().getBackStackEntryCount() != 0;
    }


    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);

        final MenuItem search = toolbar.getMenu().findItem(R.id.action_search);
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

        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            return navigateFragment(id);
        });

        return toolbar;
    }

    @SuppressLint("NonConstantResourceId")
    private boolean navigateFragment(int id) {

        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, id + "there might be settings fragment", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.action_add) {
            Toast.makeText(MainActivity.this, id + "there might be adding note fragment", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.action_about) {
            Toast.makeText(MainActivity.this, id + "there might be information about application", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}