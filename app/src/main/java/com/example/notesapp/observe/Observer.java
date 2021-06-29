package com.example.notesapp.observe;

import androidx.annotation.NonNull;

public interface Observer<T> {

    void updateValue(@NonNull T value);

}