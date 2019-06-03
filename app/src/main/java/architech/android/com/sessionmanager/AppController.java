package architech.android.com.sessionmanager;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import architech.android.com.sessionmanager.work.RefreshTokenWork;

public class AppController extends Application {


    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
    public static Context getContext() {
        return mContext;
    }
}


