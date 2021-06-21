package SportsCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoardSeeActivity extends AppCompatActivity {
    private Button back_button;
    private TextView see_title, see_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_see);

        see_title = findViewById(R.id.see_title);
        see_content = findViewById(R.id.see_content);
        back_button = findViewById(R.id.back_button);
        Intent intent = getIntent();

        String title = intent.getStringExtra("SeePostTitle");
        String content = intent.getStringExtra("SeePostContent");

        see_title.setText(title);
        see_content.setText(content);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
