package com.example.csci3130groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SubmitTaskActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_task);
    }

    //this grabs the task title from the appropriate textview
    protected String get_task_title() {
        EditText taskTitleTextBox = (EditText) findViewById(R.id.edittext_taskTitle);
        String title = taskTitleTextBox.getText().toString();
        return title;
    }
    //this grabs the task description from the appropriate textview
    protected String get_task_desc() {
        EditText taskDescTextBox = (EditText) findViewById(R.id.edittextmulti_taskDescription);
        String desc = taskDescTextBox.getText().toString();
        return desc;
    }
    //this grabs the task price from the appropriate textview
    protected String get_task_price() {
        EditText taskPriceTextBox = (EditText) findViewById(R.id.edittext_taskPayment);
        String price = taskPriceTextBox.getText().toString();
        return price;
    }
    //this will add the strings from the textview to the database
    protected void add_new_task_to_firebase(String title, String desc,String price, String employerUsername){
        Task newTask = new Task(title,desc,price,employerUsername);
        databaseReference = databaseReference.child("Tasks");
        databaseReference.push().setValue(newTask);
    }

    public void onClick(View view){
        String title = get_task_title();
        String desc = get_task_desc();
        String price = get_task_price();
        //Add a get_author() method that will grab the username of person submitting task
        String author = "EmployerUsername";
        this.add_new_task_to_firebase(title,desc,price, author);
        startActivity(new Intent(getApplicationContext(), TaskSubmitted.class));
    }
}