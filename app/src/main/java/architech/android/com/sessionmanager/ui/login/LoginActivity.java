package architech.android.com.sessionmanager.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.test.espresso.IdlingResource;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import architech.android.com.sessionmanager.R;
import architech.android.com.sessionmanager.databinding.ActivityLoginBinding;
import architech.android.com.sessionmanager.idlingresource.SimpleIdlingResource;
import architech.android.com.sessionmanager.model.network.Credential;
import architech.android.com.sessionmanager.ui.dashboard.DashboardActivity;
import architech.android.com.sessionmanager.utils.InjectorUtils;
import architech.android.com.sessionmanager.work.RefreshTokenWork;

public class LoginActivity extends AppCompatActivity {

    //The login binding object that holds reference of all views in this activity
    private ActivityLoginBinding mBinding;

    //The view model class that handle data, that survive configuration changes
    private LoginViewModel mViewModel;

    // The Idling Resource which will be null in production.
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        LoginViewModelFactory factory = new LoginViewModelFactory(InjectorUtils.sessionRepositoryProvider(this));
        mViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);

        RefreshTokenWork.initializeWork();

        //Observe if user is logged in or not and navigate accordingly
        mViewModel.isLogin().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLogin) {
                mBinding.loginBt.setEnabled(true);
                if(isLogin){
                    navigateToDashboard();
                }
            }
        });

        //Observer any error and toast it
        mViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                mBinding.loginBt.setEnabled(true);
                Toast.makeText(LoginActivity.this,errorMessage,Toast.LENGTH_SHORT).show();
            }
        });

        mBinding.loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = mBinding.emailEdt.getText().toString();
                String passwordString = mBinding.passwordEdt.getText().toString();
                if(mViewModel.isValidEmail(emailString)
                        && mViewModel.isValidPassword(passwordString)){
                    mBinding.loginBt.setEnabled(false);
                    Credential loginData = new Credential(emailString,passwordString);
                    mViewModel.requestLogin(loginData,mIdlingResource);
                }else{
                    Toast.makeText(LoginActivity.this,"Email or password is invalid",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    private void navigateToDashboard(){
        Intent intent = new Intent(this, DashboardActivity.class);
        finish();
        startActivity(intent);
    }
}
