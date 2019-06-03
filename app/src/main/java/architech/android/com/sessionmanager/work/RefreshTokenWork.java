package architech.android.com.sessionmanager.work;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import architech.android.com.sessionmanager.AppController;
import architech.android.com.sessionmanager.R;
import architech.android.com.sessionmanager.model.data.SharedPreferenceHelper;
import architech.android.com.sessionmanager.model.network.Credential;
import architech.android.com.sessionmanager.model.network.LoginNetworkDataSource;
import architech.android.com.sessionmanager.model.network.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RefreshTokenWork extends Worker {

    public RefreshTokenWork(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        LoginNetworkDataSource networkDataSource = LoginNetworkDataSource.getInstance();
        final SharedPreferenceHelper mPreferenceHelper = SharedPreferenceHelper.getInstance(getApplicationContext());

        Log.d(RefreshTokenWork.class.getSimpleName(),"doWork");

        String token = mPreferenceHelper.getSharedPreferenceString(
                AppController.getContext().getResources().getString(R.string.jwt_key),
                null);

        if(token == null){
            return Result.success();
        }

        String email = mPreferenceHelper.getSharedPreferenceString(
                AppController.getContext().getResources().getString(R.string.email_key),
                "");
        String password = mPreferenceHelper.getSharedPreferenceString(
                AppController.getContext().getResources().getString(R.string.password_key),
                "");


        Credential credential = new Credential(email,password);


        networkDataSource.sendLoginRequest(credential).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if(loginResponse.getStatus() == 0) {
                    mPreferenceHelper.setSharedPreferenceString(
                            AppController.getContext().getResources().getString(R.string.password_key),
                            loginResponse.getToken()
                    );
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
        return Result.success();
    }

}
