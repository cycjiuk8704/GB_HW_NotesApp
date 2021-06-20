package com.example.notesapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null) {
            noteData = getArguments().getParcelable(ARG_INDEX);
        }

        View v = inflater.inflate(R.layout.fragment_note_list, null);
        initPopupMenu(v);
        TextView nameTV = v.findViewById(R.id.noteName);
        TextView descriptionTV = v.findViewById(R.id.noteDescription);
        TextView dateTV = v.findViewById(R.id.noteDate);
        nameTV.setText(noteData.getName());
        descriptionTV.setText(noteData.getDescription());
        dateTV.setText(noteData.getDateOfCreation());


        return v;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initPopupMenu(View view) {
        View noteContainer = view.findViewById(R.id.noteEntryContainer);
        noteContainer.setOnClickListener(v -> requireNavigator().showNoteDetails(noteData));
        noteContainer.setOnLongClickListener(v -> {
            initPopUpMenu(v);
            return true;
        });
    }

    private void initPopUpMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), v);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.add_popup) {
                Toast.makeText(getContext(), "Add new note", Toast.LENGTH_SHORT).show();
            }
            if (id == R.id.delete_popup) {
                Toast.makeText(getContext(), "Delete note", Toast.LENGTH_SHORT).show();
            }

            if (id == R.id.open_popup) {
                requireNavigator().showNoteDetails(noteData);
            }
            return true;
        });
        popupMenu.show();
    }
}