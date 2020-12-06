package com.example.csci3130groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewSingleApplicationActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    DatabaseReference allTasks = database.getReference("Tasks");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_application);

        //Using intent to grab the appropriate data from the list of applications in ViewApplicationsActivity.java
        Intent intent = getIntent();
        final String title = intent.getStringExtra("TITLE");
        final String applicant = intent.getStringExtra("APPLICANT");
        final String author = intent.getStringExtra("AUTHOR");

        //Setting the data into TextViews on this activity
        TextView taskTitle = (TextView)findViewById(R.id.textview_applicationTitle);
        TextView applicantTextview = (TextView)findViewById(R.id.textview_applicant);
        taskTitle.setText("Task: " + title);
        applicantTextview.setText("Applicant: " + applicant);

        //OnClick method for Return button
        Button returnButton = (Button) findViewById(R.id.button_returnApplication);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchViewApplicationsActivity(author);
            }
        });

        //OnClick method for Accept button
        Button acceptButton = (Button) findViewById(R.id.button_acceptApplication);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AcceptApplication(title, applicant, author);
                AlertDialog.Builder applicationAccepted = new AlertDialog.Builder(ViewSingleApplicationActivity.this);
                applicationAccepted.setTitle("Application Approval")
                        .setMessage("You have successfully approved this application!")
                        .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                applicationAccepted.show();
            }
        });

    }

    private void launchViewApplicationsActivity(String username){
        Intent intent = new Intent(this, ViewApplicationsActivity.class);
        intent.putExtra("username", username);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void AcceptApplication(final String title, final String applicant, final String author){
        databaseReference = databaseReference.child("Applications");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot application : snapshot.getChildren()){
                    TaskApplication singleApplication = application.getValue(TaskApplication.class);
                    if(singleApplication.getTaskTitle().equals(title) &&
                            singleApplication.getApplicant().equals(applicant) &&
                            singleApplication.getAuthor().equals(author)){
                        application.getRef().child("accepted").setValue(true);
                    }
                    else if(singleApplication.getTaskTitle().equals(title) &&
                            singleApplication.getAuthor().equals(author)){
                        application.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Set 'assigned' value to True so this Task will no longer show up in ViewTasksActivity.java
        allTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot storedTask : snapshot.getChildren()){
                    Task task = storedTask.getValue(Task.class);
                    if(task.getAuthor().equals(author) && task.getTitle().equals(title)){
                        storedTask.getRef().child("assigned").setValue(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}