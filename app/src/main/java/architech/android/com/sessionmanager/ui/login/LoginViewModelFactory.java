package architech.android.com.sessionmanager.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import architech.android.com.sessionmanager.model.SessionRepository;
import architech.android.com.sessionmanager.ui.login.LoginViewModel;


public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private final SessionRepository mSessionRepository;

    public LoginViewModelFactory(SessionRepository sessionRepository){
        mSessionRepository = sessionRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(mSessionRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
