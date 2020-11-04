package com.example.csci3130groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewApplicationsActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference allApplications = database.getReference("Applications");
    //Change this variable to the username of the current employer on the page
    String employerUsername = "EmployerUsername";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_applications);

        final LinearLayout displayApplications = (LinearLayout) findViewById(R.id.linearlayout_applications);

        allApplications.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot storedApplications : snapshot.getChildren()){
                    TaskApplication application = storedApplications.getValue(TaskApplication.class);
                    //Only display the Applications related to this Employer i.e. applications for their tasks
                    if(employerUsername.equals(application.getAuthor())){
                        Button singleApplication = new Button(getApplicationContext());

                        //Display application data inside button
                        singleApplication.setText(Html.fromHtml(formatApplicationData(application)));

                        //Setting layout, color, general styles, etc.
                        singleApplication.setLayoutParams(new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        ));

                        singleApplication.setGravity(Gravity.LEFT);
                        singleApplication.setTextColor(Color.BLACK);
                        singleApplication.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        LinearLayout.LayoutParams margins = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        margins.setMargins(54,54,54,0);
                        singleApplication.setLayoutParams(margins);

                        displayApplications.addView(singleApplication);

                        //Save title and applicant info for display on ViewSingleTaskActivity.java
                        final String title = application.getTaskTitle();
                        final String applicant = application.getApplicant();
                    }
                    else
                        continue;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //Returns a formatted String for proper display of applications on this page
    public String formatApplicationData(TaskApplication application){
        return "<b>Task Title</b>: " + application.getTaskTitle() + "<br><b>Task Applicant</b>: " +
                application.getApplicant();
    }

}