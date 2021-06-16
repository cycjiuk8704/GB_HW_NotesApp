package com.example.notesapp;

import androidx.annotation.NonNull;

public interface INavigator {

    void showNotes(@NonNull NoteDataClass note);

    void showNoteDetails(@NonNull NoteDataClass note);

}
