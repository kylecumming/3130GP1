package com.example.csci3130groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TaskSubmitted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_submitted);
        findViewById(R.id.submitAnotherTaskButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                launchSubmitTaskActivity();
            }
        });
    }
    private void launchSubmitTaskActivity(){
        Intent intent = new Intent(this, SubmitTaskActivity.class);
        startActivity(intent);
    }
}