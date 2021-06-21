//https://recipes4dev.tistory.com/43?category=605791
//이거보고 다시 해보자.
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

public class KboRankAdapter extends BaseAdapter {
    private ArrayList<KboRankItem> kboRankItemList = new ArrayList<KboRankItem>() ;


    public KboRankAdapter(){

    }

    @Override
    public int getCount() {

        return kboRankItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return kboRankItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rank_item_kbo, parent, false);
        }
        TextView kboRankTextView = (TextView) convertView.findViewById(R.id.kbo_rank);
        TextView kboTeamTextView = (TextView) convertView.findViewById(R.id.kbo_team);
        ImageView kboTeamLogoImageView = (ImageView) convertView.findViewById(R.id.kbo_team_imageView);
        TextView kboGameCntTextView = (TextView) convertView.findViewById(R.id.kbo_game_cnt);
        TextView kboWinTextView = (TextView) convertView.findViewById(R.id.kbo_win);
        TextView kboLoseTextView = (TextView) convertView.findViewById(R.id.kbo_lose);
        TextView kboDrawTextView = (TextView) convertView.findViewById(R.id.kbo_draw);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        KboRankItem kboRankItem = kboRankItemList.get(position);


        kboRankTextView.setText(Integer.toString(kboRankItem.getKbo_rank()));
        kboTeamTextView.setText(kboRankItem.getKbo_team());
        kboTeamLogoImageView.setImageResource(find_emblem(kboRankItem.getKbo_team()));//여기에 배열로.
        kboGameCntTextView.setText("경기수 : "+Integer.toString(kboRankItem.getKbo_gameCnt()));
        kboWinTextView.setText("승리 : "+Integer.toString(kboRankItem.getKbo_win()));
        kboLoseTextView.setText("패배 : "+Integer.toString(kboRankItem.getKbo_lose()));
        kboDrawTextView.setText("무승부 : "+Integer.toString(kboRankItem.getKbo_draw()));


        return convertView;
    }

    private int find_emblem(String kbo_team) {
        int[] kbo_emblem_resources = {R.drawable.kbo_doosan, R.drawable.kbo_hanhwa, R.drawable.kbo_kia, R.drawable.kbo_kiwoom, R.drawable.kbo_kt,
                R.drawable.kbo_lg, R.drawable.kbo_lotte , R.drawable.kbo_nc, R.drawable.kbo_samsung, R.drawable.kbo_ssg};
        int x = 0;//기본값은 두산으로
        switch (kbo_team) {
            case "두산":
                x = 0;
                break;
            case "한화":
                x = 1;
                break;
            case "KIA":
                x = 2;
                break;
            case "키움":
                x = 3;
                break;
            case "KT":
                x = 4;
                break;
            case "LG":
                x = 5;
                break;
            case "롯데":
                x = 6;
                break;
            case "NC":
                x = 7;
                break;
            case "삼성":
                x = 8;
                break;
            case "SSG":
                x = 9;
                break;
        }
                return kbo_emblem_resources[x];
    }

    public void addItem(int kbo_rank, String kbo_team, int kbo_game_cnt, int kbo_win, int kbo_lose, int kbo_draw) {

        KboRankItem item = new KboRankItem();

        item.setKbo_rank(kbo_rank);
        item.setKbo_team(kbo_team);
        item.setKbo_gameCnt(kbo_game_cnt);
        item.setKbo_win(kbo_win);
        item.setKbo_lose(kbo_lose);
        item.setKbo_draw(kbo_draw);

        kboRankItemList.add(item);

    }

}
