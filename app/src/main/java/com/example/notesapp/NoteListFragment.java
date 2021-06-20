package com.example.notesapp;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class NoteListFragment extends BaseFragment {

    private static final String ARG_INDEX = "index";
    private NoteDataClass noteData;

    public static NoteListFragment newInstance(NoteDataClass noteData) {
        NoteListFragment n = new NoteListFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_INDEX, noteData);
        n.setArguments(args);
        return n;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null) {
            noteData = (NoteDataClass) getArguments().getParcelable(ARG_INDEX);
        }

        View v = inflater.inflate(R.layout.fragment_note_list, null);
        TextView nameTV = (TextView) v.findViewById(R.id.noteName);
        TextView descriptionTV = (TextView) v.findViewById(R.id.noteDescription);
        TextView dateTV = (TextView) v.findViewById(R.id.noteDate);
        nameTV.setText(noteData.getName());
        descriptionTV.setText(noteData.getDescription());
        dateTV.setText(noteData.getDateOfCreation());

        LinearLayout linearLayout1 = (LinearLayout) v.findViewById(R.id.noteField);
        linearLayout1.setOnClickListener(view -> requireNavigator().showNoteDetails(noteData));

        return v;
    }
}