package com.example.firebasejon.storage;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasejon.MainActivity;
import com.example.firebasejon.note.Note;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// holds list of array of the note object
public class FirebaseRepo {
    public static List<Note> notes = new ArrayList<>();

    // database firebase
    public final static String notesPath = "notes";
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static RecyclerView.Adapter adapter;


    public static Note getNote(int index){
        if (index >= notes.size()) {
            return new Note("", "Example Note", "this is example text");
        }else {
            return notes.get(index);
        }
    }


    // make sure the listener starts as soon as possible
    static {
        startNoteListener();
    }

    // Goes through database collection and adds them to our list of notes aka the note object.
    private static void startNoteListener() {
        FirebaseRepo.db.collection(notesPath).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot values, @Nullable FirebaseFirestoreException e) {
                notes.clear();
                for (DocumentSnapshot snap: values.getDocuments()) {
                    Log.i("all", "read from FB " + snap.getId() + " " + snap.get("body").toString());
                    notes.add(new Note(snap.getId(), snap.get("head").toString(), snap.get("body").toString()));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public static void editNote(int index, String head, String body) {
        String id = notes.get(index).getId();
        DocumentReference docRef = db.collection(notesPath).document(id);
        Map<String,String> map = new HashMap<>();
        map.put("head", head);
        map.put("body", body);
        docRef.set(map);
    }

    public static void deleteNote(int index) {
        String key = notes.get(index).getId();
        DocumentReference docRef = db.collection(notesPath).document(key);
        docRef.delete();

    }

}