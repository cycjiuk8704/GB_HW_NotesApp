package com.example.notesapp.data;

public interface NoteSource {
    NoteDataClass getNoteData(int position);

    int size();
}
