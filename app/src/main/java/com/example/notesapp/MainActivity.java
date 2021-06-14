package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    NoteListFragment noteListFrag;
    private TextView noteName;
    private TextView noteDescription;
    private TextView noteDateOfCreation;
    private TextView noteText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        NoteDataClass noteOne = new NoteDataClass("note1", "someNoteDescrikption1", "date", "there might be your text");

        noteListFrag = new NoteListFragment();


        FragmentTransaction f1Trans = getSupportFragmentManager().beginTransaction();
        f1Trans.add(R.id.fragmentContainerView, noteListFrag);
        f1Trans.commit();

        noteName = findViewById(R.id.noteName);
        noteDescription = findViewById(R.id.noteDescription);
        noteDateOfCreation = findViewById(R.id.noteDate);

        noteName.setText(noteOne.getName());
        noteDescription.setText(noteOne.getDescription());
        noteDateOfCreation.setText(noteOne.getDateOfCreation());
    }
}