package com.example.csci3130groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewCompletedTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_completed_task);

        //Using intent to grab the appropriate data from the list of tasks in ViewTasksActivity.java
        Intent intent = getIntent();
        final String title = intent.getStringExtra("TITLE");
        final String price = intent.getStringExtra("PRICE");
        final String tags = intent.getStringExtra("TAGS");
        final String description = intent.getStringExtra("DESCRIPTION");
        final String username = getIntent().getStringExtra("username");

        //Setting the data into TextViews on this activity
        TextView taskTitle = (TextView)findViewById(R.id.textview_mytasktitleEmployer);
        TextView taskPrice = (TextView)findViewById(R.id.textview_taskpriceEmployer);
        TextView taskTags = (TextView)findViewById(R.id.textview_tasktagsEmployer);
        TextView taskDesc = (TextView)findViewById(R.id.textview_taskdescriptionEmployer);
        taskTitle.setText(title);
        taskPrice.setText(price);
        taskTags.setText(tags);
        taskDesc.setText(description);

        Button returnButton = (Button) findViewById(R.id.button_return3);
        final Button approvePayment = (Button) findViewById(R.id.button_approvepayment);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchViewCompletedTasksActivity(username);
            }
        });

        approvePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                approvePayment();
            }
        });


    }

    private void launchViewCompletedTasksActivity(String username){
        Intent intent = new Intent(this, ViewCompletedTasksActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void approvePayment(){
        Toast.makeText(getApplicationContext(), "Please create approve payment method", Toast.LENGTH_LONG).show();
    }

}