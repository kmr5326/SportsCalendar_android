package SportsCalendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import SportsCalendar.R;

import java.util.ArrayList;

public class KleagueRankAdapter extends BaseAdapter {

    private ArrayList<KleagueRankItem> KleagueRankItemList = new ArrayList<KleagueRankItem>() ;

    public KleagueRankAdapter(){

    }

    @Override
    public int getCount() {
        return KleagueRankItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return KleagueRankItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rank_item_kleague, parent, false);
        }

        TextView kleagueRankTextView = (TextView) convertView.findViewById(R.id.kleague_rank);
        TextView kleagueTeamTextView = (TextView) convertView.findViewById(R.id.kleague_team);
        ImageView kleagueLogoImageView = (ImageView) convertView.findViewById(R.id.kleague_imageView);// 여기 이미지...
        TextView kleagueGameCntTextView = (TextView) convertView.findViewById(R.id.kleague_game_cnt);
        TextView kleaguePointTextView = (TextView) convertView.findViewById(R.id.kleague_point);
        TextView kleagueWinTextView = (TextView) convertView.findViewById(R.id.kleague_win);
        TextView kleagueDrawTextView = (TextView) convertView.findViewById(R.id.kleague_draw);
        TextView kleagueLoseTextView = (TextView) convertView.findViewById(R.id.kleague_lose);
        TextView kleagueGoalTextView = (TextView) convertView.findViewById(R.id.kleague_goal);
        TextView kleagueLostTextView = (TextView) convertView.findViewById(R.id.kleague_lost);

        KleagueRankItem KleagueRankItem = KleagueRankItemList.get(position);

        kleagueRankTextView.setText(Integer.toString(KleagueRankItem.getKleague_rank()));
        kleagueTeamTextView.setText(KleagueRankItem.getKleague_team());
        kleagueLogoImageView.setImageResource(find_emblem(KleagueRankItem.getKleague_team()));//여기에 배열로.
        kleagueGameCntTextView.setText("경기 수 : "+Integer.toString(KleagueRankItem.getKleague_gameCnt()));
        kleaguePointTextView.setText("승점 : "+Integer.toString(KleagueRankItem.getKleague_point()));
        kleagueWinTextView.setText("승리 : "+Integer.toString(KleagueRankItem.getKleague_win()));
        kleagueDrawTextView.setText("무승부 : "+Integer.toString(KleagueRankItem.getKleague_draw()));
        kleagueLoseTextView.setText("패배 : "+Integer.toString(KleagueRankItem.getKleague_lose()));
        kleagueGoalTextView.setText("득점 : "+Integer.toString(KleagueRankItem.getKleague_goal()));
        kleagueLostTextView.setText("실점 : "+Integer.toString(KleagueRankItem.getKleague_lost()));

        return convertView;
    }
    private int find_emblem(String kleague_team) {
        int[] kleague_emblem_resources = {R.drawable.kleague_daegu, R.drawable.kleague_gangwon, R.drawable.kleague_gwangju,
                R.drawable.kleague_hyundai, R.drawable.kleague_incheon, R.drawable.kleague_jeju, R.drawable.kleague_jeonbuk,
                R.drawable.kleague_pohang, R.drawable.kleague_samsung, R.drawable.kleague_seongnam, R.drawable.kleague_seoul, R.drawable.kleague_suwon};

        switch (kleague_team) {
            case "대구":
                return kleague_emblem_resources[0];
            case "강원":
                return kleague_emblem_resources[1];
            case "광주":
                return kleague_emblem_resources[2];
            case "울산":
                return kleague_emblem_resources[3];
            case "인천":
                return kleague_emblem_resources[4];
            case "제주":
                return kleague_emblem_resources[5];
            case "전북":
                return kleague_emblem_resources[6];
            case "포항":
                return kleague_emblem_resources[7];
            case "수원":
                return kleague_emblem_resources[8];
            case "성남":
                return kleague_emblem_resources[9];
            case "서울":
                return kleague_emblem_resources[10];
            case "수원FC":
                return kleague_emblem_resources[11];
        }
        return kleague_emblem_resources[0];//대구가 기본
    }
    public void addItem(int kleague_rank, String kleague_team, int kleague_game_cnt, int kleague_point,
                        int kleague_win, int kleague_draw, int kleague_lose, int kleague_goal, int kleague_lost) {
        KleagueRankItem item = new KleagueRankItem();

        item.setKleague_draw(kleague_draw);
        item.setKleague_gameCnt(kleague_game_cnt);
        item.setKleague_goal(kleague_goal);
        item.setKleague_lose(kleague_lose);
        item.setKleague_lost(kleague_lost);
        item.setKleague_win(kleague_win);
        item.setKleague_point(kleague_point);
        item.setKleague_rank(kleague_rank);
        item.setKleague_team(kleague_team);

        KleagueRankItemList.add(item);
    }


}
