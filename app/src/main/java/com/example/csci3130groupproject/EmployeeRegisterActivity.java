package com.example.csci3130groupproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;

public class EmployeeRegisterActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_register);
        findViewById(R.id.button_signUpEmployee).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                findViewById(R.id.emailError).setVisibility(View.GONE);
                findViewById(R.id.passwordError).setVisibility(View.GONE);
                findViewById(R.id.usernameError).setVisibility(View.GONE);
                findViewById(R.id.monthError).setVisibility(View.GONE);
                findViewById(R.id.dayError).setVisibility(View.GONE);
                findViewById((R.id.yearError)).setVisibility(View.GONE);
                TextView emailTextView = findViewById(R.id.edittext_emailEmployee);
                TextView usernameTextView = findViewById(R.id.edittext_usernameEmployee);
                TextView passwordTextView = findViewById(R.id.edittext_passwordEmployee);
                TextView dobTextViewDay= findViewById(R.id.edittext_dayEmployee);
                TextView dobTextViewMonth = findViewById(R.id.edittext_monthEmployee);
                TextView dobTextViewYear = findViewById(R.id.edittext_yearEmployee);
                String email = emailTextView.getText().toString();
                String username = usernameTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                ArrayList<String> dob = new ArrayList<>();
                dob.add(dobTextViewMonth.getText().toString());
                dob.add(dobTextViewDay.getText().toString());
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
                if(us.isValid()){
                    databaseReference = databaseReference.child("Users");
                    databaseReference.push().setValue(us);
                    launchEmployeeHomepageActivity(us.getUsername());
                }
                else{
                    checkErrors(us);
                }
            }
        });
    }
    private void checkErrors(User us){
        if(!us.emailIsValid()){
            findViewById(R.id.emailError).setVisibility(View.VISIBLE);
        }
        if(!us.passwordIsValid()){
            findViewById(R.id.passwordError).setVisibility(View.VISIBLE);
        }
        if(!us.usernameIsValid()){
            findViewById(R.id.usernameError).setVisibility(View.VISIBLE);
        }
        if(!us.dobMonthIsValid()){
            findViewById(R.id.monthError).setVisibility(View.VISIBLE);
        }
        if(!us.dobDayIsValid()){
            findViewById(R.id.dayError).setVisibility(View.VISIBLE);
        }
        if(!us.dobYearIsValid()){
            findViewById((R.id.yearError)).setVisibility(View.VISIBLE);
        }
    }
    private void launchEmployeeHomepageActivity(String username){
        Intent intent = new Intent(this, EmployeeHomepageActivity.class);
        intent.putExtra("username", username);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}