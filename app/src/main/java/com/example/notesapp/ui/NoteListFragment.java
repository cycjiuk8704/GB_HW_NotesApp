package com.example.notesapp.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.data.NoteDataClass;
import com.example.notesapp.data.NoteSource;
import com.example.notesapp.data.NoteSourceImpl;

import java.util.List;

public class NoteListFragment extends BaseFragment {

    private static final String ARG_INDEX = "index";
    private List<NoteDataClass> noteData;
    private NoteSourceImpl noteSource;
    private int listPosition;

    public static NoteListFragment newInstance(NoteSourceImpl noteData) {
        NoteListFragment n = new NoteListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_INDEX, (Parcelable) noteData);
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
        initPopupMenu(v, listPosition);
        RecyclerView recyclerView = v.findViewById(R.id.recycler_view_lines);
        initRecyclerView(recyclerView, noteSource);
//        TextView nameTV = v.findViewById(R.id.noteName);
//        TextView descriptionTV = v.findViewById(R.id.noteDescription);
//        TextView dateTV = v.findViewById(R.id.noteDate);
//        nameTV.setText(noteData.getName());
//        descriptionTV.setText(noteData.getDescription());
//        dateTV.setText(noteData.getDateOfCreation());


        return v;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initPopupMenu(View view, int position) {
        View noteContainer = view.findViewById(R.id.noteEntryContainer);
        noteContainer.setOnClickListener(v -> requireNavigator().showNoteDetails(noteSource.getNoteData(listPosition)));
        noteContainer.setOnLongClickListener(v -> {
            initPopUpMenu(v, position);
            return true;
        });
    }

    private void initPopUpMenu(View v, int position) {
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
                requireNavigator().showNoteDetails(noteSource.getNoteData(position));
            }
            return true;
        });
        popupMenu.show();
    }

    private void initRecyclerView(RecyclerView recyclerView, NoteSource data) {

        // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);

        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Установим адаптер
        final NoteAdapter adapter = new NoteAdapter(data);
        recyclerView.setAdapter(adapter);

        // Установим слушателя
        adapter.SetOnLongItemClickListener(new NoteAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(View view, int position) {
                initPopUpMenu(view, position);
            }

            @Override
            public void onItemClick(View view, int position) {
                listPosition = position;
                requireNavigator().showNoteDetails(noteSource.getNoteData(position));
            }
        });

        adapter.SetOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                listPosition = position;
                requireNavigator().showNoteDetails(noteSource.getNoteData(position));
            }
        });
    }
}