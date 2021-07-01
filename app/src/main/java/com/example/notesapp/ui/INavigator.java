package com.example.notesapp.ui;

import androidx.annotation.NonNull;

import com.example.notesapp.data.NoteDataClass;

public interface INavigator {

    void showNotes();

    void showNoteDetails(@NonNull NoteDataClass note, int position);

    void showEditNoteDetails(@NonNull NoteDataClass noteDataClass, int position);

    void showAddNote(NoteDataClass noteDataClass, int position);

}
