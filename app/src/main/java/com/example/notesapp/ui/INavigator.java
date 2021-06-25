package com.example.notesapp.ui;

import androidx.annotation.NonNull;

import com.example.notesapp.data.NoteDataClass;
import com.example.notesapp.data.NoteSourceImpl;

public interface INavigator {

    void showNotes(@NonNull NoteSourceImpl note);

    void showNoteDetails(@NonNull NoteDataClass note);

    void showEditNoteDetails(@NonNull NoteDataClass note);

    void showAddNote();

}
