package com.example.notesapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteDataClass implements Parcelable {
    private String name;
    private final String description;
    private final String dateOfCreation;
    private final String noteText;

    public NoteDataClass(String name, String description, String dateOfCreation, String noteText) {
        this.name = name;
        this.description = description;
        this.dateOfCreation = dateOfCreation;
        this.noteText = noteText;
    }

    protected NoteDataClass(Parcel in) {
        name = in.readString();
        description = in.readString();
        dateOfCreation = in.readString();
        noteText = in.readString();
    }

    public static final Creator<NoteDataClass> CREATOR = new Creator<NoteDataClass>() {
        @Override
        public NoteDataClass createFromParcel(Parcel in) {
            return new NoteDataClass(in);
        }

        @Override
        public NoteDataClass[] newArray(int size) {
            return new NoteDataClass[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(dateOfCreation);
        dest.writeString(noteText);
    }
}
