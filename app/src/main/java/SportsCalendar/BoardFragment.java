package SportsCalendar;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Thread.sleep;

public class BoardFragment extends Fragment {
    private static Handler zHandler;
    ArrayList<PostItem> board_list = new ArrayList<PostItem>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_board_list, container, false);

        get_board_list();
        Log.d("프래그먼트보드","oncreateview");

        RecyclerView recyclerView_board = rootView.findViewById(R.id.recyclerView_board);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView_board.setLayoutManager(layoutManager);

        BoardAdapter boardAdapter = new BoardAdapter();
//        데이터 가져와서 매핑하기.


//안되면 여기 핸들러부터 살펴봐야함. 레트로핏 연결은 성공적이었음.
        zHandler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("쓰레드","프래그먼트"+Integer.toString(board_list.size()));


                for(int i = 0; i < board_list.size() ; i++){
                    boardAdapter.setArrayData(board_list.get(i));
                    Log.d("쓰레드",board_list.get(i).toString());
                    Log.d("쓰레드","ds");

                }
                recyclerView_board.setAdapter(boardAdapter);
            }
        };

        class NewRunnable implements Runnable {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    String message = e.getMessage();
                    Log.d("보드프래그먼트",message);
                    e.printStackTrace();
                }

                //동기처리가 되어야 한다 이말.
                zHandler.post(runnable);

                // 받아온 날짜의 경기들을 리스트 형태로 받는다.
            }
        }

        NewRunnable nr = new NewRunnable();
        Thread t = new Thread(nr);
        t.start();


        return rootView;
    }


    private void get_board_list() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.219.101:8000//")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SportsCalendar sportsCalendar = retrofit.create(SportsCalendar.class);

        sportsCalendar.getBoardList().enqueue(new Callback<List<Board>>() {
            @Override
            public void onResponse(Call<List<Board>> call, Response<List<Board>> response) {
                Log.d("프래그먼트보드","성공");

                List<Board> boards = response.body();

                for(Board board: boards) {
                    PostItem postItem = new PostItem();

                    postItem.setPost_title(board.getTitle());
                    postItem.setPost_create(board.getRegistered_date());
                    postItem.setPost_content(board.getContent());
                    board_list.add(postItem);
                    Log.d("프래그먼트보드",board.getTitle());

                    //board를 넣어준다. 리스트에
                }
            }

            @Override
            public void onFailure(Call<List<Board>> call, Throwable t) {
                Log.d("프래그먼트보드","실패");

            }
        });
        //여기는 그림그리는거임.
    }
}
