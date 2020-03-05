package com.example.firebasejon;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.firebasejon.adapter.MyRecycleViewAdapter;
import com.example.firebasejon.note.Note;
import com.example.firebasejon.storage.FirebaseRepo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.HashMap;
import java.util.Map;

import static com.example.firebasejon.storage.FirebaseRepo.notes;
import static com.example.firebasejon.storage.FirebaseRepo.notesPath;

public class MainActivity extends AppCompatActivity {

    // UI View
    private MyRecycleViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private EditText editText;

    public static final String INDEX_KEY = "INDEX_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recycler with adapter
        recyclerView = findViewById(R.id.recyclerViewer);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyRecycleViewAdapter();
        recyclerView.setAdapter(adapter);
        FirebaseRepo.adapter = adapter;

        // add headline UI
        editText = findViewById(R.id.headlineEditText);

    }

    public void addNewNote(View view) {

        // access the database with DocumentReference
        DocumentReference docRef = FirebaseRepo.db.collection(notesPath).document();
        // database uses a key and value so we use a HashMap for that
        final Map<String, String> map = new HashMap<>();

        // placeholder for headline input
        String noteName = editText.getText().toString();

        map.put("head", noteName);
        map.put("body", "This is a test");

        // clears input after adding
        editText.getText().clear();

        // Lastly we set the Map as the data to send.
        docRef.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i("all", "added successfully " + map);
            }
        });
    }

}