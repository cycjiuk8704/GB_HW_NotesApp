package com.example.notesapp;

import java.util.ArrayList;

public class NoteDataClass {
    private String name;
    private String description;
    private String dateOfCreation;
    private String noteText;

    public NoteDataClass(String name, String description, String dateOfCreation, String noteText) {
        this.name = name;
        this.description = description;
        this.dateOfCreation = dateOfCreation;
        this.noteText = noteText;
    }

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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }
}
