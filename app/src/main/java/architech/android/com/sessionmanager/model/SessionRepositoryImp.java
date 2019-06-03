package architech.android.com.sessionmanager.model;

import architech.android.com.sessionmanager.AppController;
import architech.android.com.sessionmanager.R;
import architech.android.com.sessionmanager.model.data.SharedPreferenceHelper;
import architech.android.com.sessionmanager.model.network.Credential;
import architech.android.com.sessionmanager.model.network.LoginNetworkDataSource;
import architech.android.com.sessionmanager.model.network.LoginResponse;
import retrofit2.Call;

public class SessionRepositoryImp implements SessionRepository {

    private static final Object LOCK = new Object();
    private static SessionRepositoryImp sInstance;

    private SharedPreferenceHelper mPreferenceHelper;
    private LoginNetworkDataSource mNetworkDataSource;

    private SessionRepositoryImp(SharedPreferenceHelper sharedPreferenceHelper, LoginNetworkDataSource networkDataSource){
        mNetworkDataSource = networkDataSource;
        mPreferenceHelper = sharedPreferenceHelper;
    }

    public static synchronized SessionRepositoryImp getInstance(SharedPreferenceHelper sharedPreferenceHelper, LoginNetworkDataSource networkDataSource){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new SessionRepositoryImp(sharedPreferenceHelper,networkDataSource);
            }
        }
        return sInstance;
    }



    @Override
    public boolean isUserLogin() {
        String token = mPreferenceHelper.getSharedPreferenceString(
                AppController.getContext().getResources().getString(R.string.jwt_key),
                null);
        if(token!= null && token.length()>0){
            return true;
        }
        return false;
    }

    @Override
    public Call<LoginResponse> requestLogin(Credential loginData){
        return mNetworkDataSource.sendLoginRequest(loginData);
    }

    @Override
    public void saveCredential(Credential credential) {
        mPreferenceHelper.setSharedPreferenceString(
                AppController.getContext().getResources().getString(R.string.email_key),
                credential.getEmail()
        );
        mPreferenceHelper.setSharedPreferenceString(
                AppController.getContext().getResources().getString(R.string.password_key),
                credential.getPassword()
        );
    }

    @Override
    public void saveToken(String token) {
        mPreferenceHelper.setSharedPreferenceString(
                AppController.getContext().getResources().getString(R.string.jwt_key),
                token
        );
    }

    @Override
    public Credential getCredential() {
        String email = mPreferenceHelper.getSharedPreferenceString(
                AppController.getContext().getResources().getString(R.string.email_key),
                "");
        String password = mPreferenceHelper.getSharedPreferenceString(
                AppController.getContext().getResources().getString(R.string.password_key),
                "");

        return new Credential(email,password);
    }
}
