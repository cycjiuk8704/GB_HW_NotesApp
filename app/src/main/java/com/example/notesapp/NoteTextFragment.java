package com.example.notesapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class NoteTextFragment extends BaseFragment {

    private static final String NOTE_STATE = "state";
    private NoteDataClass noteDataClass;

    public static NoteTextFragment newInstance(NoteDataClass noteDataClass) {
        NoteTextFragment noteTextFragment = new NoteTextFragment();

        Bundle args = new Bundle();
        args.putParcelable(NOTE_STATE, noteDataClass);
        noteTextFragment.setArguments(args);
        return noteTextFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            noteDataClass = getArguments().getParcelable(NOTE_STATE);
        }

        View v = inflater.inflate(R.layout.fragment_note_text, null);
        TextView nameTV = (TextView) v.findViewById(R.id.noteName);
        TextView textTV = (TextView) v.findViewById(R.id.noteText);
        nameTV.setText(noteDataClass.getName());
        textTV.setText(noteDataClass.getNoteText());

        return v;
    }
}