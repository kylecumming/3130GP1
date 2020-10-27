package com.example.csci3130groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class EmployeeRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_register);
        findViewById(R.id.button_signUpEmployee).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                TextView emailTextView = findViewById(R.id.edittext_emailEmployee);
                TextView usernameTextView = findViewById(R.id.edittext_usernameEmployee);
                TextView passwordTextView = findViewById(R.id.edittext_passwordEmployee);
                TextView dobTextViewDay= findViewById(R.id.edittext_dayEmployee);
                TextView dobTextViewMonth = findViewById(R.id.edittext_monthEmployee);
                TextView dobTextViewYear = findViewById(R.id.edittext_yearEmployee);
                String email = emailTextView.getText().toString();
                String username = usernameTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                ArrayList<String> dob = new ArrayList<String>();
                dob.add(dobTextViewDay.getText().toString());
                dob.add(dobTextViewMonth.getText().toString());
                dob.add(dobTextViewYear.getText().toString());
                String gender = "Not Specified";
                RadioGroup genderSelections = findViewById(R.id.radioGroup_Employee);
                for (int i =0; i < genderSelections.getChildCount(); i++ ){
                    RadioButton rb = (RadioButton) genderSelections.getChildAt(i);
                    if(rb.isChecked()){
                        gender = rb.getText().toString();
                    }
                }
                User us = new User(email, username, password,dob, gender, false);
                /* Further down the line, do something with the created object */
                Log.d("TAG", us.toString());
                launchEmployeeHomepageActivity();
            }
        });

    }
    private void launchEmployeeHomepageActivity(){
        Intent intent = new Intent(this, EmployeeHomepageActivity.class);
        startActivity(intent);
    }
}