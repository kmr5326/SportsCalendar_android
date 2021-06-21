package SportsCalendar;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    //null이 가능하다 처리를 해줘야ㅕ함.
    @SerializedName("email")
    public String error_email;//이메일에서 에러가 생긴 경우

    @SerializedName("non_field_errors")
    public String non_field_errors;//비밀번호 체크하는데 달랐다

    @SerializedName("username")
    public String id_error;//아이디가 에러있는 경우.


    public String getError_email() {
        return error_email;
    }

    public void setError_email(String error_email) {
        this.error_email = error_email;
    }

    public String getNon_field_errors() {
        return non_field_errors;
    }

    public void setNon_field_errors(String non_field_errors) {
        this.non_field_errors = non_field_errors;
    }

    public String getId_error() {
        return id_error;
    }

    public void setId_error(String id_error) {
        this.id_error = id_error;
    }
}
