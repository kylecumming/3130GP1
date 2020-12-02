package com.example.csci3130groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button employerRegisterButton = (Button) findViewById(R.id.button_employer);
        Button employeeRegisterButton = (Button) findViewById(R.id.button_employee);

        employerRegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                launchEmployerRegisterActivity();
            }
        });

        employeeRegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                launchEmployeeRegisterActivity();
            }
        });

    }

    /**
     * Brings User to the Registration page for an Employer type User
     */
    private void launchEmployerRegisterActivity(){
        Intent intent = new Intent(this, EmployerRegisterActivity.class);
        startActivity(intent);
    }

    /**
     * Brings User to the Registration page for an Employee type User
     */
    private void launchEmployeeRegisterActivity(){
        Intent intent = new Intent(this, EmployeeRegisterActivity.class);
        startActivity(intent);
    }

}