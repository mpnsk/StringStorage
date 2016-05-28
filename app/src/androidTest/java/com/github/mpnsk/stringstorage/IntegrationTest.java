package com.github.mpnsk.stringstorage;

import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class IntegrationTest {

    @Rule
    public ActivityTestRule<EditQueryActivity> mActivityRule = new ActivityTestRule<>(EditQueryActivity.class);
    private String mStringToBeTyped;

    @Before
    public void setUp() throws Exception {
        mStringToBeTyped = "TestString";
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testOnCreate() throws Exception {
        onView(withId(R.id.item_name))
                .perform(typeText(mStringToBeTyped));
        onView(withId(R.id.item_location))
                .perform(typeText(mStringToBeTyped + "loc"));
        onView(withId(R.id.save_button))
                .perform(click());
    }
}