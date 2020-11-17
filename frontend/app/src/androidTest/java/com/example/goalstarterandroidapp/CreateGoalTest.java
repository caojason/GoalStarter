package com.example.goalstarterandroidapp;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CreateGoalTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void createGoalTest() {
        ViewInteraction gb = onView(
                allOf(withText("Sign in"),
                        childAtPosition(
                                allOf(withId(R.id.sign_in_button),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        gb.perform(click());

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_my_goals), withContentDescription("My Goals"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction extendedFloatingActionButton = onView(
                allOf(withId(R.id.fab_my_goal_fragment), withText("CREATE GOAL"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                1),
                        isDisplayed()));
        extendedFloatingActionButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.edit_text_goal_title),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout_goal_title),
                                        0),
                                0)));
        textInputEditText.perform(scrollTo(), replaceText("auto test"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.edit_text_goal_content),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout_goal_content),
                                        0),
                                0)));
        textInputEditText2.perform(scrollTo(), replaceText("test content"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.edit_text_goal_tag),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout_goal_tag),
                                        0),
                                0)));
        textInputEditText3.perform(scrollTo(), replaceText("testing"), closeSoftKeyboard());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.edit_text_goal_milestone1),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout_goal_milestone1),
                                        0),
                                0)));
        textInputEditText4.perform(scrollTo(), replaceText("this is milestone 1"), closeSoftKeyboard());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.milestone1_date),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                4)));
        materialTextView.perform(scrollTo(), click());

        ViewInteraction materialButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton.perform(scrollTo(), click());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.edit_text_goal_milestone2),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout_goal_milestone2),
                                        0),
                                0)));
        textInputEditText5.perform(scrollTo(), replaceText("milestone"), closeSoftKeyboard());

        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.milestone2_date),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                6)));
        materialTextView2.perform(scrollTo(), click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.edit_text_goal_milestone3),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout_goal_milestone3),
                                        0),
                                0)));
        textInputEditText6.perform(scrollTo(), replaceText("milesonte 3"), closeSoftKeyboard());

        ViewInteraction materialTextView3 = onView(
                allOf(withId(R.id.milestone3_date),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                8)));
        materialTextView3.perform(scrollTo(), click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton3.perform(scrollTo(), click());

        ViewInteraction materialTextView4 = onView(
                allOf(withId(R.id.goal_date),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                9)));
        materialTextView4.perform(scrollTo(), click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton4.perform(scrollTo(), click());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.button_create_goal), withText("create goal"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                10)));
        materialButton5.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(withText("My Goals"),
                        withParent(allOf(withId(R.id.toolbar_host),
                                withParent(withId(R.id.app_bar_layout_host)))),
                        isDisplayed()));
        textView.check(matches(withText("My Goals")));
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
