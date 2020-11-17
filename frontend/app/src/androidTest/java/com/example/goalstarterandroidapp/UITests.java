package com.example.goalstarterandroidapp;


import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.volley.toolbox.StringRequest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
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

    private final static String INTENTINFO = "{\"method\":\"Post\",\"idToken\":\"eyJhbGciOiJSUzI1NiIsImtpZCI6ImQ5NDZiMTM3NzM3Yjk3MzczOGU1Mjg2YzIwOGI2NmU3YTM5ZWU3YzEiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI1MzQ4OTU1MzgxNTctc3UxMG80a2gyZ2N0OWVsZ2FhZmpnOW1uOTVmNWhsbW4uYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI1MzQ4OTU1MzgxNTctMTdvMjJyM3RxN2ZuNmc2cG5ob2UwcnBsNHFza3E1bmcuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTQ5NjI1NTQ3NDQ5MDcyNjk3MTEiLCJlbWFpbCI6ImFsYW4uc2h1eWFvd2VuQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiU2h1eWFvIHdlbiIsInBpY3R1cmUiOiJodHRwczovL2xoNC5nb29nbGV1c2VyY29udGVudC5jb20vLW1IVHhRU0M3TkpzL0FBQUFBQUFBQUFJL0FBQUFBQUFBQUFBL0FNWnV1Y2xGQ0xLRDFTQXdwX25KTHM2MTBaY3BHemhGWWcvczk2LWMvcGhvdG8uanBnIiwiZ2l2ZW5fbmFtZSI6IlNodXlhbyIsImZhbWlseV9uYW1lIjoid2VuIiwibG9jYWxlIjoiZW4tR0IiLCJpYXQiOjE2MDU1ODA0MjUsImV4cCI6MTYwNTU4NDAyNX0.DpDY8ILR2MU2yLe5PBFIk6gYQNhZkfLda31JXfMDv0gow0eYIuTf3N9WDXLr-Grj1WJArgUDr8Zqdzll_xdc_VV-EBczXjsMaDIU3_HLiHsSB2aHziVl5w9ivkmQvvBJQdOyNAMKrlamEHUzSl1uey3vX1mPjr1kjJ9xaakP4T_lqMVy1TjAuDtI-BFgx_YMCl9xJKybeCuPtCyIJ7SlXmU2uGEXT0xO4fphxxavYOQx-5jkgkg8BCxfAhZaADrPdgCSIRgpp3xyDbOtXFZvUEU5O26lmqFkrCchoxyAfEyiQHL72OhtAg-WUYmu4mgX4vBuTxHtni1_f8AOX9HOBw\",\"userid\":\"114962554744907269711\",\"name\":\"Shuyao wen\",\"email\":\"alan.shuyaowen@gmail.com\"}";
    static Intent intent = new Intent(ApplicationProvider.getApplicationContext(), HostActivity.class);
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
                allOf(withId(R.id.navigation_profile), withContentDescription("Profile"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView3.perform(click());

        ViewInteraction viewGroup = onView(
                allOf(withId(R.id.frameLayout),
                        withParent(allOf(withId(R.id.nav_host_fragment),
                                withParent(withId(R.id.container)))),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));
    }

    @Test
    public void createGoalActivityTest() {

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
