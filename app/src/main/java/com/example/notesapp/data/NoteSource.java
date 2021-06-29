package com.example.notesapp.data;

public interface NoteSource {
    NoteDataClass getNoteData(int position);
    void deleteNoteData(int position);
    void updateNoteData(int position, NoteDataClass noteData);
    void addNoteData(NoteDataClass noteData);
    int size();
}
