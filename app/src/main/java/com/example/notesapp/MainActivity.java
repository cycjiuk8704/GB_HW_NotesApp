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
import androidx.fragment.app.FragmentManager;

import com.example.notesapp.data.NoteDataClass;
import com.example.notesapp.data.NoteSourceImpl;
import com.example.notesapp.ui.EditNoteFragment;
import com.example.notesapp.ui.INavigator;
import com.example.notesapp.ui.NoteListFragment;
import com.example.notesapp.ui.NoteTextFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements INavigator {

    private NoteSourceImpl noteSource;
    private List<NoteDataClass> noteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clearBackStack();
        initData();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);

        showNotes(noteSource);

        if (!isPortrait()) {
            showNoteDetails(noteSource.getNoteData(0));
        }

    }

    @Override
    public void showNotes(@NonNull NoteSourceImpl note) {
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

    @Override
    public void showEditNoteDetails(@NonNull NoteDataClass note) {
        if (isPortrait()) {
            showFragment(EditNoteFragment.newInstance(note), R.id.fragmentContainerView);
        } else {
            showFragment(EditNoteFragment.newInstance(note), R.id.fragmentContainerView3);
        }
    }

    private void showFragment(@NonNull Fragment fragment, int fragmentContainerView) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(fragmentContainerView, fragment)
                .addToBackStack(null)
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
            if (navigateFragment(id)) {
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });
        getSupportFragmentManager().addOnBackStackChangedListener(() -> toggle.setDrawerIndicatorEnabled(!canGoBack() || !isPortrait()));
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
        return getSupportFragmentManager().getBackStackEntryCount() > 1;
    }

    @Override
    public void onBackPressed() {
        if (!canGoBack() && isPortrait()) {
            finish();
        } else {
            super.onBackPressed();
        }
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

    private void initData() {
        noteData = new ArrayList<NoteDataClass>() {
            {
                add(new NoteDataClass("note1", "description1", "date1", "some text 1"));
                add(new NoteDataClass("note2", "description2", "date2", "some text 2"));
                add(new NoteDataClass("note3", "description3", "date3", "some text 3"));
                add(new NoteDataClass("note4", "description4", "date4", "some text 4"));
                add(new NoteDataClass("note5", "description5", "date5", "some text 5"));
                add(new NoteDataClass("note6", "description6", "date6", "some text 6"));
                add(new NoteDataClass("note7", "description7", "date7", "some text 7"));
                add(new NoteDataClass("note8", "description8", "date8", "some text 8"));
                add(new NoteDataClass("note9", "description9", "date9", "some text 9"));
                add(new NoteDataClass("note10", "description10", "date10", "some text 10"));
            }
        };
        noteSource = new NoteSourceImpl(noteData);
    }

    private void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

}
