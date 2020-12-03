package com.example.csci3130groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;

public class ViewSingleApplicationActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

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

        //Rating bar
        RatingBar averageRating = (RatingBar)findViewById(R.id.averageRatingBar);
        //display rating bar filled with applicant's average rating
        getAverage(applicant, averageRating);
        //OnClick method for Return button
        Button returnButton = (Button) findViewById(R.id.button_returnApplication);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchViewApplicationsActivity(author);
            }
        });
        ScrollView reviews = findViewById(R.id.reviewScrollView);
        LinearLayout reviewsLinearLayout = findViewById(R.id.reviewsLinearLayout);

        plotReviews(applicant,reviewsLinearLayout);
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
    private void plotReviews(final String applicant, final LinearLayout reviewLinearLayout){
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> users = snapshot.getChildren().iterator();
                while(users.hasNext()){
                    DataSnapshot user = users.next();
                    User currUser = user.getValue(User.class);
                    if(currUser.getUsername().equals(applicant)){
                        ArrayList<ArrayList<String>> reviewList = currUser.getReviews();
                        if(reviewList.size()>1){
                            for(int i=1; i<reviewList.size(); i++){
                                View view_review_layout = getLayoutInflater().inflate(R.layout.view_review_layout, null);
                                RatingBar rb = view_review_layout.findViewById(R.id.reviewRating);
                                TextView taskTitle = view_review_layout.findViewById(R.id.reviewTitle);
                                TextView taskComments= view_review_layout.findViewById(R.id.reviewDescription);
                                rb.setRating(Float.parseFloat(reviewList.get(i).get(1)));
                                taskTitle.setText(reviewList.get(i).get(0).toString());
                                taskComments.setText(reviewList.get(i).get(2).toString());
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
    }
    private void getAverage(final String applicant, final RatingBar rb){
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> users = snapshot.getChildren().iterator();
                while(users.hasNext()){
                    DataSnapshot user = users.next();
                    User currUser = user.getValue(User.class);
                    if(currUser.getUsername().equals(applicant)){
                        rb.setStepSize((float)currUser.averageRating());
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