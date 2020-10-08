package com.example.csci3130groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Clicking Employer button - launches EmployerRegisterActivity
        findViewById(R.id.button_employer).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                launchEmployerRegisterActivity();
            }
        });

        //Clicking Employee button - launches EmployeeRegisterActivity
        findViewById(R.id.button_employee).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                launchEmployeeRegisterActivity();
            }
        });

    }

    private void launchEmployerRegisterActivity(){
        Intent intent = new Intent(this, EmployerRegisterActivity.class);
        startActivity(intent);
    }

    private void launchEmployeeRegisterActivity(){
        Intent intent = new Intent(this, EmployeeRegisterActivity.class);
        startActivity(intent);
    }

}