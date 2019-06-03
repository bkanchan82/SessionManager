package architech.android.com.sessionmanager.model.network;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

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

    /*{
  "error_message": "User does not exist",
  "token": "ksnfsaklnclkznc",
  "user_id": 1,
  "status": 101
}*/
}
