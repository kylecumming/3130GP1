package com.example.csci3130groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ViewSingleTask extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference singleTask = database.getReference("Tasks");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_task);

        //using intent to grab the appropriate data from the list of tasks in ViewTasksActivity.java
        Intent intent = getIntent();
        final String title = intent.getStringExtra("TITLE");
        final String price = intent.getStringExtra("PRICE");
        final String description = intent.getStringExtra("DESCRIPTION");

        singleTask.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int x = 0;
                for(DataSnapshot storedTask : snapshot.getChildren()){
                    if(x < 1) {
                        Task task = storedTask.getValue(Task.class); //A single task snapshot
                        TextView taskTitle = (TextView)findViewById(R.id.viewTitle);
                        TextView taskPrice = (TextView)findViewById(R.id.viewPrice);
                        TextView taskDesc = (TextView)findViewById(R.id.viewDesc);
                        taskTitle.setText(title);
                        taskPrice.setText(price);
                        taskDesc.setText(description);
                        x++;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}