package com.example.notesapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.notesapp.MainActivity;
import com.example.notesapp.R;
import com.example.notesapp.data.NoteDataClass;
import com.example.notesapp.data.NoteSource;
import com.example.notesapp.observe.Observer;
import com.example.notesapp.observe.Publisher;

public class NoteTextFragment extends BaseFragment {

    private static final String NOTE_STATE = "state";
//    private static final String NOTE_POSITION = "position";
    private NoteDataClass noteDataClass;
    //    private int position;
    private TextView nameTV;
    private TextView textTV;
    private TextView descriptionTV;
    private TextView dateTV;
    private Publisher<NoteSource> publisher;
    private final Observer<NoteSource> observer = value -> {

//        assert getArguments() != null;
//        getArguments().putParcelable(NOTE_STATE, new NoteDataClass(nameTV.getText().toString(), descriptionTV.getText().toString(),
//                dateTV.getText().toString(), textTV.getText().toString()));
    };

    public static NoteTextFragment newInstance(NoteDataClass noteDataClass) {
        NoteTextFragment noteTextFragment = new NoteTextFragment();
        Bundle args = new Bundle();
        args.putParcelable(NOTE_STATE, noteDataClass);
//        args.putInt(NOTE_POSITION, position);
        noteTextFragment.setArguments(args);
        return noteTextFragment;
    }

    @Override
    public void onDestroyView() {
        publisher.unsubscribe(observer);
        super.onDestroyView();
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
            noteDataClass = getArguments().getParcelable(NOTE_STATE);
        }
        publisher.subscribe(observer);
        View v = inflater.inflate(R.layout.fragment_note_text, null);
        nameTV = v.findViewById(R.id.noteDetailName);
        textTV = v.findViewById(R.id.noteText);
        descriptionTV = v.findViewById(R.id.noteDetailDescription);
        dateTV = v.findViewById(R.id.noteDetailDate);
        initTextView(noteDataClass);

        setupToolbar(toolbar);
        return v;
    }

    private void initTextView(NoteDataClass noteDataClass) {
        nameTV.setText(noteDataClass.getName());
        textTV.setText(noteDataClass.getNoteText());
        descriptionTV.setText(noteDataClass.getDescription());
        dateTV.setText(noteDataClass.getDateOfCreation());
    }

    protected void setupToolbar(Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.main_text_frag);
        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_settings) {
                Toast.makeText(getContext(), id + "there might be settings fragment", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.action_edit) {
                requireNavigator().showEditNoteDetails(noteDataClass);


            }
            return true;
        });
    }
}