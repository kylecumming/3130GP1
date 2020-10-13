package com.example.csci3130groupproject;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

public class RegistrationActivityTests {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void registerButtonClickBringsToRegistrationPage(){

        onView(withId(R.id.button_register)).perform(click());
        onView(withText("Create a profile to get started")).check(matches(isDisplayed()));
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
        onView(withId(R.id.button_signUpEmployer)).perform(click());
        onView(withId(R.id.button_homepageSubmitTask)).check(matches(isDisplayed()));
    }

    @Test
    public void submitTaskButtonBringsToSubmitTaskActivity(){
        onView(withId(R.id.button_register)).perform(click());
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.button_signUpEmployer)).perform(click());
        onView(withId(R.id.button_homepageSubmitTask)).perform(click());
        onView(withId(R.id.button_submitTask)).check(matches(isDisplayed()));
    }

    

}
