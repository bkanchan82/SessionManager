package architech.android.com.sessionmanager.model;

import architech.android.com.sessionmanager.model.network.Credential;
import architech.android.com.sessionmanager.model.network.LoginResponse;
import retrofit2.Call;

public interface SessionRepository {

    boolean isUserLogin();
    Call<LoginResponse> requestLogin(Credential loginData);
    void saveCredential(Credential credential);
    void saveToken(String token);
    Credential getCredential();
}
