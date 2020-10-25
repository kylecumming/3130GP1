package com.example.csci3130groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EmployeeRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_register);

        findViewById(R.id.button_signUpEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchEmployeeHomepageActivity();
            }
        });

    }

    private void launchEmployeeHomepageActivity(){
        Intent intent = new Intent(this, EmployeeHomepageActivity.class);
        startActivity(intent);
    }

}