package com.example.csci3130groupproject;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class PaymentNavigationTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.CAMERA");

    @Test
    public void paymentNavigationTest() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.button_login), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.edittext_username),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("employer12345"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.edittext_password),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("employer12345"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.button_finalLogin), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.button_viewMyTasksEmployer), withText("View Completed Tasks"),
                        childAtPosition(
                                allOf(withId(R.id.button_viewtasksEmployer),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction button = onView(
                allOf(withText("Task Title: test task123\nTask Description: tasky task\nTask Tags: Moving\nTask Payment: 20"),
                        childAtPosition(
                                allOf(withId(R.id.linearlayout_mytasksEmployer),
                                        childAtPosition(
                                                withId(R.id.scrollview_mytasksEmployer),
                                                0)),
                                1)));
        button.perform(scrollTo(), click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.button_approvepayment), withText("Approve Payment"),
                        childAtPosition(
                                allOf(withId(R.id.submitReviewCheckBox),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                6),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.editview_edtAmount),
                        childAtPosition(
                                allOf(withId(R.id.submitReviewCheckBox),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("20"), closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.button_approvepayment), withText("Send payment"),
                        childAtPosition(
                                allOf(withId(R.id.submitReviewCheckBox),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction linearLayout = onView(
                childAtPosition(
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0),
                        4));
        linearLayout.perform(scrollTo(), click());

        ViewInteraction button2 = onView(
                allOf(withText("Keyboard…"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        1),
                                0),
                        isDisplayed()));
        button2.perform(click());

        ViewInteraction editText = onView(
                childAtPosition(
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1),
                        1));
        editText.perform(scrollTo(), replaceText("4214 0204 3877 6926"), closeSoftKeyboard());

        ViewInteraction editText2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0),
                        1),
                        isDisplayed()));
        editText2.perform(replaceText("12/25"), closeSoftKeyboard());

        ViewInteraction editText3 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1),
                        1),
                        isDisplayed()));
        editText3.perform(replaceText("123"), closeSoftKeyboard());

        ViewInteraction button3 = onView(
                allOf(withText("Done"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1),
                                0),
                        isDisplayed()));
        button3.perform(click());

        ViewInteraction linearLayout2 = onView(
                childAtPosition(
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0),
                        8));
        linearLayout2.perform(scrollTo(), click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
