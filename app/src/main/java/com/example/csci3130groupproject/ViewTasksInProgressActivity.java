package com.example.csci3130groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ViewTasksInProgressActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference allTasks = database.getReference("Tasks");
    DatabaseReference allApplications = database.getReference("Applications");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks_in_progress);

        final LinearLayout displayTasks = (LinearLayout) findViewById(R.id.linearlayout_mytasksEmployee);
        final String username = getIntent().getStringExtra("username");
        final ArrayList<String> taskAuthor = new ArrayList<String>(); //Store author names for applications user has been approved for
        final ArrayList<String> taskTitle = new ArrayList<String>(); //Store name of the task which user has been approved for

        //Review applications to determine which tasks to be displayed next
        //Only tasks which this user has applied to, and been accepted for, should be shown
        allApplications.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                for(DataSnapshot storedApplication : snapshot.getChildren()){
                    TaskApplication application = storedApplication.getValue(TaskApplication.class);
                    //When application is related to this user and Employer has approved (.getAccepted() == true)
                    if(application.getApplicant().equals(username) && application.getAccepted() == true){
                        taskAuthor.add(application.getAuthor());
                        taskTitle.add(application.getTaskTitle());
                    }
                }

                //Displays tasks that this user has applied to and been accepted for so they can mark the progress (i.e. notify when finished)
                allTasks.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot storedTask : snapshot.getChildren()){
                            Task task = storedTask.getValue(Task.class); //A single task snapshot

                            //Check if this task is a task that has been assigned to this user
                            //If unrelated then this code is skipped
                            if(taskAuthor.contains(task.getAuthor()) && taskTitle.contains(task.getTitle())){
                                Button singleTask = new Button(getApplicationContext());

                                //Display all task data inside button
                                singleTask.setText(Html.fromHtml(formatTaskData(task)));

                                //Setting layout, color, general styles, etc.
                                singleTask.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                ));

                                singleTask.setGravity(Gravity.LEFT);
                                singleTask.setTextColor(Color.BLACK);
                                singleTask.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                                LinearLayout.LayoutParams margins = new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                margins.setMargins(54,54,54,0);
                                singleTask.setLayoutParams(margins);

                                final String title = task.getTitle();
                                final String price = task.getPrice();
                                final String tags = task.getTags();
                                final String description = task.getDescription();
                                final String author = task.getAuthor();

                                displayTasks.addView(singleTask);//Add new button to LinearLayout


                                /* when the a button is clicked, this onClick method starts the "ViewSingleTaskInProgress"
                                 * activity, and passes over the appropriate data using the putExtra() method.
                                 */
                                singleTask.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getApplicationContext(), ViewSingleTaskInProgress.class);
                                        intent.putExtra("TITLE",title);
                                        intent.putExtra("PRICE",price);
                                        intent.putExtra("TAGS", tags);
                                        intent.putExtra("DESCRIPTION",description);
                                        intent.putExtra("AUTHOR",author);
                                        intent.putExtra("username", username);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Returns a formatted String for proper display of tasks on this page
    public String formatTaskData(Task task){
        String done;
        if(task.getComplete() == false){
            done = "Unfinished";
        }
        else
            done = "Finished";
        return "<b>Task Title</b>: " + task.getTitle() + "<br><b>Task Description</b>: "
                + task.getDescription() + "<br><b>Task Tags</b>: " + task.getTags() + "<br><b>Task Payment</b>: " + task.getPrice()
                + "<br><b>Completion Status: " + done;
    }

}