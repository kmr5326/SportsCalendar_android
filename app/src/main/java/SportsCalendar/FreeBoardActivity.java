package SportsCalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import SportsCalendar.R;

public class FreeBoardActivity extends AppCompatActivity {

    BoardFragment boardFragment;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_board);

        button= findViewById(R.id.button_write_board);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //글쓰는 페이지로 넘어가기.
                Intent intent = new Intent(FreeBoardActivity.this, WriteActivity.class);
                startActivity(intent);
            }
        });

        boardFragment = new BoardFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_board, boardFragment).commit();


    }


}