package com.example.goalstarterandroidapp;


import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
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
public class UITests {

    private final static String INTENTINFO = "{\"method\":\"Post\",\"idToken\":\"\",\"userid\":\"404\",\"name\":\"GoalStarter Test\",\"email\":\"goalstarter@gmail.com\"}";
    private static Intent intent = new Intent(ApplicationProvider.getApplicationContext(), HostActivity.class);
    static{
        intent.putExtra("userInfo", INTENTINFO);
    }

    @Rule
    public ActivityScenarioRule<HostActivity> scenarioRule = new ActivityScenarioRule<HostActivity>(intent);

    @Test
    public void bottomNavTest() {

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_my_goals), withContentDescription("My Goals"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler_view_my_goals),
                        withParent(withParent(withId(R.id.nav_host_fragment))),
                        isDisplayed()));
        recyclerView.check(matches(isDisplayed()));

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.navigation_feed), withContentDescription("Feed"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recycler_view_feed_fragment),
                        withParent(withParent(withId(R.id.nav_host_fragment))),
                        isDisplayed()));
        recyclerView2.check(matches(isDisplayed()));

        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.navigation_friends), withContentDescription("My Friends"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView3.perform(click());

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.recycler_view_friends_fragment),
                        withParent(withParent(withId(R.id.nav_host_fragment))),
                        isDisplayed()));
        recyclerView3.check(matches(isDisplayed()));
    }

    @Test
    public void goalDetailActivityTest() {

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler_view_feed_fragment),
                        withParent(withParent(withId(R.id.nav_host_fragment))),
                        isDisplayed()));
        recyclerView.check(matches(isDisplayed()));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recycler_view_feed_fragment),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction viewGroup = onView(
                allOf(withParent(allOf(withId(android.R.id.content),
                        withParent(withId(R.id.action_bar_root)))),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));
    }

    @Test
    public void logoutActivityTest() {

        ViewInteraction textView = onView(
                allOf(withText("Feed"),
                        withParent(allOf(withId(R.id.toolbar_host),
                                withParent(withId(R.id.app_bar_layout_host)))),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar_host),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.title), withText("Log out"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction imageView = onView(
                allOf(withId(R.id.imageView),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction button = onView(
                allOf(withText("Sign in"),
                        withParent(allOf(withId(R.id.sign_in_button),
                                withParent(IsInstanceOf.<View>instanceOf(ViewGroup.class)))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

    }

    @Test
    public void createGoalTest() {

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_my_goals), withContentDescription("My Goals"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler_view_my_goals),
                        withParent(withParent(withId(R.id.nav_host_fragment))),
                        isDisplayed()));
        recyclerView.check(matches(isDisplayed()));

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
                allOf(withId(R.id.edit_text_goal_title)));
        textInputEditText.perform(scrollTo(), replaceText("Test Goal"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.edit_text_goal_content)));
        textInputEditText2.perform(scrollTo(), replaceText("Test Goal Content"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.edit_text_goal_tag)));
        textInputEditText3.perform(scrollTo(), replaceText("Test"), closeSoftKeyboard());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.edit_text_goal_milestone1)));
        textInputEditText4.perform(scrollTo(), replaceText("Test Milestone 1"), closeSoftKeyboard());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.milestone1_date)));
        materialTextView.perform(scrollTo(), click());

        ViewInteraction materialButton = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        materialButton.perform(scrollTo(), click());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.edit_text_goal_milestone2)));
        textInputEditText5.perform(scrollTo(), replaceText("Test Milestone 2"), closeSoftKeyboard());

        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.milestone2_date)));
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
                allOf(withId(R.id.edit_text_goal_milestone3)));
        textInputEditText6.perform(scrollTo(), replaceText("Test Milestone 3"), closeSoftKeyboard());

        ViewInteraction materialTextView3 = onView(
                allOf(withId(R.id.milestone3_date)));
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
                allOf(withId(R.id.goal_date)));
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

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recycler_view_my_goals),
                        isDisplayed()));
        recyclerView2.check(matches(isDisplayed()));

    }

    @Test
    public void sendFriendReqTest() {

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
                        isDisplayed()));
        textInputEditText.perform(replaceText("goalstarter@gmail.com"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.button_send_request), withText("Send Friend Request")));
        materialButton.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withText("Add Friends"),
                        withParent(allOf(withId(R.id.toolbar_add_friends),
                                withParent(withId(R.id.appBarLayout)))),
                        isDisplayed()));
        textView3.check(matches(withText("Add Friends")));
    }

    @Test
    public void myFriendReqActivityTest() {
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
                allOf(withId(R.id.fab_friend_req_button), withText("Check Friend Requests"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                2),
                        isDisplayed()));
        extendedFloatingActionButton.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withText("My Friend Requests"),
                        withParent(allOf(withId(R.id.toolbar_create_goal),
                                withParent(withId(R.id.appBarLayout)))),
                        isDisplayed()));
        textView2.check(matches(withText("My Friend Requests")));
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
