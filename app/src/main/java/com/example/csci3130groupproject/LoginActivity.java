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

    /**
     * Handles login process by checking existing Users in database.
     * Will either bring the User to their homepage or notify them that
     * their account does not currently exist in database and prompt them to sign up.
     *
     * @param username the String input into the first EditText field on LoginActivity.xml
     *                 representing the User's username
     * @param password the String input into the second EditText field on LoginActivity.xml
     *                 representing the User's password
     */
    private void loginHandler(final String username, final String password){
        allUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User currentUser = null;
                for(DataSnapshot storedUsers : snapshot.getChildren()){
                    User user = storedUsers.getValue(User.class);
                    if(user.getUsername().equals(username) && user.getPassword().equals(password))
                        currentUser = user;
                }
                if(currentUser != null){
                    //.getType() returns either true or false, value of true means it is an Employer
                    //User meaning it should be brought to the default Employer homepage
                    if(currentUser.getType()){
                        launchEmployerHomepageActivity(currentUser.getUsername());
                    }
                    //Otherwise bring User to default Employee homepage
                    else{
                        launchEmployeeHomepageActivity(currentUser.getUsername());
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "User not found in database. Please sign up first!", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Database error!", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Creates new intent with EmployerHomepageActivity.java, erases Activity stack history
     * and launches this new Activity; bringing User to default Employer homepage and making
     * previous Activities inaccessible. String 'username' is pushed to next Activity for later use.
     *
     * @param username the String which represents the username of an existing User. Used in 
     *                 subsequent Activities, through the use of putExtra(), as a way to persist
     *                 "logged in" functionality.
     */
    private void launchEmployerHomepageActivity(String username){
        Intent intent = new Intent(this, EmployerHomepageActivity.class);
        intent.putExtra("username", username);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    /**
     * Creates new intent with EmployeeHomepageActivity.java, erases Activity stack history
     * and launches this new Activity; bringing User to default Employee homepage and making
     * previous Activities inaccessible. String 'username' is pushed to next Activity for later use.
     *
     * @param username the String which represents the username of an existing User. Used in
     *                 subsequent Activities, through the use of putExtra(), as a way to persist
     *                 "logged in" functionality.
     */
    private void launchEmployeeHomepageActivity(String username){
        Intent intent = new Intent(this, EmployeeHomepageActivity.class);
        intent.putExtra("username", username);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}