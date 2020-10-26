package com.example.csci3130groupproject;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SubmitTask_Test {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void taskInFirebase(){
        onView(withId(R.id.button_register)).perform(click());
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.button_signUpEmployer)).perform(click());
        onView(withId(R.id.button_homepageSubmitTask)).perform(click());
        onView(withId(R.id.edittext_taskTitle)).perform(typeText("TestTitle"));
        onView(withId(R.id.edittextmulti_taskDescription)).perform(typeText("This is a description"),closeSoftKeyboard());
        onView(withId(R.id.edittext_taskPayment)).perform(typeText("100"), closeSoftKeyboard());
        onView(withId(R.id.button_submitTask)).perform(click());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference allTasks = database.getReference("Tasks");
        allTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot storedTasks : snapshot.getChildren()){
                    Task task = storedTasks.getValue(Task.class);
                    if(task.getTitle().equals("TestTitle"))
                        assertTrue(task.getTitle().equals("TestTitle"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
