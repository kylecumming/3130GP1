package com.example.csci3130groupproject;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

public class GeneralNavigationTests {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void registerButtonClickBringsToRegistrationPage(){
        onView(withId(R.id.button_register)).perform(click());
        onView(withText("Create a profile to get started")).check(matches(isDisplayed()));
    }

    @Test
    public void loginButtonBringsToLoginPage(){
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.textview_username)).check(matches(isDisplayed()));
    }

    @Test
    public void employerButtonBringsToEmployerRegistrationPage(){
        onView(withId(R.id.button_register)).perform(click());
        onView(withId(R.id.button_employer)).perform(click());
        onView(withText("Employer Sign up")).check(matches(isDisplayed()));
    }

    @Test
    public void employeeButtonBringsToEmployeeRegistrationPage(){
        onView(withId(R.id.button_register)).perform(click());
        onView(withId(R.id.button_employee)).perform(click());
        onView(withText("Employee Sign up")).check(matches(isDisplayed()));
    }

    @Test
    public void employerSignUpButtonBringsToEmployerHomepage(){
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
        onView(withId(R.id.button_homepageSubmitTask)).check(matches(isDisplayed()));
    }

    @Test
    public void submitTaskButtonBringsToSubmitTaskActivity(){
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.edittext_username)).perform(typeText("TestEmployerBot"), closeSoftKeyboard());
        onView(withId(R.id.edittext_password)).perform(typeText("testingpassword"), closeSoftKeyboard());
        onView(withId(R.id.button_finalLogin)).perform(click());
        onView(withId(R.id.button_homepageSubmitTask)).perform(click());
        onView(withId(R.id.button_submitTask)).check(matches(isDisplayed()));
    }

    @Test
    public void employeeSignUpButtonBringsToEmployeeHomepage(){
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
        onView(withId(R.id.button_viewtasks)).check(matches(isDisplayed()));
    }

    @Test
    public void viewTasksButtonBringsToViewTasksPage(){
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.edittext_username)).perform(typeText("TestEmployeeBot"), closeSoftKeyboard());
        onView(withId(R.id.edittext_password)).perform(typeText("testingpassword"), closeSoftKeyboard());
        onView(withId(R.id.button_finalLogin)).perform(click());
        onView(withId(R.id.button_viewtasks)).perform(click());
        onView(withId(R.id.scrollview_tasks)).check(matches(isDisplayed()));
    }

    @Test
    public void viewApplicationsButtonBringsToViewApplicationsPage(){
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.edittext_username)).perform(typeText("TestEmployerBot"), closeSoftKeyboard());
        onView(withId(R.id.edittext_password)).perform(typeText("testingpassword"), closeSoftKeyboard());
        onView(withId(R.id.button_finalLogin)).perform(click());
        onView(withId(R.id.button_viewApplications)).perform(click());
        onView(withId(R.id.textview_quickcashApplications)).check(matches(isDisplayed()));
    }

    @Test
    public void viewTasksInProgressPageFromEmployeeHomepage(){
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.edittext_username)).perform(typeText("TestEmployeeBot"), closeSoftKeyboard());
        onView(withId(R.id.edittext_password)).perform(typeText("testingpassword"), closeSoftKeyboard());
        onView(withId(R.id.button_finalLogin)).perform(click());
        onView(withId(R.id.button_viewAcceptedTasks)).perform(click());
        onView(withText("My Tasks in Progress")).check(matches(isDisplayed()));
    }

    @Test
    public void viewTasksInProgressPageFromEmployerHomepage(){
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.edittext_username)).perform(typeText("TestEmployerBot"), closeSoftKeyboard());
        onView(withId(R.id.edittext_password)).perform(typeText("testingpassword"), closeSoftKeyboard());
        onView(withId(R.id.button_finalLogin)).perform(click());
        onView(withId(R.id.button_viewMyTasksEmployer)).perform(click());
        onView(withText("Completed Tasks")).check(matches(isDisplayed()));
    }
}
