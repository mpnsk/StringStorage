package com.github.mpnsk.stringstorage;

import android.support.test.espresso.ViewInteraction;
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
import static org.hamcrest.CoreMatchers.not;

public class EditQueryActivityTest {

    private final ViewInteraction onViewName;
    private final ViewInteraction onViewLocation;
    @Rule
    public ActivityTestRule<EditQueryActivity> mActivityRule = new ActivityTestRule<>(EditQueryActivity.class);

    public EditQueryActivityTest() {
        onViewName = onView(withId(R.id.edit_item_name));
        onViewLocation = onView(withId(R.id.edit_item_location));
    }

    @Test
    public void testThis() {
        createEntry("this", "that");
        createEntry("this2", "that2");
    }

    @Test
    public void testThat() {
        createEntry("this3", "that3");
        createEntry("this4", "that4");
    }

    @Test
    public void testCreate() {
        String itemName = "arbitrary name";
        String itemLocation = "item-location";
        createEntry(itemName, itemLocation);
        onData(anything()).inAdapterView(withId(R.id.listview))
                // list should be empty so we just take the first item
                .atPosition(0)
                .onChildView(withId(R.id.item_name))
                .check(matches(withText(itemName)))
                .check(matches(not(withText("other text"))));
    }

    private void createEntry(String name, String location) {
        onView(withId(R.id.edit_item_name))
                .perform(click(), typeText(name), closeSoftKeyboard());
        onView(withId(R.id.edit_item_location))
                .perform(typeText(location));
        onView(withId(R.id.button_save))
                .perform(click());

    }

    @Test
    public void testAutoComplete() throws Exception {
        String testName = "item-name";
        String testNameBeginsWith = "item-";
        String testLocation = "item-location";
        createEntry(testName, testLocation);

        // clear the textboxes to test autocomplete
        onView(withId(R.id.button_clear)).perform(click());

        onViewName.perform(click(), typeText(testNameBeginsWith), closeSoftKeyboard());
        onView(withText(testName)).inRoot(isPlatformPopup()).perform(click());
        onViewName.check(matches(withText(testName)));

        onData(anything()).inAdapterView(withId(R.id.listview)).atPosition(0).perform(click());
        onViewLocation.check(matches(withText(testLocation)));
    }
}