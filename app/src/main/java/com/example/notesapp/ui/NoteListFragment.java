package com.example.notesapp.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.data.NoteDataClass;
import com.example.notesapp.data.NoteSource;
import com.example.notesapp.data.NoteSourceImpl;

import java.util.List;

public class NoteListFragment extends BaseFragment {

    private static final String LIST_STATE = "state";
    private List<NoteDataClass> noteData;
    private NoteSourceImpl noteSource;

    public static NoteListFragment newInstance(NoteSourceImpl noteData) {
        NoteListFragment n = new NoteListFragment();
        Bundle args = new Bundle();
        args.putParcelable(LIST_STATE, (Parcelable) noteData);
        n.setArguments(args);
        return n;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState, Toolbar toolbar) {

        if (getArguments() != null) {
            noteSource = getArguments().getParcelable(LIST_STATE);
            noteData = noteSource.getNoteSource();
        }

        noteSource = new NoteSourceImpl(noteData);
        View v = inflater.inflate(R.layout.fragment_note_list, null);
        RecyclerView recyclerView = v.findViewById(R.id.recycler_view_lines);
        initRecyclerView(recyclerView, noteSource);
        setupToolbar(toolbar);
        return v;
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

            if (id == R.id.edit_popup) {
                requireNavigator().showEditNoteDetails(noteSource.getNoteData(position));
            }
            return true;
        });
        popupMenu.show();
    }

    private void initRecyclerView(RecyclerView recyclerView, NoteSource data) {

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        final NoteAdapter adapter = new NoteAdapter(data);
        recyclerView.setAdapter(adapter);

        adapter.SetOnLongItemClickListener(new NoteAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(View view, int position) {
                initPopUpMenu(view, position);
            }
        });

        adapter.SetOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                requireNavigator().showNoteDetails(noteSource.getNoteData(position));
            }
        });
    }

    protected void setupToolbar(Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.main);
        final MenuItem search = toolbar.getMenu().findItem(R.id.action_search);
        SearchView searchText = (SearchView) search.getActionView();

        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_settings) {
                Toast.makeText(getContext(), id + "there might be settings fragment", Toast.LENGTH_SHORT).show();
            }
            if (id == R.id.action_add) {
                Toast.makeText(getContext(), id + "there might be adding note fragment", Toast.LENGTH_SHORT).show();
            }

            return true;
        });
    }
}