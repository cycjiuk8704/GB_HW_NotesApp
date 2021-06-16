package com.example.notesapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NoteTextFragment extends Fragment {

    private static final String ARG_INDEX = "index";
    private NoteDataClass noteDataClass;

    public static NoteTextFragment newInstance(NoteDataClass noteDataClass) {
        NoteTextFragment n = new NoteTextFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_INDEX, noteDataClass);
        n.setArguments(args);
        return n;
    }


    public NoteTextFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            noteDataClass = getArguments().getParcelable(ARG_INDEX);
        }

        View v = inflater.inflate(R.layout.fragment_note_text, null);
        TextView nameTV = (TextView) v.findViewById(R.id.noteName);
        TextView textTV = (TextView) v.findViewById(R.id.noteText);
        nameTV.setText(noteDataClass.getName());
        textTV.setText(noteDataClass.getNoteText());

        return v;
    }
}