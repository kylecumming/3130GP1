package com.example.csci3130groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
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

import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class ViewCompletedTask extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference allUsers = database.getReference("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_completed_task);

        //Using intent to grab the appropriate data from the list of tasks in ViewTasksActivity.java
        Intent intent = getIntent();
        final String title = intent.getStringExtra("TITLE");
        final String price = intent.getStringExtra("PRICE");
        final String tags = intent.getStringExtra("TAGS");
        final String description = intent.getStringExtra("DESCRIPTION");
        final String username = getIntent().getStringExtra("username");
        final String[] applicants = new String[1];

        //Setting the data into TextViews on this activity
        final TextView taskTitle = (TextView)findViewById(R.id.textview_mytasktitleEmployer);
        TextView taskPrice = (TextView)findViewById(R.id.textview_taskpriceEmployer);
        TextView taskTags = (TextView)findViewById(R.id.textview_tasktagsEmployer);
        TextView taskDesc = (TextView)findViewById(R.id.textview_taskdescriptionEmployer);
        taskTitle.setText(title);
        taskPrice.setText(price);
        taskTags.setText(tags);
        taskDesc.setText(description);
        database.getReference().child("Applications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> applications = snapshot.getChildren().iterator();
                while(applications.hasNext()){
                    DataSnapshot application = applications.next();
                    TaskApplication taskApp = application.getValue(TaskApplication.class);
                    if(taskApp.getAuthor().equals(username)&& taskApp.getTaskTitle().equals(title)){
                        applicants[0] = taskApp.getApplicant();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //Alert for submitting review
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View submit_review_layout = getLayoutInflater().inflate(R.layout.submit_review_layout, null);
        builder.setTitle("Submit Review for: " + applicants[0])
                .setView(submit_review_layout)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RatingBar rb = (RatingBar)submit_review_layout.findViewById(R.id.ratingBar);
                        EditText comment = (EditText) submit_review_layout.findViewById(R.id.reviewCommentText);
                        submitReview(title, applicants[0], (int) rb.getRating(),comment.getText().toString());
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
        });
        Button returnButton = (Button) findViewById(R.id.button_return3);
        final Button approvePayment = (Button) findViewById(R.id.button_approvepayment);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchViewCompletedTasksActivity(username);
            }
        });
        approvePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox reviewCheckbox = (CheckBox)findViewById(R.id.checkBox);
                approvePayment(reviewCheckbox, applicants[0], builder);
            }
        });
    }

    private void launchViewCompletedTasksActivity(String username){
        Intent intent = new Intent(this, ViewCompletedTasksActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void approvePayment(CheckBox submitReview, String username, AlertDialog.Builder builder){
        if(submitReview.isChecked()) { //checkbox for review is checked
            AlertDialog alert = builder.create();
            alert.show();
            Toast.makeText(getApplicationContext(), "Submitted review for: " + username, Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Opening payment option", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, TaskPaymentActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }
    }
    private void submitReview(final String title, final String applicant, final int rating, final String comment) {
        allUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot User : snapshot.getChildren()){
                    User user = User.getValue(User.class);
                    if(user.getUsername().equals(applicant)){
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