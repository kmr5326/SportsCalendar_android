package SportsCalendar;

import com.google.gson.annotations.SerializedName;

public class KleagueRankItem {
    private int kleague_rank ;
    private String kleague_team;
    private int kleague_gameCnt;
    private int kleague_point;
    private int kleague_win;
    private int kleague_draw;
    private int kleague_lose;
    private int kleague_goal;
    private int kleague_lost;

    public int getKleague_rank() {
        return kleague_rank;
    }

    public void setKleague_rank(int kleague_rank) {
        this.kleague_rank = kleague_rank;
    }

    public String getKleague_team() {
        return kleague_team;
    }

    public void setKleague_team(String kleague_team) {
        this.kleague_team = kleague_team;
    }

    public int getKleague_gameCnt() {
        return kleague_gameCnt;
    }

    public void setKleague_gameCnt(int kleague_gameCnt) {
        this.kleague_gameCnt = kleague_gameCnt;
    }

    public int getKleague_point() {
        return kleague_point;
    }

    public void setKleague_point(int kleague_point) {
        this.kleague_point = kleague_point;
    }

    public int getKleague_win() {
        return kleague_win;
    }

    public void setKleague_win(int kleague_win) {
        this.kleague_win = kleague_win;
    }

    public int getKleague_draw() {
        return kleague_draw;
    }

    public void setKleague_draw(int kleague_draw) {
        this.kleague_draw = kleague_draw;
    }

    public int getKleague_lose() {
        return kleague_lose;
    }

    public void setKleague_lose(int kleague_lose) {
        this.kleague_lose = kleague_lose;
    }

    public int getKleague_goal() {
        return kleague_goal;
    }

    public void setKleague_goal(int kleague_goal) {
        this.kleague_goal = kleague_goal;
    }

    public int getKleague_lost() {
        return kleague_lost;
    }

    public void setKleague_lost(int kleague_lost) {
        this.kleague_lost = kleague_lost;
    }
}
