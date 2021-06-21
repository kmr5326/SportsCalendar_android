package SportsCalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import SportsCalendar.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText login_id, login_password;
    private Button login_button, join_button;
    SportsCalendar sportsCalendar;
    Retrofit retrofit;
    AlarmManager alarmMgr;
    private PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.219.101:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sportsCalendar = retrofit.create(SportsCalendar.class);

        login_id = findViewById(R.id.login_id);
        login_password = findViewById(R.id.login_password);

        //회원가입으로 넘어가는 분기점.
        join_button = findViewById(R.id.join_button);
        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });

        //로그인으로 넘어가는 분기점. 여기가 처리가 좀 필요함.
        login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = login_id.getText().toString();
                String password = login_password.getText().toString();
                if (id.length() == 0 || password.length() == 0) {
                    Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    login(id, password);
                }

            }
        });


        //createNotificationChannel();
        alarmBroadcastReceiver();
    }



        public void alarmBroadcastReceiver(){
            Calendar calendar = Calendar.getInstance();
            //calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY,12);//지금 시간
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            //Log.d("날짜_캘린더설정",calendar.getTime().toString());

            Intent alarmReceiverIntent = new Intent(this, AlarmReceiver.class);

            alarmReceiverIntent.putExtra("tomorrow_month",calendar.get(Calendar.MONTH)+1);
            alarmReceiverIntent.putExtra("tomorrow_day",calendar.get(Calendar.DAY_OF_MONTH));


            Log.d("날짜_로그인",Integer.toString(
                    calendar.get(Calendar.MONTH)+1)+Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));

            alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            pendingIntent = PendingIntent.getBroadcast(this,0,alarmReceiverIntent,PendingIntent.FLAG_UPDATE_CURRENT);

            //alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        }



        //코드 확인되고 나면 setREPEATING을 setinexactrepeating으로 바꿀거임. 테스트때문에 정확한시간을 요구하라고 하는것뿐임.
        //세번째 파라미터는 시간임. 하루에 한 번 이라는 뜻. 1000*60*20은 20분 단위로 반복.
        //alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,
        //        calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_name";
            String description = "channel_description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void login(String id, String password){



        Call<LoginResponse> call = sportsCalendar.login(new LoginRequest(id, password));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                //Toast.makeText(LoginActivity.this, response.message(),Toast.LENGTH_LONG).show();

                if (response.isSuccessful()&&response.body() != null){
                    LoginResponse result = response.body();
                    String token = result.getKey();
                    Toast.makeText(getApplicationContext(),token,Toast.LENGTH_LONG).show();
                    //토큰을 이제 넘겨서 이 토큰을 가지고 로그인 작업을 수행할 것이다.

                    SharedPreferences pref = getSharedPreferences("mine",MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("token", token);
                    editor.commit();

//                    키값을 가져오는 코드. 나중에 게시판 만들면 거기에다가 사용하기
//                    SharedPreferences pref = getSharedPreferences("mine",MODE_PRIVATE);
//                    String prefData = pref.getString("token","");
//                    https://mine-it-record.tistory.com/271
//                    https://gaybee.tistory.com/7


                    Intent intent = new Intent(LoginActivity.this, SelectNextActivity.class);
                    startActivity(intent);
                } else if(response.isSuccessful() == true){
                    Log.d("Login_Response", "성공적으로 응답을 받았지만, 키값을 불러오지 못했습니다.");
                    Toast.makeText(LoginActivity.this, "관리자에게 문의 부탁드립니다.",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호를 잘못 입력하셨습니다.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("Login_Response", t.getMessage());

                Toast.makeText(LoginActivity.this, "관리자에게 문의 부탁드립니다.",Toast.LENGTH_LONG).show();

            }
        });

    }
}















//    public void get_tomorrow_match() {
//        //alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        int month = calendar.get(Calendar.MONTH) + 1;
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//
//
//        String click_day_string_kleague = "2021-" + String.format("%02d", month) + "-" + String.format("%02d", day);
//        sportsCalendar.getKleagueData(click_day_string_kleague).enqueue(new Callback<KleagueSchedule>() {
//
//            @Override
//            public void onResponse(Call<KleagueSchedule> call, Response<KleagueSchedule> response) {
//                if (response.isSuccessful()) {
//
//                    KleagueSchedule data_Kleague = response.body();
//
//                    cnt_matches = data_Kleague.getDataCnt();
//                    switch (cnt_matches) {
//                        case 6:
//                            tomorrow_matches.add(data_Kleague.getGame6());
//                        case 5:
//                            tomorrow_matches.add(data_Kleague.getGame5());
//                        case 4:
//                            tomorrow_matches.add(data_Kleague.getGame4());
//                        case 3:
//                            tomorrow_matches.add(data_Kleague.getGame3());
//                        case 2:
//                            tomorrow_matches.add(data_Kleague.getGame2());
//                        case 1:
//                            tomorrow_matches.add(data_Kleague.getGame1());
//                        case 0:
//                            break;
//                    }
//                } else {
//                    Log.d("축구일정", "response 실패.");
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<KleagueSchedule> call, Throwable t) {
//                //t.printStackTrace();
//                String message = t.getMessage();
//                Log.d("축구일정", message);
//            }
//
//        });
//
//        String click_day_string_kbo = String.format("%02d", month) + "." + String.format("%d", day);
//        sportsCalendar.getKboData(click_day_string_kbo).enqueue(new Callback<KboSchedule>() {
//            @Override
//            public void onResponse(Call<KboSchedule> call, Response<KboSchedule> response) {
//                if (response.isSuccessful()) {
//                    tomorrow_matches.clear();
//                    KboSchedule kboData = response.body();
//
//                    if (kboData.getGame1() != null) {
//                        cnt_matches += 5;
//                        tomorrow_matches.add(kboData.getGame1());
//                        tomorrow_matches.add(kboData.getGame2());
//                        tomorrow_matches.add(kboData.getGame3());
//                        tomorrow_matches.add(kboData.getGame4());
//                        tomorrow_matches.add(kboData.getGame5());
//                    }
//                } else {
//                    Log.d("야구일정", "response 실패.");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<KboSchedule> call, Throwable t) {
//                //t.printStackTrace();
//                String message = t.getMessage();
//                Log.d("야구일정", message);
//
//            }
//        });
//
//    }



//        createNotificationChannel();
//        createNotification("channel_id",1,"title","text");
//        //alarmBroadcastReceiver();


//    public void alarmBroadcastReceiver(){
//        Intent alarmReceiverIntent = new Intent(this, AlarmReceiver.class);
//        pendingIntent = PendingIntent.getBroadcast(this,0,alarmReceiverIntent,0);
//
//        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY,23);
//        calendar.set(Calendar.MINUTE,21);
//
//
//        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
//        //코드 확인되고 나면 setREPEATING을 setinexactrepeating으로 바꿀거임. 테스트때문에 정확한시간을 요구하라고 하는것뿐임.
//        //세번째 파라미터는 시간임. 하루에 한 번 이라는 뜻. 1000*60*20은 20분 단위로 반복.
//        //alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,
//        //        calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

//
//    public void createNotification(String channelId, int id, String title, String text){
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channelId")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setSmallIcon(R.drawable.kbo_kia)
//                .setContentTitle("테스트")
//                .setContentText("정해진 시간에만 켜지는거")
//                .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE);
//
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(1,builder.build());
//
//    }
//    public void createNotificationChannel(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            String channelId = "channel_id";
//            CharSequence name = "알림설정에서의 제목";
//            String description = "Oreo Version 이상을 위한 알림(알림설정에서의 설명)";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel notificationChannel = new NotificationChannel(channelId, name, importance);
//            notificationChannel.setDescription(description);
//
//            notificationManager.createNotificationChannel(notificationChannel);
//
//        }
//}
