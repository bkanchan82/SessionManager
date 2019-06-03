package architech.android.com.sessionmanager.ui.login;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import architech.android.com.sessionmanager.R;
import architech.android.com.sessionmanager.ui.dashboard.DashboardActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    private static final String EMAIL_TO_BE_TYPED = "valid_email";
    private static final String PASSWORD_TO_BE_TYPED = "valid_password";

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        ActivityScenario activityScenario = ActivityScenario.launch(LoginActivity.class);
        activityScenario.onActivity(new ActivityScenario.ActivityAction<LoginActivity>() {
            @Override
            public void perform(LoginActivity activity) {
                mIdlingResource = activity.getIdlingResource();
                // To prove that the test fails, omit this call:
                IdlingRegistry.getInstance().register(mIdlingResource);
            }
        });
    }

    @Test
    public void clickLogin_ToDashboard() {
        // Type text and then press the button.
        onView(withId(R.id.email_edt))
                .perform(typeText(EMAIL_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.password_edt))
                .perform(typeText(PASSWORD_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.login_bt)).perform(click());

        // Check that the activity is displayed
        intended(hasComponent(DashboardActivity.class.getName()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

}