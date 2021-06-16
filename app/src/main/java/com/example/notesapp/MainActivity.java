package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements INavigator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NoteDataClass noteOne = new NoteDataClass("note1", "someNoteDescription1", "date", "there might be your text");

        showNotes(noteOne);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
        showNoteDetails(noteOne);
        }

    }

    @Override
    public void showNotes(@NonNull NoteDataClass note) {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            showFragment(NoteListFragment.newInstance(note), R.id.fragmentContainerView);
        } else {
            showFragment(NoteListFragment.newInstance(note), R.id.fragmentContainerView2);
        }
    }

    @Override
    public void showNoteDetails(@NonNull NoteDataClass note) {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
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

}