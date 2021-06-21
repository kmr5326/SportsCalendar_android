package SportsCalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import SportsCalendar.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RankActivity extends AppCompatActivity {

    Spinner spinner;
    TextView textView;
    String[] items = {"종목을 선택하세요","야구","축구"};
    ListView listView_rank;
    KboRankAdapter kboRankAdapter;
    KleagueRankAdapter KleagueRankAdapter;

    Retrofit retrofit;
    SportsCalendar sportsCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        textView = findViewById(R.id.textView);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, items);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(items[position]);
                get_rank(position);
                //여기서 핸들러 바꿔줘야함.
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textView.setText("");
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.219.101:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sportsCalendar = retrofit.create(SportsCalendar.class);

        get_rank(0);


    }

    public void get_rank(int position){
        listView_rank = (ListView) findViewById(R.id.listView_rank);

        switch (position) {

            case 0:
                kboRankAdapter = new KboRankAdapter();
                listView_rank.setAdapter(kboRankAdapter);
                //빈화면 보여주기
                break;
            case 1:

                kboRankAdapter = new KboRankAdapter();

                sportsCalendar.getKboRank().enqueue(new Callback<List<KboRank>>() {
                    @Override
                    public void onResponse(Call<List<KboRank>> call, Response<List<KboRank>> response) {
                        List<KboRank> kboRanks = response.body();
                        for(KboRank kboRank: kboRanks){

                            kboRankAdapter.addItem(kboRank.getRank(),kboRank.getTeam(),kboRank.getGameCnt(),
                                    kboRank.getWin(),kboRank.getLose(),kboRank.getDraw());

                        }
                        //화면을 다시 그려라. handler 쓰면 될듯?
                        listView_rank.setAdapter(kboRankAdapter);// 어댑터를 여기서 리스트뷰로 보내줌. 그러니까 다 받고나서 해야지~!!


                    }

                    @Override
                    public void onFailure(Call<List<KboRank>> call, Throwable t) {

                        Log.d("RankActivity",t.getMessage());

                    }
                });

                break;

            case 2:
                KleagueRankAdapter = new KleagueRankAdapter();

                sportsCalendar.getBaseballRank().enqueue(new Callback<List<KleagueRank>>() {
                    @Override
                    public void onResponse(Call<List<KleagueRank>> call, Response<List<KleagueRank>> response) {
                        List<KleagueRank> KleagueRanks = response.body();
                        for(KleagueRank KleagueRank : KleagueRanks){

                            KleagueRankAdapter.addItem(KleagueRank.getRank(), KleagueRank.getTeam(), KleagueRank.getGameCnt(), KleagueRank.getPoint(),
                                    KleagueRank.getWin(), KleagueRank.getDraw(), KleagueRank.getLose(), KleagueRank.getGoal(), KleagueRank.getLost());
                        }
                        listView_rank.setAdapter(KleagueRankAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<KleagueRank>> call, Throwable t) {
                        Log.d("RankActivity",t.getMessage());

                    }
                });


                break;

        }

    }

}