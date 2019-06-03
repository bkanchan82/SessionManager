package architech.android.com.sessionmanager.model.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginNetworkDataSource {

    private static final String BASE_URL = "https://munkapp.herokuapp.com/";

    private Gson gson = new GsonBuilder()
            .create();

    private Retrofit mRetrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build();
    private static final Object LOCK = new Object();
    private static LoginNetworkDataSource sInstance;

    private LoginNetworkDataSource(){
    }


    public static synchronized LoginNetworkDataSource getInstance(){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new LoginNetworkDataSource();
            }
        }
        return sInstance;
    }

    /**
     * Sends Login request to the server
     * @param loginData contains data that will be posted to the server
     * @return An observable object and run in different thread, called Retrofit.Call of type LoginResponse
     */
    public Call<LoginResponse> sendLoginRequest(Credential loginData){
        LoginApiService loginApiService = mRetrofit.create(LoginApiService.class);
        return loginApiService.loginUser(loginData);
    }

}
