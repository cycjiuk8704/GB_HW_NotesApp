package com.example.notesapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class NoteSourceImpl implements NoteSource, Parcelable {

    private final List<NoteDataClass> noteSource;

    public NoteSourceImpl(List<NoteDataClass> noteSource) {
        this.noteSource = noteSource;
    }

    @Override
    public NoteDataClass getNoteData(int position) {
        return noteSource.get(position);
    }

    @Override
    public int size() {
        return noteSource.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(noteSource);
    }
}
