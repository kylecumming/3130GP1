package com.example.csci3130groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SubmitTaskActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_task);

        final String username = getIntent().getStringExtra("username");

        this.addTags();

        Button submitTask = (Button) findViewById(R.id.button_submitTask);
        submitTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = get_task_title();
                String desc = get_task_desc();
                String price = get_task_price();
                String tags = get_task_tags();
                //Add a get_author() method that will grab the username of person submitting task
                String author = username;
                add_new_task_to_firebase(title,desc,tags,price,author);
                launchEmployerHomepageActivity(username);
            }
        });
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
    protected void add_new_task_to_firebase(String title, String desc, String tags, String price, String employerUsername){
        Task newTask = new Task(title,desc,tags,price,employerUsername);
        databaseReference = databaseReference.child("Tasks");
        databaseReference.push().setValue(newTask);
    }

    //This will add the different tags to the tagspinner
    protected void addTags(){
            Spinner tagList = (Spinner)findViewById(R.id.tagList);
            List<String> tags = new ArrayList<String>();
            tags.add("Lawn Care");
            tags.add("House work");
            tags.add("Construction");
            tags.add("Moving");
            tags.add("Labour");
            @SuppressLint("ResourceType")ArrayAdapter<String> tagListAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tags);
            tagListAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            tagList.setAdapter(tagListAdapter);
    }

    protected String get_task_tags(){
        Spinner tagList = (Spinner)findViewById(R.id.tagList);
        String tagText = tagList.getSelectedItem().toString();
        return tagText;
    }

    public void launchEmployerHomepageActivity(String username){
        Intent intent = new Intent(this, EmployerHomepageActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}