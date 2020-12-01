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
public class sendFriendReqTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void sendFriendReqTest() {
        ViewInteraction gc = onView(
                allOf(withText("Sign in"),
                        childAtPosition(
                                allOf(withId(R.id.sign_in_button),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        gc.perform(click());

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_friends), withContentDescription("My Friends"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction textView = onView(
                allOf(withText("My Friends"),
                        withParent(allOf(withId(R.id.toolbar_host),
                                withParent(withId(R.id.app_bar_layout_host)))),
                        isDisplayed()));
        textView.check(matches(withText("My Friends")));

        ViewInteraction extendedFloatingActionButton = onView(
                allOf(withId(R.id.fab_add_friends_button), withText("Add Friends"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                1),
                        isDisplayed()));
        extendedFloatingActionButton.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withText("Add Friends"),
                        withParent(allOf(withId(R.id.toolbar_add_friends),
                                withParent(withId(R.id.appBarLayout)))),
                        isDisplayed()));
        textView2.check(matches(withText("Add Friends")));

        ViewInteraction button = onView(
                allOf(withId(R.id.button_send_request), withText("SEND FRIEND REQUEST"),
                        withParent(withParent(withId(R.id.cardView))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.edit_text_friend_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout_friend_email),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("sampleEmail@gmail.com"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.button_send_request), withText("Send Friend Request"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.cardView),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withText("Add Friends"),
                        withParent(allOf(withId(R.id.toolbar_add_friends),
                                withParent(withId(R.id.appBarLayout)))),
                        isDisplayed()));
        textView3.check(matches(withText("Add Friends")));
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
