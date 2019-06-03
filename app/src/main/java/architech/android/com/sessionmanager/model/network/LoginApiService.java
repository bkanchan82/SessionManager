package architech.android.com.sessionmanager.model.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApiService {

    @POST("test/user")
    Call<LoginResponse> loginUser(@Body Credential user);

}
