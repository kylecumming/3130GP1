package com.example.csci3130groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button) findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchLoginActivity();
            }
        });

        Button register = (Button) findViewById(R.id.button_register);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                launchRegisterActivity();
            }
        });
    }

    /**
     * Creates new intent with RegistrationActivity.java and starts this Activity;
     * bringing User to Registration page.
     */
    private void launchRegisterActivity(){
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    /**
     * Creates new intent with LoginActivity.java and starts this Activity;
     * bringing User to Login page.
     */
    private void launchLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}