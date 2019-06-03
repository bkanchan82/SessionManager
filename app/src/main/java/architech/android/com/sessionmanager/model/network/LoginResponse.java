package architech.android.com.sessionmanager.model.network;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

       /*{
  "error_message": "",
  "token": "ksnfsaklnclkznc",
  "user_id": 1,
  "status": 0
}*/

    private String token;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("error_message")
    private String errorMessage;
    private int status;

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getStatus() {
        return status;
    }


}
