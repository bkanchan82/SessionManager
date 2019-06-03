package architech.android.com.sessionmanager.ui;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import architech.android.com.sessionmanager.model.SessionRepository;
import architech.android.com.sessionmanager.ui.login.LoginViewModel;

import static org.junit.Assert.*;

public class LoginViewModelTest {

    private static final String VALID_EMAIL = "abc@gmail.com";
    private static final String INVALID_EMAIL = "abc.com";

    private static final String VALID_PASSWORD = "abcdefgh";
    private static final String INVALID_PASSWORD = "";

    @Mock
    private SessionRepository mSessionRepository;

    private LoginViewModel mViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mViewModel = new LoginViewModel(mSessionRepository);

    }

    @Test
    public void validEmail() {
        boolean isValid = mViewModel.isValidEmail(VALID_EMAIL);
        assertEquals(isValid, true);
    }

    @Test
    public void invalidEmail() {
        boolean isValid = mViewModel.isValidEmail(INVALID_EMAIL);
        assertNotEquals(isValid, true);
    }

    @Test
    public void validPassword() {
        boolean isValid = mViewModel.isValidPassword(VALID_PASSWORD);
        assertEquals(isValid, true);
    }

    @Test
    public void invalidPassword() {
        boolean isValid = mViewModel.isValidPassword(INVALID_PASSWORD);
        assertNotEquals(isValid, true);
    }

}