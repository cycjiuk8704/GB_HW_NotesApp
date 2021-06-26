package com.example.notesapp.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.example.notesapp.MainActivity;
import com.example.notesapp.R;
import com.example.notesapp.data.NoteDataClass;
import com.example.notesapp.data.NoteSourceImpl;
import com.example.notesapp.observe.Publisher;

import java.util.Calendar;

public class EditNoteFragment extends BaseFragment {

    private static final String NOTE_STATE = "state";
    private static final String NOTE_POSITION = "position";
    private NoteDataClass noteDataClass;
    private NoteSourceImpl noteSource;
    private int position;
    private TextView nameTV;
    private TextView textTV;
    private TextView descriptionTV;
    private TextView dateTV;
    private final Calendar calendar = Calendar.getInstance();
    private Publisher publisher;

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

    DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) -> {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setInitialDate();
    };

    public static EditNoteFragment newInstance(NoteSourceImpl noteData, int position) {
        EditNoteFragment noteEditFragment = new EditNoteFragment();

        Bundle args = new Bundle();
        args.putParcelable(NOTE_STATE, noteData);
        args.putInt(NOTE_POSITION, position);
        noteEditFragment.setArguments(args);
        return noteEditFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState, @NonNull Toolbar toolbar) {
        if (getArguments() != null) {
            noteSource = getArguments().getParcelable(NOTE_STATE);
            position = getArguments().getInt(NOTE_POSITION);
            noteDataClass = noteSource.getNoteData(position);
        }
        return initView(inflater, toolbar);
    }

    @NonNull
    private View initView(@NonNull LayoutInflater inflater, Toolbar toolbar) {
        View v = inflater.inflate(R.layout.fragment_edit_note, null);
        nameTV = v.findViewById(R.id.noteEditName);
        textTV = v.findViewById(R.id.noteEditText);
        descriptionTV = v.findViewById(R.id.noteEditDescription);
        dateTV = v.findViewById(R.id.noteEditDate);
        nameTV.setText(noteDataClass.getName());
        textTV.setText(noteDataClass.getNoteText());
        descriptionTV.setText(noteDataClass.getDescription());
        if (noteDataClass.getDateOfCreation().equals("")) {
            setInitialDate();
        } else {
            dateTV.setText(noteDataClass.getDateOfCreation());
        }
        setupToolbar(toolbar);

        dateTV.setOnClickListener(this::setDate);

        Button buttonSave = v.findViewById(R.id.buttonSaveEdit);
        buttonSave.setOnClickListener(v1 -> {
            noteSource.updateNoteData(position, new NoteDataClass(nameTV.getText().toString(), descriptionTV.getText().toString(), dateTV.getText().toString(), textTV.getText().toString()));
            publisher.notify(noteSource);
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return v;


    }

    protected void setupToolbar(Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.main_edit_frag);
    }

    private void setInitialDate() {

        dateTV.setText(DateUtils.formatDateTime(getContext(),
                calendar.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    public void setDate(View v) {
        new DatePickerDialog(getContext(), d,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    @NonNull
    public Dialog onCreateExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Выход из режима редактирования")
                .setMessage("Действительно хотите выйти? Внесенные изменения не сохранятся")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onDetach();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }
}