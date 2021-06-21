package SportsCalendar;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;


public class RegisterRequest {
    @Nullable
    @SerializedName("email")
    private String useremail;

    @Nullable
    @SerializedName("username")
    private String userid;

    @Nullable
    @SerializedName("password1")
    private String password;

    @Nullable
    @SerializedName("password2")
    private String password_check;

    public RegisterRequest(String useremail, String userid, String password, String password_check) {
        this.userid = userid;
        this.useremail = useremail;
        this.password = password;
        this.password_check = password_check;
    }


}
