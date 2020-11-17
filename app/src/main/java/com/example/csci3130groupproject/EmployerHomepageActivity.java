package com.example.csci3130groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmployerHomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_homepage);

        final String username = getIntent().getStringExtra("username");

        Button createTask = (Button) findViewById(R.id.button_homepageSubmitTask);
        Button viewApplications = (Button) findViewById(R.id.button_viewApplications);

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

}