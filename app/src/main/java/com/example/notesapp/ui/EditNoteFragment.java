package com.example.notesapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.notesapp.R;
import com.example.notesapp.data.NoteDataClass;

public class EditNoteFragment extends BaseFragment {

    private static final String NOTE_STATE = "state";
    private NoteDataClass noteDataClass;

    public static EditNoteFragment newInstance(NoteDataClass noteDataClass) {
        EditNoteFragment noteTextFragment = new EditNoteFragment();

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

        View v = inflater.inflate(R.layout.fragment_edit_note, null);
        TextView nameTV = v.findViewById(R.id.noteEditName);
        TextView textTV = v.findViewById(R.id.noteEditText);
        TextView descriptionTV = v.findViewById(R.id.noteEditDescription);
        TextView dateTV = v.findViewById(R.id.noteEditDate);
        nameTV.setText(noteDataClass.getName());
        textTV.setText(noteDataClass.getNoteText());
        descriptionTV.setText(noteDataClass.getDescription());
        dateTV.setText(noteDataClass.getDateOfCreation());

        return v;
    }
}