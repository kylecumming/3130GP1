package com.example.csci3130groupproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class EmployerRegisterActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_register);

        findViewById(R.id.button_signUpEmployer).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                findViewById(R.id.emailError2).setVisibility(View.GONE);
                findViewById(R.id.passwordError2).setVisibility(View.GONE);
                findViewById(R.id.usernameError2).setVisibility(View.GONE);
                findViewById(R.id.monthError2).setVisibility(View.GONE);
                findViewById(R.id.dayError2).setVisibility(View.GONE);
                findViewById((R.id.yearError2)).setVisibility(View.GONE);
                TextView emailTextView = findViewById(R.id.edittext_emailEmployer);
                TextView usernameTextView = findViewById(R.id.edittext_usernameEmployer);
                TextView passwordTextView = findViewById(R.id.edittext_passwordEmployer);
                TextView dobTextViewDay= findViewById(R.id.edittext_dayEmployer);
                TextView dobTextViewMonth = findViewById(R.id.edittext_monthEmployer);
                TextView dobTextViewYear = findViewById(R.id.edittext_yearEmployer);
                String email = emailTextView.getText().toString();
                String username = usernameTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                ArrayList<String> dob = new ArrayList<>();
                dob.add(dobTextViewMonth.getText().toString());
                dob.add(dobTextViewDay.getText().toString());
                dob.add(dobTextViewYear.getText().toString());
                String gender = "Not Specified";
                RadioGroup genderSelections = findViewById(R.id.radioGroup_Employer);
                for (int i =0; i < genderSelections.getChildCount(); i++ ){
                    RadioButton rb = (RadioButton) genderSelections.getChildAt(i);
                    if(rb.isChecked()){
                        gender = rb.getText().toString();
                    }
                }
                User us = new User(email, username, password,dob, gender, true);
                /* Further down the line, do something with the created object */
                if(us.isValid()){
                    databaseReference = databaseReference.child("Users");
                    databaseReference.push().setValue(us);
                    launchEmployerHomepageActivity(us.getUsername());
                }
                else{
                    checkErrors(us);
                }
            }
        });
    }
    private void checkErrors(User us){
        if(!us.emailIsValid()){
            findViewById(R.id.emailError2).setVisibility(View.VISIBLE);
        }
        if(!us.passwordIsValid()){
            findViewById(R.id.passwordError2).setVisibility(View.VISIBLE);
        }
        if(!us.usernameIsValid()){
            findViewById(R.id.usernameError2).setVisibility(View.VISIBLE);
        }
        if(!us.dobMonthIsValid()){
            findViewById(R.id.monthError2).setVisibility(View.VISIBLE);
        }
        if(!us.dobDayIsValid()){
            findViewById(R.id.dayError2).setVisibility(View.VISIBLE);
        }
        if(!us.dobYearIsValid()){
            findViewById((R.id.yearError2)).setVisibility(View.VISIBLE);
        }
    }
    private void launchEmployerHomepageActivity(String username){
        Intent intent = new Intent(this, EmployerHomepageActivity.class);
        intent.putExtra("username", username);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}