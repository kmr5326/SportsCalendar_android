package SportsCalendar;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SportsCalendar {
    @GET("/kleagueschedule/{this_id}")
    Call<KleagueSchedule> getKleagueData(@Path("this_id") String id);

    @GET("/kboschedule/{this_id}")
    Call<KboSchedule> getKboData(@Path("this_id") String id);

    @GET("/kborank")
    Call<List<KboRank>> getKboRank();

    @GET("/kleaguerank")
    Call<List<KleagueRank>> getBaseballRank();

    @POST("/rest-auth/signup/")
    Call<RegisterResponse> signUp(@Body RegisterRequest registerRequest);

    @POST("/rest-auth/login/")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    //@POST("/board/")
    //Call<Write> writePost(@Body Write write);

    @GET("/board/")
    Call<List<Board>> getBoardList();

    @POST("/board/")
    Call<WriteResponse> writePost(@Body Write write);

}
