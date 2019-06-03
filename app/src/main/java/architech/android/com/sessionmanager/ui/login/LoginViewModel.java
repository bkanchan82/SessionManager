package architech.android.com.sessionmanager.ui.login;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import architech.android.com.sessionmanager.idlingresource.SimpleIdlingResource;
import architech.android.com.sessionmanager.model.SessionRepository;
import architech.android.com.sessionmanager.model.network.Credential;
import architech.android.com.sessionmanager.model.network.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private final SessionRepository mSessionRepository;

    //Holds the login state of the user
    private MutableLiveData<Boolean> isLogin = new MutableLiveData<>();
    //Holds error message if occurs
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LoginViewModel(SessionRepository sessionRepository) {
        mSessionRepository = sessionRepository;

    }

    public LiveData<Boolean> isLogin(){
        isLogin.setValue(mSessionRepository.isUserLogin());
        return isLogin;
    }

    public LiveData<String> getErrorMessage(){
        return errorMessage;
    }



    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public boolean isValidEmail(String email) {
        if(email == null) {
            return false;
        }
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * method is used to check if password is valid or not
     *
     * @param password password string
     * @return boolean true for valid false for invalid
     */
    public boolean isValidPassword(String password) {
        if(password != null && password.length()>0) {
            return true;
        }else{
            return false;
        }
    }


    public void requestLogin(final Credential credential,@Nullable final SimpleIdlingResource idlingResource) {

        // The IdlingResource is null in production.
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        mSessionRepository.requestLogin(credential).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if(loginResponse.getStatus() == 101){
                    mSessionRepository.saveCredential(credential);

                    Log.d(LoginViewModel.class.getSimpleName(),"TOKEN "+loginResponse.getToken());
                    mSessionRepository.saveToken(loginResponse.getToken());
                    isLogin.setValue(true);
                }else{
                    errorMessage.setValue(loginResponse.getErrorMessage());
                }

                if (idlingResource != null) {
                    idlingResource.setIdleState(true);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                errorMessage.setValue("Not able to connect with server");
                if (idlingResource != null) {
                    idlingResource.setIdleState(true);
                }
            }
        });
    }
}
