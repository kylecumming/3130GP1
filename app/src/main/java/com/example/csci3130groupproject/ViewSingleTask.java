package com.example.csci3130groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewSingleTask extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_task);

        //Using intent to grab the appropriate data from the list of tasks in ViewTasksActivity.java
        Intent intent = getIntent();
        final String title = intent.getStringExtra("TITLE");
        final String price = intent.getStringExtra("PRICE");
        final String tags = intent.getStringExtra("TAGS");
        final String description = intent.getStringExtra("DESCRIPTION");
        final String author = intent.getStringExtra("AUTHOR");
        final String applicant = getIntent().getStringExtra("username");

        //Setting the data into TextViews on this activity
        TextView taskTitle = (TextView)findViewById(R.id.viewTitle);
        TextView taskPrice = (TextView)findViewById(R.id.viewPrice);
        TextView taskTags = (TextView)findViewById(R.id.viewTags);
        TextView taskDesc = (TextView)findViewById(R.id.viewDesc);
        taskTitle.setText(title);
        taskPrice.setText(price);
        taskTags.setText(tags);
        taskDesc.setText(description);

        //OnClick method for Return button
        Button returnButton = (Button) findViewById(R.id.buttonReturn);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchViewTasksActivity(applicant);
            }
        });

        //OnClick method for Apply button
        Button applyButton = (Button) findViewById(R.id.buttonApplyForTask);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference allApplications = databaseReference.child("Applications");
                allApplications.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean applicationExists = false;
                        for(DataSnapshot storedApplication : snapshot.getChildren()){
                            TaskApplication application = storedApplication.getValue(TaskApplication.class);
                            if(application.getApplicant().equals(applicant) && application.getTaskTitle().equals(title))
                                applicationExists = true;

                        }
                        if(applicationExists){
                            AlertDialog.Builder noSubmission = new AlertDialog.Builder(ViewSingleTask.this);
                            noSubmission.setTitle("Application Unsuccessful")
                                    .setMessage("You have already applied for this task!")
                                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                            noSubmission.show();
                        }
                        else{
                            storeApplicationOnFirebase(title, applicant, author);
                            AlertDialog.Builder applicationSubmitted = new AlertDialog.Builder(ViewSingleTask.this);
                            applicationSubmitted.setTitle("Application Success")
                                    .setMessage("You have successfully applied for this task!")
                                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                            applicationSubmitted.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    private void launchViewTasksActivity(String username){
        Intent intent = new Intent(this, ViewTasksActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    //Creates a new application for a task and stores in Firebase
    private void storeApplicationOnFirebase(String title, String applicant, String author){
        TaskApplication newApp = new TaskApplication(title, applicant, author);
        databaseReference = databaseReference.child("Applications");
        databaseReference.push().setValue(newApp);
    }

}