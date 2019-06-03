package architech.android.com.sessionmanager.model.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApiService {

    //TODO change api path before testing
    @POST("api/path")
    Call<LoginResponse> loginUser(@Body Credential user);

}
