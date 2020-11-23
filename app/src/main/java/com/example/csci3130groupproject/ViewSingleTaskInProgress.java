package com.example.csci3130groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewSingleTaskInProgress extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference allTasks = database.getReference("Tasks");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_task_in_progress);

        //Using intent to grab the appropriate data from the list of tasks in ViewTasksActivity.java
        Intent intent = getIntent();
        final String title = intent.getStringExtra("TITLE");
        final String price = intent.getStringExtra("PRICE");
        final String tags = intent.getStringExtra("TAGS");
        final String description = intent.getStringExtra("DESCRIPTION");
        final String author = intent.getStringExtra("AUTHOR");
        final String applicant = getIntent().getStringExtra("username");

        //Setting the data into TextViews on this activity
        TextView taskTitle = (TextView)findViewById(R.id.textview_mytasktitleEmployee);
        TextView taskPrice = (TextView)findViewById(R.id.textview_taskpriceEmployee);
        TextView taskTags = (TextView)findViewById(R.id.textview_tasktagsEmployee);
        TextView taskDesc = (TextView)findViewById(R.id.textview_taskdescriptionEmployee);
        taskTitle.setText(title);
        taskPrice.setText(price);
        taskTags.setText(tags);
        taskDesc.setText(description);

        //OnClick method for Return button
        Button returnButton = (Button) findViewById(R.id.button_return2);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchViewTasksInProgressActivity(applicant);
            }
        });

        Button markAsCompleted = (Button) findViewById(R.id.button_finished);
        markAsCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markTaskAsComplete(title, author);
                AlertDialog.Builder taskCompleted = new AlertDialog.Builder(ViewSingleTaskInProgress.this);
                taskCompleted.setTitle("Task Completed")
                        .setMessage("You have successfully completed this task! Once " + author + " is notified, payment will process momentarily.")
                        .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                taskCompleted.show();
            }
        });

    }

    private void launchViewTasksInProgressActivity(String username){
        Intent intent = new Intent(this, ViewTasksInProgressActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void markTaskAsComplete(final String title, final String author){
        allTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot storedTask : snapshot.getChildren()){
                    Task task = storedTask.getValue(Task.class);
                    if(task.getAuthor().equals(author) && task.getTitle().equals(title)){
                        storedTask.getRef().child("complete").setValue(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}