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
import com.example.notesapp.data.NoteSource;
import com.example.notesapp.observe.Publisher;
import com.example.notesapp.ui.EditNoteFragment;
import com.example.notesapp.ui.INavigator;
import com.example.notesapp.ui.IToolbarHolder;
import com.example.notesapp.ui.NoteListFragment;
import com.example.notesapp.ui.NoteTextFragment;
import com.google.android.material.navigation.NavigationView;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity implements INavigator, IToolbarHolder {

    private Toolbar toolbar;
    private final Publisher<NoteSource> publisher = new Publisher<>();

    public Publisher<NoteSource> getPublisher() {
        return publisher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Logger.getLogger("TAG").log(Level.SEVERE, sha1AsString("sha"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        clearBackStack();
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        initDrawer(toolbar);

        showNotes();

    }

    @Override
    public void showNotes() {
        if (isPortrait()) {
            showFragment(new NoteListFragment(), R.id.fragmentContainerView);
        } else {
            showFragment(new NoteListFragment(), R.id.fragmentContainerView2);
        }
    }

    private boolean isPortrait() {
        return (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
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

    @Override
    public void showAddNote(NoteDataClass note) {
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

    public static byte[] sha1(@NonNull String str) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.update(str.getBytes(StandardCharsets.UTF_8));

        return digest.digest();
    }

    public static String sha1AsString(@NonNull String str) throws NoSuchAlgorithmException {
        return String.format("%X", ByteBuffer.wrap(sha1(str)).getLong());
    }
}

