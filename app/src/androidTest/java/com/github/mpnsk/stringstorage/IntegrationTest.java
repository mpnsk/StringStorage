package com.github.mpnsk.stringstorage;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;

public class IntegrationTest {

    @Rule
    public ActivityTestRule<EditQueryActivity> mActivityRule = new ActivityTestRule<>(EditQueryActivity.class, true, false);
    @Inject
    Realm realm;
    private String mStringToBeTyped;

    @Before
    public void setUp() throws Exception {
        mStringToBeTyped = "TestString";
        RealmConfiguration myConfig = new RealmConfiguration.Builder(InstrumentationRegistry.getTargetContext())
                .name("myrealm.realm")
                .inMemory()
                .build();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreate() throws Exception {

        onView(withId(R.id.edit_item_name))
                .perform(typeText(mStringToBeTyped));
        onView(withId(R.id.edit_item_location))
                .perform(typeText(mStringToBeTyped + "loc"));
        onView(withId(R.id.save_button))
                .perform(click());
    }

    @Test
    public void testUpdate() {
        onData(anything()).inAdapterView(withId(R.id.listview)).atPosition(0);
    }

}