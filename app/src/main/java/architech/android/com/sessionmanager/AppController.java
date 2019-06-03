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
        initializeWork();
    }

    private void initializeWork() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                Constraints constraints = new Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build();

                PeriodicWorkRequest saveRequest =
                        new PeriodicWorkRequest.Builder(RefreshTokenWork.class, 15, TimeUnit.MINUTES)
                                .setConstraints(constraints)
                                .build();

                WorkManager.getInstance()
                        .enqueue(saveRequest);
                return null;
            }
        }.execute();

    }

    public static Context getContext() {
        return mContext;
    }
}


