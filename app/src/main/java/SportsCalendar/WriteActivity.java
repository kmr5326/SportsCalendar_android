package SportsCalendar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WriteActivity extends AppCompatActivity {

    private Button write_button;
    private Button writeCancel_button;
    private EditText title,content;
    private String title_request,content_request;
    SportsCalendar sportsCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        write_button= findViewById(R.id.write_button);
        writeCancel_button= findViewById(R.id.writeCancel_button);
        title=findViewById(R.id.title);
        content=findViewById(R.id.content);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.219.101:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sportsCalendar = retrofit.create(SportsCalendar.class);

        write_button.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view){
            title_request = title.getText().toString();
            content_request = content.getText().toString();

            if(title_request.length()==0&&content_request.length()==0)
            {
                Toast.makeText(getApplicationContext(), "제목과 내용을 입력하세요.", Toast.LENGTH_SHORT).show();
            }
            else if(title_request.length()==0){
                Toast.makeText(getApplicationContext(), "제목을 입력하세요.", Toast.LENGTH_SHORT).show();
            }
            else if(content_request.length()==0){
                Toast.makeText(getApplicationContext(), "내용을 입력하세요.", Toast.LENGTH_SHORT).show();
            }
            else if(title_request.length()!=0&&content_request.length()!=0){
                write(title_request, content_request);

            }
        }
        });

        writeCancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //stopPlay(); //이 액티비티에서 종료되어야 하는 활동 종료시켜주는 함수
                //Toast.makeText(WriteActivity.this, "글쓰기 취소", Toast.LENGTH_SHORT).show();   //토스트 메시지
                  //인텐트 이동
                //finish();
            }
        });
    }

    public void write(String title_request,String content_request) {
        Call<WriteResponse> call = sportsCalendar.writePost(new Write(title_request, content_request));
        call.enqueue(new Callback<WriteResponse>() {
            @Override
            public void onResponse(Call<WriteResponse> call, Response<WriteResponse> response) {
                if(!response.isSuccessful())
                {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                    } catch (Exception e) {
                        Log.d("writeError", "글쓰기 실패");
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"글 등록 완료",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(WriteActivity.this, FreeBoardActivity.class); //지금 액티비티에서 다른 액티비티로 이동하는 인텐트 설정
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //인텐트 플래그 설정
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<WriteResponse> call, Throwable t) {
                Toast.makeText(WriteActivity.this, "연결 실패", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
