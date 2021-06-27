package com.example.notesapp;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.notesapp.data.NoteDataClass;
import com.example.notesapp.data.NoteSourceImpl;
import com.example.notesapp.observe.Publisher;
import com.example.notesapp.ui.EditNoteFragment;
import com.example.notesapp.ui.INavigator;
import com.example.notesapp.ui.IToolbarHolder;
import com.example.notesapp.ui.NoteListFragment;
import com.example.notesapp.ui.NoteTextFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements INavigator, IToolbarHolder {

    private NoteSourceImpl noteSource;
    private List<NoteDataClass> noteData;
    private Toolbar toolbar;
    private final Publisher<NoteSourceImpl> publisher = new Publisher<>();

    public Publisher<NoteSourceImpl> getPublisher() {
        return publisher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clearBackStack();
        initData();
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        initDrawer(toolbar);

        showNotes(noteSource);

        if (!isPortrait()) {
            showNoteDetails(noteSource, 0);
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
        return (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }

    @Override
    public void showNoteDetails(@NonNull NoteSourceImpl note, int position) {
        if (isPortrait()) {
            showFragment(NoteTextFragment.newInstance(note, position), R.id.fragmentContainerView);
        } else {
            showFragment(NoteTextFragment.newInstance(note, position), R.id.fragmentContainerView3);
        }
    }

    @Override
    public void showEditNoteDetails(@NonNull NoteSourceImpl note, int position) {
        if (isPortrait()) {
            showFragment(EditNoteFragment.newInstance(note, position), R.id.fragmentContainerView);
        } else {
            showFragment(EditNoteFragment.newInstance(note, position), R.id.fragmentContainerView3);
        }
    }

    @Override
    public void showAddNote() {
        NoteDataClass emptyNote = new NoteDataClass("", "", "", "");
        noteSource.addNoteData(emptyNote);
        if (isPortrait()) {
            showFragment(EditNoteFragment.newInstance(noteSource, noteSource.size() - 1), R.id.fragmentContainerView);
        } else {
            showFragment(EditNoteFragment.newInstance(noteSource, noteSource.size() - 1), R.id.fragmentContainerView3);
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
        toggle.setToolbarNavigationClickListener(v -> onBackPressed());
    }

    private boolean canGoBack() {
        return (getSupportFragmentManager().getBackStackEntryCount() > 1);
    }

    private boolean isEditFragmentCurrent() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (canGoBack() && fragment != null && fragment.isVisible() && fragment instanceof EditNoteFragment) {
                return ((EditNoteFragment) fragment).onBackPress();
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (isPortrait()) {
            if (canGoBack() && !isEditFragmentCurrent()) {
                super.onBackPressed();
            } else if (!canGoBack()) {
                finish();
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    private boolean navigateFragment(int id) {

        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, id + "there might be settings fragment", Toast.LENGTH_SHORT).show();
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

    @Override
    public Toolbar requireToolbar() {
        return toolbar;
    }
}
