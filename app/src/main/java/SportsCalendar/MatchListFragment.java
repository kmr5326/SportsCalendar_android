package SportsCalendar;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import SportsCalendar.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Thread.*;


public class MatchListFragment extends Fragment {
    private int cnt_matches;
    public static interface ShowMatchListCallback{
        public void get_click_days_match(int click_month, int click_day);
    }

    ArrayList<String> matches = new ArrayList<>();

    public int click_month, click_day, match_type;


    public ShowMatchListCallback callback;
    private static Handler mHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ShowMatchListCallback) {
            callback = (ShowMatchListCallback) context;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_match_list, container, false);

        get_click_day();
        get_matches();//스레드처리??

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        SportMatchAdapter adapter = new SportMatchAdapter();
        //이녀석을 스레드로 보내서 동기처리 해야할듯?


        mHandler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

                for(int i = 0; i < cnt_matches ; i++){
                    adapter.setArrayData(matches.get(i));
                }
                recyclerView.setAdapter(adapter);
            }
        };

        class NewRunnable implements Runnable {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    String message = e.getMessage();
                    Log.d("야구일정2-runnable",message);
                    e.printStackTrace();
                }

                //동기처리가 되어야 한다 이말.
                Log.d("야구일정2",Integer.toString(matches.size()));
                mHandler.post(runnable);

                // 받아온 날짜의 경기들을 리스트 형태로 받는다.
            }
        }

        NewRunnable nr = new NewRunnable();
        Thread t = new Thread(nr);
        t.start();


        return rootView;
    }

    public void get_matches(){ //백엔드에서 가져온다., 그리고 여기에 변수를 하나 넣어서, 축구인지 야구이닞

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.219.101:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SportsCalendar sportsCalendar = retrofit.create(SportsCalendar.class);

        if(match_type == 0) {//KBO 야구.
            String click_day_string = String.format("%02d", click_month) + "." + String.format("%d", click_day);
            Log.d("click_day_string", click_day_string);
            sportsCalendar.getKboData(click_day_string).enqueue(new Callback<KboSchedule>() {
                @Override
                public void onResponse(Call<KboSchedule> call, Response<KboSchedule> response) {
                    if (response.isSuccessful()) {
                        matches.clear();
                        KboSchedule kboData = response.body();

                        if (kboData.getGame1() != null) {
                            matches.add(kboData.getGame1());
                            matches.add(kboData.getGame2());
                            matches.add(kboData.getGame3());
                            matches.add(kboData.getGame4());
                            matches.add(kboData.getGame5());
                            cnt_matches = 5;
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

        } else if(match_type == 1) {//Kleague
            String id_Kleague = "2021-" + String.format("%02d", click_month) + "-" + String.format("%02d", click_day);
            Log.d("id_Kleague", id_Kleague);
            sportsCalendar.getKleagueData(id_Kleague).enqueue(new Callback<KleagueSchedule>() {

                @Override
                public void onResponse(Call<KleagueSchedule> call, Response<KleagueSchedule> response) {
                    if (response.isSuccessful()) {
                        matches.clear();
                        KleagueSchedule data_Kleague = response.body();

                        Log.d("축구일정", data_Kleague.getThis_id());
                        Log.d("축구일정", Integer.toString(data_Kleague.getDataCnt()));
                        cnt_matches = data_Kleague.getDataCnt();
                        switch (cnt_matches) {
                            case 6:
                                matches.add(data_Kleague.getGame6());
                            case 5:
                                matches.add(data_Kleague.getGame5());
                            case 4:
                                matches.add(data_Kleague.getGame4());
                            case 3:
                                matches.add(data_Kleague.getGame3());
                            case 2:
                                matches.add(data_Kleague.getGame2());
                            case 1:
                                matches.add(data_Kleague.getGame1());
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
        }
    }


    public void get_click_day(){
        Bundle bundle = getArguments();
        click_month = Integer.parseInt(bundle.getString("click_month"));
        click_day = Integer.parseInt(bundle.getString("click_day"));
        match_type = Integer.parseInt(bundle.getString("match_type"));

        //String click_date = items[match_type] + Integer.toString(click_month) + "월 " + Integer.toString(click_day);
        //textView1.setText(click_date);

//        if (callback != null){
//            callback.get_click_days_match(click_month,click_day);
//        }



    }

}