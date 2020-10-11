package com.example.csci3130groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EmployerRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_register);

        findViewById(R.id.button_signUpEmployer).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                launchEmployerHomepageActivity();
            }
        });

    }

    private void launchEmployerHomepageActivity(){
        Intent intent = new Intent(this, EmployerHomepageActivity.class);
        startActivity(intent);
    }

}