package com.example.notesapp.ui;

import androidx.annotation.NonNull;

import com.example.notesapp.data.NoteSourceImpl;

public interface INavigator {

    void showNotes(@NonNull NoteSourceImpl note);

    void showNoteDetails(@NonNull NoteSourceImpl note, int position);

    void showEditNoteDetails(@NonNull NoteSourceImpl note, int position);

    void showAddNote();

}
