package com.example.csci3130groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    DatabaseReference allTasks = database.getReference("Tasks");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_task);

        final LinearLayout displayTasks = (LinearLayout) findViewById(R.id.linearlayout_tasks);

        //Displays all tasks currently stored in Firebase
        allTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int x = 0;
                for(DataSnapshot storedTask : snapshot.getChildren()){
                    if(x < 1) {
                        Task task = storedTask.getValue(Task.class); //A single task snapshot
                        TextView taskTitle = (TextView)findViewById(R.id.viewTitle);
                        TextView taskPrice = (TextView)findViewById(R.id.viewPrice);
                        TextView taskDesc = (TextView)findViewById(R.id.viewDesc);
                        Log.d("testytesting",task.getTitle());
                        String title = task.getTitle();
                        String price = task.getPrice();
                        String desc = task.getDescription();
                        //  taskTitle.setText("title");// every time I try and set my textview it instantly crashes the app
                        /*taskTitle.setText(Html.fromHtml(task.getPrice()));
                        taskDesc.setText(Html.fromHtml(task.getDescription()));
                        */
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