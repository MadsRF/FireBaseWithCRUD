package com.example.firebasejon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.firebasejon.storage.FirebaseRepo;

public class DetailedActivity extends AppCompatActivity {

    EditText editTextHead;
    EditText editTextBody;
    private int row = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        editTextHead = findViewById(R.id.headEditText);
        editTextBody = findViewById(R.id.bodyEditText);

        row = getIntent().getIntExtra(MainActivity.INDEX_KEY,0);
        Log.i("info", Integer.toString(row));

        if(editTextHead != null) {
            editTextHead.setText(FirebaseRepo.getNote(row).getHead());
        }
        if (editTextBody != null){
            editTextBody.setText(FirebaseRepo.getNote(row).getBody());
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseRepo.editNote(row, editTextHead.getText().toString(), editTextBody.getText().toString());
    }
}
