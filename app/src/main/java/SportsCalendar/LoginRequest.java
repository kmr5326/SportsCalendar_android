package SportsCalendar;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("username")
    private String userid;
    @SerializedName("email")
    private String useremail;
    @SerializedName("password")
    private String password;



    public LoginRequest(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }
}
