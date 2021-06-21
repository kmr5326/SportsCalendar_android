package SportsCalendar;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlarmIntentService extends IntentService {
    public int tomorrow_month, tomorrow_day;

    SportsCalendar sportsCalendar;
    ArrayList<String> matches_kbo = new ArrayList<>();
    ArrayList<String> matches_kleague = new ArrayList<>();

    private int cnt_matches_kbo = 0;
    private int cnt_matches_kleague = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        tomorrow_month = intent.getIntExtra("tomorrow_month",0);
        tomorrow_day = intent.getIntExtra("tomorrow_day",0);
        Log.d("서비스scommand",Integer.toString(tomorrow_day)+Integer.toString(tomorrow_month));

        return super.onStartCommand(intent, flags, startId);
    }

    public AlarmIntentService() {
        super("AlarmIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("서비스에요..", "onReceive 작동됨.");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.219.101:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sportsCalendar = retrofit.create(SportsCalendar.class);


        Log.d("날짜_서비스",Integer.toString(tomorrow_month)+Integer.toString(tomorrow_day));

        String id_string_kbo = String.format("%02d", tomorrow_month) + "." + String.format("%d", tomorrow_day);
        //id_string_kbo = "06.20";

        sportsCalendar.getKboData(id_string_kbo).enqueue(new Callback<KboSchedule>() {
            @Override
            public void onResponse(Call<KboSchedule> call, Response<KboSchedule> response) {
                if (response.isSuccessful()) {
                    matches_kbo.clear();
                    KboSchedule kboData = response.body();

                    if (kboData.getGame1() != null) {
                        matches_kbo.add(kboData.getGame1());
                        matches_kbo.add(kboData.getGame2());
                        matches_kbo.add(kboData.getGame3());
                        matches_kbo.add(kboData.getGame4());
                        matches_kbo.add(kboData.getGame5());
                        cnt_matches_kbo = 5;
                    }
                } else {
                    Log.d("야구일정", "response 실패.");
                }
            }

            @Override
            public void onFailure(Call<KboSchedule> call, Throwable t) {
                //t.printStackTrace();
                String message = t.getMessage();
                Log.d("야구일정", message);

            }
        });

        String id_string_kleague = "2021-" + String.format("%02d", tomorrow_month) + "-" + String.format("%02d", tomorrow_day);
        //id_string_kleague = "2021-07-20";

        sportsCalendar.getKleagueData(id_string_kleague).enqueue(new Callback<KleagueSchedule>() {

            @Override
            public void onResponse(Call<KleagueSchedule> call, Response<KleagueSchedule> response) {
                if (response.isSuccessful()) {
                    matches_kleague.clear();
                    KleagueSchedule data_Kleague = response.body();

                    Log.d("축구일정", data_Kleague.getThis_id());
                    Log.d("축구일정", Integer.toString(data_Kleague.getDataCnt()));
                    cnt_matches_kleague = data_Kleague.getDataCnt();
                    switch (cnt_matches_kleague) {
                        case 6:
                            matches_kleague.add(data_Kleague.getGame6());
                        case 5:
                            matches_kleague.add(data_Kleague.getGame5());
                        case 4:
                            matches_kleague.add(data_Kleague.getGame4());
                        case 3:
                            matches_kleague.add(data_Kleague.getGame3());
                        case 2:
                            matches_kleague.add(data_Kleague.getGame2());
                        case 1:
                            matches_kleague.add(data_Kleague.getGame1());
                        case 0:
                            break;
                    }
                } else {
                    Log.d("축구일정", "response 실패.");
                }

            }

            @Override
            public void onFailure(Call<KleagueSchedule> call, Throwable t) {
                //t.printStackTrace();
                String message = t.getMessage();
                Log.d("축구일정", message);
            }

        });

        Thread t1 = new MyThread();
        t1.setName("kbo");
        t1.start();

        Thread t2 = new MyThread();
        t2.setName("kleague");
        t2.start();
//        createNotificationChannel();
//        createNotification();


    }

    private void createNotification(String thread_name) {
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();

        if (thread_name.equals("kbo")) {
            for (int i = 0; i < cnt_matches_kbo; i++) {
                style.addLine(matches_kbo.get(i));

            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSmallIcon(R.drawable.kleague_daegu)
                    .setContentTitle("야구")
                    .setContentText("경기 일정 눌러서 보기")
                    .setAutoCancel(true)
                    .setStyle(style);


            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, builder.build());
        } else {
            for (int i = 0; i < cnt_matches_kleague; i++) {
                style.addLine(matches_kleague.get(i));

            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSmallIcon(R.drawable.kbo_kia)
                    .setContentTitle("축구")
                    .setContentText("경기 일정 눌러서 보기")
                    .setAutoCancel(true)
                    .setStyle(style);


            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(2, builder.build());
        }

    }


    private void createNotificationChannel() {
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


    public class MyThread extends Thread {
        @Override
        public void run() {
            Log.e("서비스 안", "들어옴");

            try {
                sleep(2000);
            } catch (InterruptedException e) {
                String message = e.getMessage();
                Log.e("서비스 안 mythread", message);
                e.printStackTrace();
            }
            String thread_name = this.getName();

            createNotificationChannel();
            createNotification(thread_name);

        }
    }
}






//onHandleIntent 여기 안에 있으면 되는 코드. 이게 노티피케이션을 만들어줌.

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "channel_name";
//            String description = "channel_description";
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setSmallIcon(R.drawable.kbo_kia)
//                .setContentTitle("테스트")
//                .setContentText("정해진 시간에만 켜지는거")
//                .setAutoCancel(true)
//                .setStyle(new NotificationCompat.InboxStyle()
//                        .addLine("1번 문장.")
//                        .addLine("22222222222")
//                        .addLine("#333333333")
//                        .addLine("444444444444"));
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        notificationManager.notify(1,builder.build());