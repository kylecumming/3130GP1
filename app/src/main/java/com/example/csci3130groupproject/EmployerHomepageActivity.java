package com.example.csci3130groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployerHomepageActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference allUsers = database.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_homepage);

        final String username = getIntent().getStringExtra("username");

        Button createTask = (Button) findViewById(R.id.button_homepageSubmitTask);
        Button viewApplications = (Button) findViewById(R.id.button_viewApplications);
        Button viewTasks = (Button) findViewById(R.id.button_viewMyTasksEmployer);
        Button switchViews = (Button) findViewById(R.id.button_switchviewsEmployer);

        createTask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                launchSubmitTaskActivity(username);
            }
        });

        viewApplications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchViewApplicationsActivity(username);
            }
        });

        viewTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchViewCompletedTasksActivity(username);
            }
        });

        switchViews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchEmployeeHomepageActivity(username);
            }
        });


    }

    private void launchSubmitTaskActivity(String username){
        Intent intent = new Intent(this, SubmitTaskActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void launchViewApplicationsActivity(String username){
        Intent intent = new Intent(this, ViewApplicationsActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void launchViewCompletedTasksActivity(String username){
        Intent intent = new Intent(this, ViewCompletedTasksActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void launchEmployeeHomepageActivity(String username){
        Intent intent = new Intent(this, EmployeeHomepageActivity.class);
        intent.putExtra("username", username);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}