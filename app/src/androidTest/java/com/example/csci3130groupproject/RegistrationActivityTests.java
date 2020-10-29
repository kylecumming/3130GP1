package com.example.csci3130groupproject;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class RegistrationActivityTests {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void EmployeeSignupButtonDisplaysErrorHandling(){
        onView(withId(R.id.button_register)).perform(click());
        onView(withId(R.id.button_employee)).perform(click());
        onView(withId(R.id.edittext_emailEmployee)).perform(typeText("test123@email.com"),closeSoftKeyboard());
        onView(withId(R.id.edittext_passwordEmployee)).perform(typeText("fail"),closeSoftKeyboard());
        onView(withId(R.id.edittext_usernameEmployee)).perform(typeText("testusername"),closeSoftKeyboard());
        onView(withId(R.id.edittext_monthEmployee)).perform(typeText("12"),closeSoftKeyboard());
        onView(withId(R.id.edittext_dayEmployee)).perform(typeText("01"),closeSoftKeyboard());
        onView(withId(R.id.edittext_yearEmployee)).perform(typeText("1950"),closeSoftKeyboard());
        onView(withId(R.id.button_signUpEmployee)).perform(click());
        onView(withId(R.id.passwordError)).check(matches(isDisplayed()));
    }
    @Test
    public void EmployerSignupButtonDisplaysErrorHandling(){
        onView(withId(R.id.button_register)).perform(click());
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.edittext_emailEmployer)).perform(typeText("test123@email.com"),closeSoftKeyboard());
        onView(withId(R.id.edittext_passwordEmployer)).perform(typeText("fail"),closeSoftKeyboard());
        onView(withId(R.id.edittext_usernameEmployer)).perform(typeText("testusername"),closeSoftKeyboard());
        onView(withId(R.id.edittext_monthEmployer)).perform(typeText("12"),closeSoftKeyboard());
        onView(withId(R.id.edittext_dayEmployer)).perform(typeText("01"),closeSoftKeyboard());
        onView(withId(R.id.edittext_yearEmployer)).perform(typeText("1950"),closeSoftKeyboard());
        onView(withId(R.id.button_signUpEmployer)).perform(click());
        onView(withId(R.id.passwordError2)).check(matches(isDisplayed()));
    }
}
