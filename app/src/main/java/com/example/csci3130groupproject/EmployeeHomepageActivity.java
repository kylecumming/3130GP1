package com.example.csci3130groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EmployeeHomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_homepage);

        final String username = getIntent().getStringExtra("username");

        findViewById(R.id.button_viewtasks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchViewTasksActivity(username);
            }
        });

    }

    private void launchViewTasksActivity(String username){
        Intent intent = new Intent(this, ViewTasksActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

}