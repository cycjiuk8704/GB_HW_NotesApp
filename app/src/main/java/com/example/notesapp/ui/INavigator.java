package com.example.notesapp.ui;

import androidx.annotation.NonNull;

import com.example.notesapp.data.NoteDataClass;

public interface INavigator {

    void showNotes();

    void showNoteDetails(@NonNull NoteDataClass note);

    void showEditNoteDetails(@NonNull NoteDataClass noteDataClass);

    void showAddNote(NoteDataClass noteDataClass);

}
