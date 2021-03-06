package com.example.notesapp.ui;

import android.content.Context;
import android.os.Bundle;
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

import com.example.notesapp.MainActivity;
import com.example.notesapp.R;
import com.example.notesapp.data.NoteDataClass;
import com.example.notesapp.data.NoteSource;
import com.example.notesapp.data.NoteSourceImpl;
import com.example.notesapp.observe.Observer;
import com.example.notesapp.observe.Publisher;

import java.util.List;

public class NoteListFragment extends BaseFragment {

    private static final String LIST_STATE = "state";
    private List<NoteDataClass> noteData;
    private NoteSourceImpl noteSource;
    private int menuPosition;
    private NoteAdapter adapter;
    private Publisher<NoteSourceImpl> publisher;
    private final Observer<NoteSourceImpl> observer = new Observer<NoteSourceImpl>() {
        @Override
        public void updateValue(@NonNull NoteSourceImpl value) {
            assert getArguments() != null;
            getArguments().putParcelable(LIST_STATE, value);
            adapter.notifyItemChanged(menuPosition);
        }
    };

    public static NoteListFragment newInstance(NoteSourceImpl noteData) {
        NoteListFragment n = new NoteListFragment();
        Bundle args = new Bundle();
        args.putParcelable(LIST_STATE, noteData);
        n.setArguments(args);
        return n;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState, @NonNull Toolbar toolbar) {

        if (getArguments() != null) {
            noteSource = getArguments().getParcelable(LIST_STATE);
            noteData = noteSource.getNoteSource();
        }
        publisher.subscribe(observer);
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
                addNewNote();
            } else if (id == R.id.delete_popup) {
                noteSource.deleteNoteData(position);
                adapter.notifyItemRemoved(position);
            } else if (id == R.id.open_popup) {
                requireNavigator().showNoteDetails(noteSource, position);
            } else if (id == R.id.edit_popup) {
                requireNavigator().showEditNoteDetails(noteSource, position);
            }
            return true;
        });
        popupMenu.show();
    }

    private void addNewNote() {
        requireNavigator().showAddNote();
        menuPosition = noteSource.size();
    }

    private void initRecyclerView(RecyclerView recyclerView, NoteSource data) {

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NoteAdapter(data);
        recyclerView.setAdapter(adapter);

        adapter.SetOnLongItemClickListener((view, position) -> {
            initPopUpMenu(view, position);
            menuPosition = position;
        });

        adapter.SetOnItemClickListener((view, position) -> {
            requireNavigator().showNoteDetails(noteSource, position);
            menuPosition = position;
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
            } else if (id == R.id.action_add) {
                addNewNote();
            }

            return true;
        });
    }
}