package architech.android.com.sessionmanager.utils;

import android.content.Context;

import architech.android.com.sessionmanager.model.SessionRepositoryImp;
import architech.android.com.sessionmanager.model.data.SharedPreferenceHelper;
import architech.android.com.sessionmanager.model.network.LoginNetworkDataSource;

public class InjectorUtils {

    public static SharedPreferenceHelper preferenceHelperProvider(Context ctx){
        return SharedPreferenceHelper.getInstance(ctx);
    }

    public static SessionRepositoryImp sessionRepositoryProvider(Context ctx) {
        return SessionRepositoryImp.getInstance(
                preferenceHelperProvider(ctx),
                LoginNetworkDataSource.getInstance());
    }
}
