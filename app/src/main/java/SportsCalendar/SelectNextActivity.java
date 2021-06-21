package SportsCalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import SportsCalendar.R;

public class SelectNextActivity extends AppCompatActivity {

    private Button calender_button, rank_button, board_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_next);

        calender_button = findViewById(R.id.calender_button);
        rank_button = findViewById(R.id.rank_button);
        board_button = findViewById(R.id.board_button);

        calender_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectNextActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        rank_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectNextActivity.this, RankActivity.class);
                startActivity(intent);
            }
        });

        board_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectNextActivity.this, FreeBoardActivity.class);
                startActivity(intent);
            }
        });

    }
}