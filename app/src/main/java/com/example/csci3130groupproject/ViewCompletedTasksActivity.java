package com.example.csci3130groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class ViewCompletedTasksActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference allTasks = database.getReference("Tasks");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_completed_tasks);

        final LinearLayout displayTasks = (LinearLayout) findViewById(R.id.linearlayout_mytasksEmployer);
        final String username = getIntent().getStringExtra("username");

        allTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot storedTask : snapshot.getChildren()){
                    Task task = storedTask.getValue(Task.class);
                    if(!task.getAuthor().equals(username) || task.getComplete() == false)
                        continue;

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


                    /* when the a button is clicked, this onClick method starts the "ViewSingleTask"
                     * activity, and passes over the appropriate data using the putExtra() method.
                     */
                    singleTask.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), ViewSingleTask.class);
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //Returns a formatted String for proper display of tasks on this page
    public String formatTaskData(Task task){
        return "<b>Task Title</b>: " + task.getTitle() + "<br><b>Task Description</b>: "
                + task.getDescription() + "<br><b>Task Tags</b>: " + task.getTags() + "<br><b>Task Payment</b>: " + task.getPrice();
    }

}