package com.github.mpnsk.stringstorage;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

public class EditQueryActivityTest {

    @Rule
    public ActivityTestRule<EditQueryActivity> mActivityRule = new ActivityTestRule<EditQueryActivity>(EditQueryActivity.class);

    @Test
    public void testCreate() {
        createEntry("item-name", "item-location");
        onView(withId(R.id.edit_item_name));
    }

    private void createEntry(String name, String location) {
        onView(withId(R.id.edit_item_name))
                .perform(click(), typeText(name), closeSoftKeyboard());
        onView(withId(R.id.edit_item_location))
                .perform(typeText(location));
        onView(withId(R.id.button_save));

    }

    @Test
    public void testShowPopup() throws Exception {
        createEntry("item-name", "item-location");
        onView(withId(R.id.button_clear))
                .perform(click());

        onView(withId(R.id.edit_item_name)).perform(click(), typeText("item"), closeSoftKeyboard())


                .inRoot(isPlatformPopup())
//                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .perform(click());
        onView(withId(R.id.edit_item_location))
                .check(matches(withText("item-location")));
//        onData(allOf(instanceOf(String.class), is(containsString("edi"))));

        onView(withId(R.id.edit_item_location)).perform(click(), typeText("location"));
        onData(anything()).inAdapterView(withId(R.id.listview)).atPosition(0).perform(click());

    }
}