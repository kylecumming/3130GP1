package com.example.csci3130groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewSingleTaskInProgress extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference allTasks = database.getReference("Tasks");
    DatabaseReference allUsers = database.getReference("Users");

    //this might frig it up spencer nov 28th
    DatabaseReference databaseReference = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_task_in_progress);

        //Using intent to grab the appropriate data from the list of tasks in ViewTasksInProgressActivity.java
        Intent intent = getIntent();
        final String title = intent.getStringExtra("TITLE");
        final String price = intent.getStringExtra("PRICE");
        final String tags = intent.getStringExtra("TAGS");
        final String description = intent.getStringExtra("DESCRIPTION");
        final String author = intent.getStringExtra("AUTHOR");
        final String applicant = getIntent().getStringExtra("username");

        //Setting the data into TextViews on this activity
        TextView taskTitle = (TextView)findViewById(R.id.textview_mytasktitleEmployee);
        TextView taskPrice = (TextView)findViewById(R.id.textview_taskpriceEmployee);
        TextView taskTags = (TextView)findViewById(R.id.textview_tasktagsEmployee);
        TextView taskDesc = (TextView)findViewById(R.id.textview_taskdescriptionEmployee);
        taskTitle.setText(title);
        taskPrice.setText(price);
        taskTags.setText(tags);
        taskDesc.setText(description);

        //OnClick method for Return button
        Button returnButton = (Button) findViewById(R.id.button_return2);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchViewTasksInProgressActivity(applicant);
            }
        });
        //Alert for submitting review
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View submit_review_layout = getLayoutInflater().inflate(R.layout.submit_review_layout, null);

        Button cancelButton = (Button) findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelTask(title,applicant,author);
                AlertDialog.Builder taskCancelled = new AlertDialog.Builder(ViewSingleTaskInProgress.this);
                taskCancelled.setTitle("Task Cancelled")
                        .setMessage("You have successfully cancelled this task!.")
                        .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                taskCancelled.show();
            }
        });

        Button markAsCompleted = (Button) findViewById(R.id.button_finished);
        markAsCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CheckBox reviewCheckbox = (CheckBox)findViewById(R.id.employeeReviewCheckbox);
                final AlertDialog.Builder taskCompleted = new AlertDialog.Builder(ViewSingleTaskInProgress.this);
                taskCompleted.setTitle("Task Completed")
                        .setMessage("You have successfully completed this task! Once " + author + " is notified, payment will process momentarily.")
                        .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.setTitle("Submit Review for: " + author)
                        .setView(submit_review_layout)
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RatingBar rb = (RatingBar)submit_review_layout.findViewById(R.id.ratingBar);
                                EditText comment = (EditText) submit_review_layout.findViewById(R.id.reviewCommentText);
                                submitReview(title, author, (int) rb.getRating(),comment.getText().toString());
                                dialog.dismiss();
                            }
                        });
                markTaskAsComplete(title, author);
                taskCompleted.show();
                if(reviewCheckbox.isChecked()){
                    builder.show();
                }
            }
        });
    }
    private void launchViewTasksInProgressActivity(String username){
        Intent intent = new Intent(this, ViewTasksInProgressActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void markTaskAsComplete(final String title, final String author){
        allTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot storedTask : snapshot.getChildren()){
                    Task task = storedTask.getValue(Task.class);
                    if(task.getAuthor().equals(author) && task.getTitle().equals(title)){
                        storedTask.getRef().child("complete").setValue(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void cancelTask(final String title, final String applicant, final String author){
        databaseReference = databaseReference.child("Applications");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot application : snapshot.getChildren()){
                    TaskApplication singleApplication = application.getValue(TaskApplication.class);
                    if(singleApplication.getTaskTitle().equals(title) &&
                            singleApplication.getApplicant().equals(applicant) &&
                            singleApplication.getAuthor().equals(author)) {
                        application.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Set 'assigned' value to False so Task is availaible to be applied for once again in ViewTasksActivity
        allTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot storedTask : snapshot.getChildren()){
                    Task task = storedTask.getValue(Task.class);
                    if(task.getAuthor().equals(author) && task.getTitle().equals(title)){
                        storedTask.getRef().child("assigned").setValue(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void submitReview(final String title, final String author, final int rating, final String comment) {
        allUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (final DataSnapshot User : snapshot.getChildren()) {
                    User user = User.getValue(User.class);
                    if (user.getUsername().equals(author)) {
                        Toast.makeText(getApplicationContext(), "User = Applicant", Toast.LENGTH_LONG).show();
                        ArrayList<String> currReview = new ArrayList<>();
                        currReview.add(title);
                        currReview.add(String.valueOf(rating));
                        currReview.add(comment);
                        ArrayList<ArrayList<String>> tempReviews = (ArrayList<ArrayList<String>>) User.child("reviews").getValue();
                        tempReviews.add(currReview);
                        User.child("reviews").getRef().setValue(tempReviews);
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