package com.example.csci3130groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference allUsers = database.getReference("Users");
    User currentUser = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditext = findViewById(R.id.edittext_username);
        final EditText passwordEdittext = findViewById(R.id.edittext_password);
        Button login = (Button) findViewById((R.id.button_finalLogin));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = usernameEditext.getText().toString();
                final String password = passwordEdittext.getText().toString();
                loginHandler(username, password);
            }
        });

    }

    private void loginHandler(final String username, final String password){
        allUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot storedUsers : snapshot.getChildren()){
                    User user = storedUsers.getValue(User.class);
                    if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                        if(user.getType()){
                            launchEmployerHomepageActivity();
                        }
                        else{
                            launchEmployeeHomepageActivity();
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void launchEmployerHomepageActivity(){
        Intent intent = new Intent(this, EmployerHomepageActivity.class);
        startActivity(intent);
    }

    private void launchEmployeeHomepageActivity(){
        Intent intent = new Intent(this, EmployeeHomepageActivity.class);
        startActivity(intent);
    }

}