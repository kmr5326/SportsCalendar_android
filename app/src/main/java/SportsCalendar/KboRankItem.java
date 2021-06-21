package SportsCalendar;

public class KboRankItem { //필요없을지도? 필요하면 게터세터 해주기.
    private int kbo_rank;
    private String kbo_team;
    private int kbo_gameCnt;
    private int kbo_win;
    private int kbo_lose;
    private int kbo_draw;

    public int getKbo_rank() {
        return kbo_rank;
    }

    public void setKbo_rank(int kbo_rank) {
        this.kbo_rank = kbo_rank;
    }

    public String getKbo_team() {
        return kbo_team;
    }

    public void setKbo_team(String kbo_team) {
        this.kbo_team = kbo_team;
    }

    public int getKbo_gameCnt() {
        return kbo_gameCnt;
    }

    public void setKbo_gameCnt(int kbo_gameCnt) {
        this.kbo_gameCnt = kbo_gameCnt;
    }

    public int getKbo_win() {
        return kbo_win;
    }

    public void setKbo_win(int kbo_win) {
        this.kbo_win = kbo_win;
    }

    public int getKbo_lose() {
        return kbo_lose;
    }

    public void setKbo_lose(int kbo_lose) {
        this.kbo_lose = kbo_lose;
    }

    public int getKbo_draw() {
        return kbo_draw;
    }

    public void setKbo_draw(int kbo_draw) {
        this.kbo_draw = kbo_draw;
    }
}
