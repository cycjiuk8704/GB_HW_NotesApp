package com.example.notesapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.notesapp.R;
import com.example.notesapp.data.NoteDataClass;

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
                             Bundle savedInstanceState, Toolbar toolbar) {

        if (getArguments() != null) {
            noteDataClass = getArguments().getParcelable(NOTE_STATE);
        }

        View v = inflater.inflate(R.layout.fragment_note_text, null);
        TextView nameTV = v.findViewById(R.id.noteDetailName);
        TextView textTV = v.findViewById(R.id.noteText);
        TextView descriptionTV = v.findViewById(R.id.noteDetailDescription);
        TextView dateTV = v.findViewById(R.id.noteDetailDate);
        nameTV.setText(noteDataClass.getName());
        textTV.setText(noteDataClass.getNoteText());
        descriptionTV.setText(noteDataClass.getDescription());
        dateTV.setText(noteDataClass.getDateOfCreation());
        setupToolbar(toolbar);
        return v;
    }

    protected void setupToolbar(Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.main_text_frag);
        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_settings) {
                Toast.makeText(getContext(), id + "there might be settings fragment", Toast.LENGTH_SHORT).show();
            }

            if (id == R.id.action_edit) {
                Toast.makeText(getContext(), id + "there might be edit fragment", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
    }
}