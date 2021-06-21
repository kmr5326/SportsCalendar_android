package SportsCalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import SportsCalendar.R;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//https://everyshare.tistory.com/10
//editText 텍스트 변경 시 이벤트 처리가 필요함. 아이디, 이메일 버튼을 누르고 나서 다시 손대면 안되니깐.

public class JoinActivity extends AppCompatActivity {

    private Button join_button, delete_button, check_button_email, check_button_id;
    private EditText join_email,join_id, join_password, join_pwck;
    private String email, id, password, pwck;
    boolean email_verification = false;
    boolean id_verification = false;
    SportsCalendar sportsCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);


        join_email = findViewById(R.id.join_email);
        join_id = findViewById(R.id.join_id);
        join_password = findViewById(R.id.join_password);
        join_pwck = findViewById(R.id.join_pwck);

        join_button = findViewById(R.id.join_button);
        delete_button = findViewById(R.id.delete_button);
        check_button_id = findViewById(R.id.check_button_id);
        check_button_email = findViewById(R.id.check_button_email);

        Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.219.101:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sportsCalendar = retrofit.create(SportsCalendar.class);


        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        check_button_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String join_id를 보내서 디비에 해당 아이디가 있는지를 체크.
                email = join_email.getText().toString();

                if(email.length() == 0){
                    Toast.makeText(getApplicationContext(), "이메일을 입력하세요.", Toast.LENGTH_SHORT).show();
                } else{
                    //이메일 버튼이랑 아이디 버튼을 눌러서 중복 아이디 없다라고 나오면 --> true, false 값 보내주기.
                    check_email_duplicate(email);
                }

            }
        });

        check_button_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = join_id.getText().toString();

                if(id.length() == 0){
                    Toast.makeText(getApplicationContext(), "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else{
                    //이메일 버튼이랑 아이디 버튼을 눌러서 중복 아이디 없다라고 나오면 --> true, false 값 보내주기.
                    check_id_duplicate(id);
                }

            }
        });

        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email_verification && id_verification){//둘다 확인버튼이 제대로 된 경우
                    email = join_email.getText().toString();
                    id = join_id.getText().toString();
                    password = join_password.getText().toString();
                    pwck = join_pwck.getText().toString();
                    if(email.length() == 0 || id.length() == 0 || password.length() == 0 || pwck.length() == 0 ){
                        Toast.makeText(getApplicationContext(), "모든 항목을 다 입력하세요.", Toast.LENGTH_SHORT).show();
                    } else{
                        //이메일 버튼이랑 아이디 버튼을 눌러서 중복 아이디 없다라고 나오면 --> true, false 값 보내주기.
                        registerUser(email, id, password, pwck);
                    }

                } else if(!email_verification){
                    Toast.makeText(JoinActivity.this, "이메일 확인을 해주세요.", Toast.LENGTH_SHORT).show();

                } else if(!id_verification){
                    Toast.makeText(JoinActivity.this, "아이디 확인을 해주세요.", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void check_id_duplicate(String id) {
        Call<RegisterResponse> call = sportsCalendar.signUp(new RegisterRequest(null, id, null, null));//이거 nullable 처리해주기.
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                try {
                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                    if (!jObjError.has("username") ) {
                        Toast.makeText(JoinActivity.this, "사용가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                        id_verification = true;

                    } else if (jObjError.getString("username").equals("[\"A user with that username already exists.\"]")) {
                        Toast.makeText(JoinActivity.this, "아이디가 중복되었습니다.", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(JoinActivity.this, "관리자에게 문의하십시오.", Toast.LENGTH_SHORT).show();
                        Log.d("아이디 확인 에러",jObjError.getString("username"));

                    }
                } catch (Exception e) {
                    Toast.makeText(JoinActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("errorbody2", "check_id_duplicate, catch문으로 들어옴.");
                }

            }
            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(JoinActivity.this, "연결 실패", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void check_email_duplicate(String email){
        Call<RegisterResponse> call = sportsCalendar.signUp(new RegisterRequest(email, null, null, null));
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                try {
                    JSONObject jObjError = new JSONObject(response.errorBody().string());

                    if (!jObjError.has("email") ){
                        Toast.makeText(JoinActivity.this, "사용가능한 이메일입니다.", Toast.LENGTH_SHORT).show();
                        email_verification = true;

                    }else if (jObjError.getString("email").equals("[\"Enter a valid email address.\"]")){
                        Toast.makeText(JoinActivity.this, "이메일 양식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();

                    } else if(jObjError.getString("email").equals("[\"A user is already registered with this e-mail address.\"]")){
                        Toast.makeText(JoinActivity.this, "이메일이 중복되었습니다.", Toast.LENGTH_SHORT).show();

                    } else{
                        Toast.makeText(JoinActivity.this, "관리자에게 문의하십시오.", Toast.LENGTH_SHORT).show();
                        Log.d("이메일 확인 에러",jObjError.getString("email"));
                    }

                } catch (Exception e) {
                    Toast.makeText(JoinActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("errorbody2","check_email_duplicate, catch문으로 들어옴.");

                }




            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(JoinActivity.this, "연결 실패", Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void registerUser(String email, String id, String password, String pwck){
        //Toast.makeText(getApplicationContext(), "레지스터 들어옴.", Toast.LENGTH_SHORT).show();

        Call<RegisterResponse> call = sportsCalendar.signUp(new RegisterRequest(email, id, password, pwck));
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        if (jObjError.has("password1") ) {
                            Toast.makeText(JoinActivity.this, jObjError.getString("password1"), Toast.LENGTH_SHORT).show();
                        } else if (jObjError.has("non_field_errors")){
                            Toast.makeText(JoinActivity.this, jObjError.getString("non_field_errors"), Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(),"성공적으로 가입되었습니다.",Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });


    }
}