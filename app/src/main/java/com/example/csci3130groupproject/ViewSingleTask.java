package com.example.csci3130groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

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
                allApplications.addValueEventListener(new ValueEventListener() {
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
        RatingBar reviewRatingBar = (RatingBar) findViewById(R.id.averageRatingBar2);
        getAverage(author, reviewRatingBar);
        final LinearLayout reviewsLayout = findViewById(R.id.reviewLinearLayout2);
        plotReviews(author, reviewsLayout);

        Button showReviews = findViewById(R.id.showReviewsButton);
        showReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Reviews for " + author)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
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
    private void plotReviews(final String author, final LinearLayout reviewLinearLayout){
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> users = snapshot.getChildren().iterator();
                while(users.hasNext()){
                    DataSnapshot user = users.next();
                    User currUser = user.getValue(User.class);
                    if(currUser.getUsername().equals(author)){
                        ArrayList<ArrayList<String>> reviewList = currUser.getReviews();
                        if(reviewList.size()>1){
                            for(int i=1; i<reviewList.size(); i++){
                                View view_review_layout = getLayoutInflater().inflate(R.layout.view_review_layout, null);
                                RatingBar rb = view_review_layout.findViewById(R.id.reviewRating);
                                TextView taskTitle = view_review_layout.findViewById(R.id.reviewTitle);
                                TextView taskComments= view_review_layout.findViewById(R.id.reviewDescription);
                                rb.setRating(Float.parseFloat(reviewList.get(i).get(1)));
                                taskTitle.setText(reviewList.get(i).get(0));
                                taskComments.setText(reviewList.get(i).get(2));
                                reviewLinearLayout.addView(view_review_layout);

                            }
                        }
                        else{
                            break;
                        }
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void getAverage(final String author, final RatingBar rb){
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> users = snapshot.getChildren().iterator();
                while(users.hasNext()){
                    DataSnapshot user = users.next();
                    User currUser = user.getValue(User.class);
                    if(currUser.getUsername().equals(author)){
                        rb.setRating((float)currUser.averageRating());
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}