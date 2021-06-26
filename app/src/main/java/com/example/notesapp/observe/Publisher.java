package com.example.notesapp.observe;

import com.example.notesapp.data.NoteDataClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Publisher {
    private List<Observer> observers;
    private Iterator<Observer> iterator;

    public Publisher() {
        observers = new ArrayList<>();
        iterator = observers.listIterator();
    }

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notifySingle(NoteDataClass noteDataClass) {
        for (Iterator<Observer> i = observers.iterator(); i.hasNext(); ) {
            i.next().updateData(noteDataClass);
            i.remove();
//        while (iterator.hasNext()){
//            iterator.next();
//            observers.remove(this);
//            iterator.remove();
//        for (Observer observer : observers) {
//            observer.updateData(noteDataClass);
//            unsubscribe(observer);
        }
    }

}