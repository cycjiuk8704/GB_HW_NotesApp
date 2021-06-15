package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    NoteListFragment noteListFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        NoteDataClass noteOne = new NoteDataClass("note1", "someNoteDescription1", "date", "there might be your text");

        noteListFrag = NoteListFragment.newInstance(noteOne);

        FragmentTransaction f1Trans = getSupportFragmentManager().beginTransaction();
        f1Trans.add(R.id.fragmentContainerView, noteListFrag);
        f1Trans.commit();

    }
}