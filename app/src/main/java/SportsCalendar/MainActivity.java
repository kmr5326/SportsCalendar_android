package SportsCalendar;
//이 액티비티는 로그인 이후 달력을 보여준다. 5월이면 5월 달력을 보여주고 그 밑에는 리스트로 그 날짜에 있는 경기들을 구현(프래그먼트)
//리스트의 한 부분을 클리갛며 ㄴ기능이 나올지도?
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import SportsCalendar.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements MatchListFragment.ShowMatchListCallback{

    public CalendarView calendarView;
    MatchListFragment match_list_fragment;
    TextView textView_main;
    String[] items = {"야구","축구"};
    public int match_type;// default
    public int this_month, this_dayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get_today();
        get_spinner();
        //오늘의 프래그먼트를 보여주는 기능 만들기.
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                this_month = month+1;
                this_dayOfMonth = dayOfMonth;
                put_click_day(this_month,this_dayOfMonth, match_type);
            }
        });


    }

    public void get_today() {
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDateMonth = new SimpleDateFormat("MM");
        SimpleDateFormat simpleDateDay = new SimpleDateFormat("dd");
        this_month = Integer.parseInt(simpleDateMonth.format(mDate));
        this_dayOfMonth = Integer.parseInt(simpleDateDay.format(mDate));
    }

    public void get_spinner() {
        textView_main = findViewById(R.id.textView_main);

        Spinner spinner_main = findViewById(R.id.spinner_main);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this, android.R.layout.simple_spinner_item, items);
        spinner_main.setAdapter(adapter);
        spinner_main.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                match_type = position;
                textView_main.setText(items[position]);
                put_click_day(this_month,this_dayOfMonth, match_type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textView_main.setText("");
            }
        });
    }


    @Override
    public void get_click_days_match(int click_month,int click_day) {


    }

    public void put_click_day(int click_month, int click_day, int match_num){

        match_list_fragment = new MatchListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.list_container,match_list_fragment).commit();

        Bundle bundle = new Bundle();
        bundle.putString("click_month",Integer.toString(click_month));
        bundle.putString("click_day",Integer.toString(click_day));
        bundle.putString("match_type",Integer.toString(match_type));

        match_list_fragment.setArguments(bundle);

    }

}


