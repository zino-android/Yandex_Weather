package com.chichkanov.yandex_weather.ui.main;


import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.chichkanov.yandex_weather.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        onView(withId(R.id.nav_change_city)).check(matches(isDisplayed())).perform(click());
    }

    @Test
    public void checkOpenAboutFragment() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText(R.string.menu_about)).perform(click());
        onView(withId(R.id.tv_about_task)).check(matches(isDisplayed()));
    }

    @Test
    public void testOpenChangeCityFragmentAndChangeCity() throws Exception {
        onView(withId(R.id.nav_change_city))
                .perform(click());
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.iv_clear)).perform(click());
        onView(withId(R.id.et_city_name)).perform(typeText("Moscow"));
        TimeUnit.SECONDS.sleep(2);
        onView(withId(R.id.rv_suggestions))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.tv_weather_city)).check(matches(withText("Moskva, Москва, Россия")));
    }

    @Test
    public void checkOpenSettingsFragment() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText(R.string.menu_settings)).perform(click());
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.list),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(android.R.id.text1), withText(activityTestRule.getActivity().getResources().getStringArray(R.array.refresh_entries)[1]),
                        childAtPosition(
                                allOf(withId(R.id.select_dialog_listview),
                                        withParent(withId(R.id.contentPanel))),
                                1),
                        isDisplayed()));
        appCompatCheckedTextView2.perform(click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.list),
                        withParent(withClassName(is("android.widget.FrameLayout"))),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatCheckedTextView3 = onView(
                allOf(withId(android.R.id.text1), withText(activityTestRule.getActivity().getResources().getStringArray(R.array.refresh_entries)[4]),
                        childAtPosition(
                                allOf(withId(R.id.select_dialog_listview),
                                        withParent(withId(R.id.contentPanel))),
                                4),
                        isDisplayed()));
        appCompatCheckedTextView3.perform(click());

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.list),
                        withParent(withClassName(is("android.widget.FrameLayout"))),
                        isDisplayed()));
        recyclerView3.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatCheckedTextView4 = onView(
                allOf(withId(android.R.id.text1), withText(activityTestRule.getActivity().getResources().getStringArray(R.array.refresh_entries)[1]),
                        childAtPosition(
                                allOf(withId(R.id.select_dialog_listview),
                                        withParent(withId(R.id.contentPanel))),
                                1),
                        isDisplayed()));
        appCompatCheckedTextView4.perform(click());
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
