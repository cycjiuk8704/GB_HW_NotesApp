package com.example.notesapp.observe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notesapp.data.NoteSourceImpl;

import java.util.ArrayList;
import java.util.List;

public class Publisher<T> {

    private final List<Observer<T>> observers = new ArrayList<>();
    @Nullable
    private NoteSourceImpl value;

    public void subscribe(Observer<T> observer) {
        observers.add(observer);

        if (value != null) {
            observer.updateValue(value);
        }
    }

    public void unsubscribe(Observer<T> observer) {
        observers.remove(observer);
    }

    public void notify(@NonNull NoteSourceImpl value) {
        this.value = value;

        for (Observer<T> observer : observers) {
            observer.updateValue(value);
        }
    }

}