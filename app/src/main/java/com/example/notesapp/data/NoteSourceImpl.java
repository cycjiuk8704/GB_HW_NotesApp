package com.example.notesapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class NoteSourceImpl implements NoteSource, Parcelable {

    private final List<NoteDataClass> noteSource;

    public NoteSourceImpl(List<NoteDataClass> noteSource) {
        this.noteSource = noteSource;
    }

    protected NoteSourceImpl(Parcel in) {
        noteSource = in.createTypedArrayList(NoteDataClass.CREATOR);
    }

    public static final Creator<NoteSourceImpl> CREATOR = new Creator<NoteSourceImpl>() {
        @Override
        public NoteSourceImpl createFromParcel(Parcel in) {
            return new NoteSourceImpl(in);
        }

        @Override
        public NoteSourceImpl[] newArray(int size) {
            return new NoteSourceImpl[size];
        }
    };

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

    public List<NoteDataClass> getNoteSource() {
        return noteSource;
    }
}
