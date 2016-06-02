package com.github.mpnsk.stringstorage;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;

import com.github.mpnsk.stringstorage.di.TestComponent;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.realm.Realm;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;

public class EditQueryActivityTest {

    @Rule
    public ActivityTestRule<EditQueryActivity> mActivityRule = new ActivityTestRule<EditQueryActivity>(EditQueryActivity.class);
    Realm realm;
    private TestComponent component;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testShowPopup() throws Exception {

//        onData(anything()).inAdapterView(withId(R.id.listview)).atPosition(0).perform(click());
//        ViewInteraction var = onView(withText(containsString("edit ")));
//        String interesting = var.toString();
        ViewInteraction itemName = onView(withId(R.id.edit_item_name));
        itemName.check(matches(isDisplayed()));
        itemName.perform(click(), typeText("edited-item-name"), click());
        ViewInteraction itemLocation = onView(withId(R.id.edit_item_location));
        itemLocation.check(matches(isDisplayed()));
        itemLocation.perform(click(), typeText("edited-item-location"), click());
        onView(withId(R.id.save_button)).check(matches(isDisplayed())).perform(click());
//        onData(withText(containsString("xxx"))).perform(click());

        onData(anything()).inAdapterView(withId(R.id.listview)).atPosition(0).perform(click());
        //

    }
}