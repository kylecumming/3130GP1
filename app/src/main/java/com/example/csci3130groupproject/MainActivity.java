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

    private void launchRegisterActivity(){
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void launchLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onClick2(View view){//this button will be removed later on, I am just using it as a way to skip through the menus so I can quickly test my work
        startActivity(new Intent(getApplicationContext(), ViewTasksActivity.class));
    }
    public void onClick3(View view){//this button will be removed later on, I am just using it as a way to skip through the menus so I can quickly test my work
        startActivity(new Intent(getApplicationContext(), SubmitTaskActivity.class));
    }

    public void onClick(View view){
        startActivity(new Intent(getApplicationContext(), ViewApplicationsActivity.class));
    }

}