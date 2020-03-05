package com.example.firebasejon.viewHolder;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasejon.DetailedActivity;
import com.example.firebasejon.MainActivity;
import com.example.firebasejon.R;
import com.example.firebasejon.storage.FirebaseRepo;

// is the functionality of the RecyclerView
public class ViewHolder extends RecyclerView.ViewHolder {

    private int rowNumber = 0;

    private TextView textView;
    Intent intent;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.textViewInList);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("info", "you clicked " + rowNumber);
                intent = new Intent(view.getContext(), DetailedActivity.class);
                intent.putExtra(MainActivity.INDEX_KEY, rowNumber);
                view.getContext().startActivity(intent);
            }
        });

        handleLongPress();

    }

    private void handleLongPress() {
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("info", "you deleted " + rowNumber);
                // Toast.makeText(v.getContext(), rowNumber, Toast.LENGTH_LONG).show();
                FirebaseRepo.deleteNote(rowNumber);
                return true;
            }
        });
    }

    public void setPosition(int position) {
        rowNumber = position;
        textView.setText(FirebaseRepo.notes.get(position).getHead());
        Log.i("info", "note headline" + FirebaseRepo.notes.get(position).getHead());
    }










}