package com.example.notesapp.data;

public interface NoteSource {
    NoteSource init(NoteSourceResponse noteSourceResponse);

    NoteDataClass getNoteData(int position);
    void deleteNoteData(int position);

    void updateNoteData(NoteDataClass noteData);

    void addNoteData(NoteDataClass noteData);
    int size();
}
