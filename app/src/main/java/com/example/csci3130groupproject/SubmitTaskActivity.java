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
    DatabaseReference taskNameRef = database.getReference("taskName");
    DatabaseReference taskDescRef = database.getReference("taskDesc");
    DatabaseReference taskPriceRef = database.getReference("taskPrice");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_task);
    }
    protected void initializeFirebase(){
        this.database = FirebaseDatabase.getInstance();
        taskNameRef = database.getReference("taskName");
        taskDescRef = database.getReference("taskDesc");
        taskPriceRef = database.getReference("taskPrice");
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
    protected void add_new_task_to_firebase(String title, String desc,String price){
        this.taskNameRef.push().setValue(title);
        this.taskDescRef.push().setValue(desc);
        this.taskPriceRef.push().setValue(price);
    }

    public void onClick(View view){
        String title = get_task_title();
        String desc = get_task_desc();
        String price = get_task_price();
        this.add_new_task_to_firebase(title,desc,price);
        startActivity(new Intent(getApplicationContext(), TaskSubmitted.class));
    }
    /* this is an older method to test if the data is being properly stored in the database
    public void testFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference taskNameRef = database.getReference("taskName");
        taskNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    } */

}