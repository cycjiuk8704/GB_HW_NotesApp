package com.example.notesapp.observe;

import androidx.annotation.NonNull;

import com.example.notesapp.data.NoteSourceImpl;

public interface Observer<T> {

    void updateValue(@NonNull NoteSourceImpl value);

}