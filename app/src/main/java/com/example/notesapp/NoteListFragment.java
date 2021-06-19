package com.example.notesapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        LinearLayout linearLayout1 = view.findViewById(R.id.noteField);
        linearLayout1.setOnTouchListener(new View.OnTouchListener() {
            long startTime;

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startTime = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        if( ((System.currentTimeMillis() - startTime) / 1000) >= 1 )
                        {
                            Activity activity = requireActivity();
                            PopupMenu popupMenu = new PopupMenu(activity, v);
                            activity.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                            popupMenu.setOnMenuItemClickListener(item -> {
                                int id = item.getItemId();
                                switch (id) {
                                    case R.id.add_popup:
                                        Toast.makeText(getContext(), "Add new note", Toast.LENGTH_SHORT).show();
                                        return true;
                                    case R.id.delete_popup:
                                        Toast.makeText(getContext(), "Delete note", Toast.LENGTH_SHORT).show();
                                        return true;
                                    case R.id.open_popup:
                                        requireNavigator().showNoteDetails(noteData);
                                        return true;
                                }
                                return true;
                            });
                            popupMenu.show();
                        } else {
                            requireNavigator().showNoteDetails(noteData);
                        }
                        break;
                }
                return true;
            }
        });











    }
}