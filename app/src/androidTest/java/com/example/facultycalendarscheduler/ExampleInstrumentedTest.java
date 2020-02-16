package com.example.facultycalendarscheduler;

import  android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasPackage;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.junit.Assert.*;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.facultycalendarscheduler", appContext.getPackageName());
    }
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void loginClickedSuccess() {
        onView(withId(R.id.username))
                .perform(typeText("soft@gmail.com"));
        onView(withId(R.id.password))
                .perform(typeText("softENGINE"));
    }
//    @Rule
//    public IntentsTestRule<MainActivity> intentsTestRule =
//            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void shouldShowToastUsernameEmpty() {
        onView(withId(R.id.username))
                .perform(typeText("soft@gmail.com"));
        onView(withId(R.id.password))
                .perform(typeText("softENGINE"));
        onView(withId(R.id.loginv)).perform(click(),closeSoftKeyboard());
        SystemClock.sleep(3000);
        onView(withId(R.id.imageButton)).check(matches(isDisplayed()));
//        SystemClock.sleep(3000);
//        Intents.init();
//        Log.v("san",Userlogin.class.getName(),null);
//        intended(hasPackage(Userlogin.class.getName()));
    }

}
