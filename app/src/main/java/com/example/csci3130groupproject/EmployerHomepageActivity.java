package com.example.csci3130groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EmployerHomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_homepage);

        findViewById(R.id.button_homepageSubmitTask).setOnClickListener(new View.OnClickListener(){
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