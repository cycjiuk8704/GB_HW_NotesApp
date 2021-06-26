package com.example.notesapp.observe;

import com.example.notesapp.data.NoteDataClass;

public interface Observer {
    void updateData(NoteDataClass noteDataClass);
}