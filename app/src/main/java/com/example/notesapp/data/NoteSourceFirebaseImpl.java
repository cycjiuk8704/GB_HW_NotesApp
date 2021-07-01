package com.example.notesapp.data;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NoteSourceFirebaseImpl implements NoteSource {
    private static final String CARDS_COLLECTION = "cards";
    private static final String TAG = "[CardsSourceFirebaseImpl]";
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    private CollectionReference collection = store.collection(CARDS_COLLECTION);
    private List<NoteDataClass> notesData = new ArrayList<NoteDataClass>();

    @SuppressLint("LongLogTag")
    @Override
    public NoteSource init(final NoteSourceResponse noteSourceResponse) {
        collection.orderBy(NoteDataMapping.Fields.DATE, Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            notesData = new ArrayList<NoteDataClass>();
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Map<String, Object> doc = document.getData();
                                String id = document.getId();
                                NoteDataClass noteData = NoteDataMapping.toNoteData(id, doc);
                                notesData.add(noteData);
                            }
                            Log.d(TAG, "success " + notesData.size() + " qnt");
                            noteSourceResponse.initialized(NoteSourceFirebaseImpl.this);
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(e -> Log.d(TAG, "get failed with ", e));
        return this;
    }

    @Override
    public NoteDataClass getNoteData(int position) {
        return notesData.get(position);
    }

    @Override
    public void deleteNoteData(int position) {
        collection.document(notesData.get(position).getId()).delete();
        notesData.remove(position);

    }

    @Override
    public void updateNoteData(int position, NoteDataClass noteData) {
        String id = noteData.getId();
        collection.document(id).set(NoteDataMapping.toDocument(noteData));

    }

    @Override
    public void addNoteData(NoteDataClass noteData) {
        collection.add(NoteDataMapping.toDocument(noteData)).addOnSuccessListener(documentReference -> noteData.setId(documentReference.getId()));

    }

    @Override
    public int size() {
        if (notesData == null) {
            return 0;
        }
        return notesData.size();
    }
}
