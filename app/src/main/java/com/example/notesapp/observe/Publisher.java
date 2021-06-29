package com.example.notesapp.observe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Publisher<T> {

    private final List<Observer<T>> observers = new ArrayList<>();
    @Nullable
    private T value;

    public void subscribe(Observer<T> observer) {
        observers.add(observer);

        if (value != null) {
            observer.updateValue(value);
        }
    }

    public void unsubscribe(Observer<T> observer) {
        observers.remove(observer);
    }

    public void notify(@NonNull T value) {
        this.value = value;

        for (Observer<T> observer : observers) {
            observer.updateValue(value);
        }
    }

}