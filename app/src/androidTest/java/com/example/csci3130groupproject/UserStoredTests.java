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
import static org.junit.Assert.assertEquals;

public class UserStoredTests {

    //Instance of db to use in all tests
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void uponEmployerSignUpUserIsStored(){
        onView(withId(R.id.button_register)).perform(click());
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.edittext_emailEmployer)).perform(typeText("exampleEmployer@gmail.com"));
        onView(withId(R.id.edittext_passwordEmployer)).perform(typeText("employerpassword123"),closeSoftKeyboard());
        onView(withId(R.id.edittext_usernameEmployer)).perform(typeText("Employ13"),closeSoftKeyboard());
        onView(withId(R.id.edittext_monthEmployer)).perform(typeText("03"));
        onView(withId(R.id.edittext_dayEmployer)).perform(typeText("11"));
        onView(withId(R.id.edittext_yearEmployer)).perform(typeText("1999"),closeSoftKeyboard());
        //Add in select radio button for gender
        onView(withId(R.id.button_signUpEmployer)).perform(click());

        //Check if User object is stored in Firebase
        DatabaseReference allUsers = database.getReference("Users");
        allUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userFinal = new User();
                for(DataSnapshot storedUser : snapshot.getChildren()){
                    User user = storedUser.getValue(User.class);
                    if(user.getEmail().equals("exampleEmployer@gmail.com"))
                        userFinal = user;
                }
                assertEquals("Employ13", userFinal.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Test
    public void reviewIsAdded() {
        onView(withId(R.id.button_register)).perform(click());
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.edittext_emailEmployer)).perform(typeText("exampleEmployer@gmail.com"));
        onView(withId(R.id.edittext_passwordEmployer)).perform(typeText("employerpassword123"), closeSoftKeyboard());
        onView(withId(R.id.edittext_usernameEmployer)).perform(typeText("Employ13"), closeSoftKeyboard());
        onView(withId(R.id.edittext_monthEmployer)).perform(typeText("03"));
        onView(withId(R.id.edittext_dayEmployer)).perform(typeText("11"));
        onView(withId(R.id.edittext_yearEmployer)).perform(typeText("1999"), closeSoftKeyboard());
        //Add in select radio button for gender
        onView(withId(R.id.button_signUpEmployer)).perform(click());

        //Check if User object is stored in Firebase
        DatabaseReference allUsers = database.getReference("Users");
        allUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userFinal = new User();
                for (DataSnapshot storedUser : snapshot.getChildren()) {
                    User user = storedUser.getValue(User.class);
                    if (user.getEmail().equals("exampleEmployer@gmail.com"))
                        userFinal = user;
                }
                userFinal.addReview(4, "mow lawn","bad job");
                System.out.println(userFinal.getReviews());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Test
    public void uponEmployeeSignUpUserIsStored(){
        onView(withId(R.id.button_register)).perform(click());
        onView(withId(R.id.button_employee)).perform(click());
        onView(withId(R.id.edittext_emailEmployee)).perform(typeText("exampleEmployee@gmail.com"));
        onView(withId(R.id.edittext_passwordEmployee)).perform(typeText("employeepassword321"),closeSoftKeyboard());
        onView(withId(R.id.edittext_usernameEmployee)).perform(typeText("Employee5"),closeSoftKeyboard());
        onView(withId(R.id.edittext_monthEmployee)).perform(typeText("09"));
        onView(withId(R.id.edittext_dayEmployee)).perform(typeText("28"));
        onView(withId(R.id.edittext_yearEmployee)).perform(typeText("2000"),closeSoftKeyboard());
        //Add in select radio button for gender
        onView(withId(R.id.button_signUpEmployee)).perform(click());

        //Check if User object is stored in Firebase
        DatabaseReference allUsers = database.getReference("Users");
        allUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userFinal = new User();
                for(DataSnapshot storedUser : snapshot.getChildren()){
                    User user = storedUser.getValue(User.class);
                    if(user.getEmail().equals("exampleEmployee@gmail.com"))
                        userFinal = user;
                }
                assertEquals("Employee5", userFinal.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
