package com.example.notesapp.data;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class NoteDataMapping {
    public static class Fields {
        public final static String NAME = "name";
        public final static String DATE = "date";
        public final static String NOTE_TEXT = "noteText";
        public final static String DESCRIPTION = "description";
    }

    public static NoteDataClass toNoteData(String id, Map<String, Object> doc) {
        Timestamp timeStamp = (Timestamp) doc.get(Fields.DATE);
        NoteDataClass answer = new NoteDataClass((String) doc.get(Fields.NAME),
                (String) doc.get(Fields.DESCRIPTION),
                timeStamp.toDate().toString(),
                (String) doc.get(Fields.NOTE_TEXT));
        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> toDocument(NoteDataClass noteData) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.NOTE_TEXT, noteData.getNoteText());
        answer.put(Fields.DESCRIPTION, noteData.getDescription());
        answer.put(Fields.NAME, noteData.getName());
        answer.put(Fields.DATE, noteData.getDateOfCreation());
        return answer;
    }
}
